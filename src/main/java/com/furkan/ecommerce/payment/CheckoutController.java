package com.furkan.ecommerce.payment;

import com.furkan.ecommerce.config.Config;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/config")
    public ResponseEntity<ConfigResponse> config() {
        ConfigResponse configResponse = new ConfigResponse();
        return ResponseEntity.ok().body(configResponse);

    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<PaymentResponse> createPaymentIntent()
            throws StripeException {
        PaymentResponse paymentIntent = checkoutService.createPaymentIntent();
        return ResponseEntity.ok().body(paymentIntent);
    }

}