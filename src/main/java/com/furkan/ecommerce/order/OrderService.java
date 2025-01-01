package com.furkan.ecommerce.order;

import com.furkan.ecommerce.orderItem.OrderItem;
import com.furkan.ecommerce.cart.Cart;
import com.furkan.ecommerce.cart.CartService;
import com.furkan.ecommerce.enums.OrderStatus;
import com.furkan.ecommerce.payment.CheckoutService;
import com.furkan.ecommerce.product.Product;
import com.furkan.ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final CheckoutService checkoutService;

    @Transactional
    public LocalDate placeOrder(Long customerId){
        Cart cart   = cartService.getCartByCustomerId(customerId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setItems(new HashSet<>(orderItemList));
        order.setTotalAmount(cart.getTotalAmount());
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder.getOrderDate();
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return  cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return  OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getUnitPrice()).build();
        }).toList();
    };

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return  order;
    }

}