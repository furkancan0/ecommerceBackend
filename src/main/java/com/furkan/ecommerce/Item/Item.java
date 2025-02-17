package com.furkan.ecommerce.Item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.furkan.ecommerce.cart.Cart;
import com.furkan.ecommerce.order.Order;
import com.furkan.ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "cart_id")
    private Cart cart;

}