package com.furkan.ecommerce.order;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public  class OrderController {

    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<LocalDate> placeOrder(@PathVariable Long userId) throws StripeException {

        LocalDate date= orderService.placeOrder(userId);
        return ResponseEntity.ok().body(date);
    }
}
