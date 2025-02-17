package com.furkan.ecommerce.order;

import com.furkan.ecommerce.Item.Item;
import com.furkan.ecommerce.exception.OutOfStockException;
import com.furkan.ecommerce.kafka.OrderPlacedEvent;
import com.furkan.ecommerce.orderItem.OrderItem;
import com.furkan.ecommerce.cart.Cart;
import com.furkan.ecommerce.cart.CartService;
import com.furkan.ecommerce.enums.OrderStatus;
import com.furkan.ecommerce.payment.CheckoutService;
import com.furkan.ecommerce.product.Product;
import com.furkan.ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
    private final CheckoutService checkoutService;

    @Transactional
    public LocalDate placeOrder(Long customerId){
        Cart cart   = cartService.getCartByCustomerId(customerId);
        Order order = createOrder(cart);
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setItems(new HashSet<>(orderItemList));
        order.setTotalAmount(cart.getTotalAmount());
        Order savedOrder = orderRepository.save(order);

        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(),cart.getCustomer().getEmail());
        kafkaTemplate.send("order-placed",orderPlacedEvent);

        cartService.clearCart(cart.getId());
        return savedOrder.getOrderDate();
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return  cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            //Transactional used if there is any out of stock case
            validateStock(cartItem,product);
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return  OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getUnitPrice()).build();
        }).toList();
    };

    private static void validateStock(Item item, Product product) {
        if (item.getQuantity() > product.getInventory()) {
            throw new OutOfStockException("Insufficient stock !", HttpStatus.BAD_REQUEST);
        }
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return  order;
    }

}