package com.furkan.ecommerce.cart;

import com.furkan.ecommerce.Item.Item;
import com.furkan.ecommerce.Item.ItemDto;
import com.furkan.ecommerce.customer.Customer;
import com.furkan.ecommerce.customer.CustomerDto;
import com.furkan.ecommerce.customer.CustomerRepository;
import com.furkan.ecommerce.exception.BusinessException;
import com.furkan.ecommerce.exception.UserNotFoundException;
import com.furkan.ecommerce.jwt.JwtService;
import com.furkan.ecommerce.mappers.CartMapper;
import com.furkan.ecommerce.product.Product;
import com.furkan.ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService{
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final JwtService jwtService;


    public ItemDto addItemToCart(Long cartId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Product not found", HttpStatus.NOT_FOUND));

        BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));

        //create one if cart not created yet

        Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new BusinessException("Cart not found", HttpStatus.NOT_FOUND));


        if (cart.getId() == null) {
            cart.setCustomer(cart.getCustomer());
            cartRepository.save(cart);
        }

        Item newItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(Item.builder()
                        .product(product)
                        .quantity(quantity)
                        .unitPrice(product.getPrice())
                        .subTotal(subTotal)
                        .cart(cart)
                        .build());

        //If the product is already in the cart
        if (newItem.getId() != null) {
            newItem.setQuantity(newItem.getQuantity() + quantity);
        }

        cart.getItems().add(newItem);
        BigDecimal totalAmount = getTotalPrice(cart);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);

        return cartMapper.itemToItemDto(newItem);
    }

    public CartDto removeItemFromCart(Long cartId, Long productId,Long temporaryCartId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Product not found", HttpStatus.NOT_FOUND));

        Cart cart;
        if(true) {
            cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new BusinessException("Cart not found", HttpStatus.NOT_FOUND));
        }else {
            cart = cartRepository.findById(temporaryCartId)
            .orElseThrow(() -> new BusinessException("Cart not found", HttpStatus.NOT_FOUND));
        }

        Item cartItemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Cart item not found", HttpStatus.NOT_FOUND));

        cart.getItems().remove(cartItemToRemove);

        BigDecimal totalAmount= getTotalPrice(cart);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);

        List<ItemDto> cartItems = getCartItems(cart);

        return cartMapper.cartToCartDto(cart, totalAmount, cartItems);
    }

    private BigDecimal getTotalPrice(Cart cart) {
        return cart.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<ItemDto> getCartItems(Cart cart) {
        return cart.getItems().stream().map(item -> {
            ItemDto itemDto = new ItemDto();
            itemDto.setId(item.getId());
            itemDto.setProduct(cartMapper.productToProductDto(item.getProduct()));
            itemDto.setQuantity(item.getQuantity());
            itemDto.setUnitPrice(item.getUnitPrice());
            itemDto.setSubTotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return itemDto;
        }).toList();
    }


    public CartDto getUserCart(String token) {
        final String jwt = token.substring(7);
        String name = jwtService.extractUsername(jwt);

        Customer customer = customerRepository.findByEmail(name).orElseThrow();
        Cart cart = cartRepository.findById(customer.getId()).orElseThrow(() -> new RuntimeException("Cart not found"));

        List<ItemDto> cartItems = getCartItems(cart);
        BigDecimal totalAmount = getTotalPrice(cart);

        return cartMapper.cartToCartDto(cart, totalAmount, cartItems);
    }

    public CartDto getGuestCart(Long cartId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        List<ItemDto> cartItems = getCartItems(cart);
        BigDecimal totalAmount = getTotalPrice(cart);

        return cartMapper.cartToCartDto(cart, totalAmount, cartItems);
    }


    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new UserNotFoundException("Cart not found for user id: " + cartId, HttpStatus.NOT_FOUND));
        if (cart != null) {
            cart.getItems().clear();
            cart.setTotalAmount(BigDecimal.ZERO);
            cartRepository.save(cart);
        }
    }

    public Cart getCartByCustomerId(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() ->
                new UserNotFoundException("Cart not found for user id: " + cartId, HttpStatus.NOT_FOUND));
    }



}