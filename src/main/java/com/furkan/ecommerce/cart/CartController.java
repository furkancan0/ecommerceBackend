package com.furkan.ecommerce.cart;

import com.furkan.ecommerce.Item.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/carts")
class CartController {

    private final CartService cartService;

    @PostMapping("/{customerId}/{productId}/{quantity}")
    public ResponseEntity<ItemDto> addItemToCart(@PathVariable Long customerId, @PathVariable Long productId, @PathVariable int quantity) {
        ItemDto cartDto = cartService.addItemToCart(customerId, productId, quantity);
        return ResponseEntity.ok().body(cartDto);
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<CartDto> removeItemFromCart(@PathVariable Long customerId, @PathVariable Long productId) {
        CartDto cartDto = cartService.removeItemFromCart(customerId, productId);
        return ResponseEntity.ok().body(cartDto);
    }


    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long cartId, @RequestHeader(value = "Authorization",required = false) String token) {
        CartDto cart = cartService.getUserCart(token);
        return ResponseEntity.ok().body(cart);
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<String> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return new ResponseEntity<>("Cart Cleared", HttpStatus.OK);
    }

    @PostMapping("/merge")
    public ResponseEntity<?> mergeCart(@RequestParam String temporaryCartId, @RequestParam Long userId) {
        //cartService.mergeCarts(temporaryCartId, userId);
        return ResponseEntity.ok("Cart merged successfully.");
    }
}