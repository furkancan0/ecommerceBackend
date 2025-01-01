package com.furkan.ecommerce.payment;

import com.furkan.ecommerce.cart.Cart;
import com.furkan.ecommerce.cart.CartService;
import com.furkan.ecommerce.customer.CustomerRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartService cartService;
    public PaymentResponse createPaymentIntent() throws StripeException {
        Cart cart   = cartService.getCartByCustomerId(1L);
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(cart.getTotalAmount().longValue()* 100L)
                        .setCurrency("usd")
                        //.setPaymentMethod("card")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams
                                        .AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        ).build();
        PaymentIntent intent = PaymentIntent.create(params);
        return new PaymentResponse(intent.getClientSecret(),intent.getCurrency(),intent.getAmount());
    }
}