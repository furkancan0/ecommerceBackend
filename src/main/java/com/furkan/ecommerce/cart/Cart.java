package com.furkan.ecommerce.cart;

import com.furkan.ecommerce.Item.Item;
import com.furkan.ecommerce.customer.Customer;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> items = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Nullable
    private String temporaryCartId;

}