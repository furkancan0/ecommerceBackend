package com.furkan.ecommerce.cart;

import com.furkan.ecommerce.Item.ItemDto;
import com.furkan.ecommerce.customer.Customer;
import com.furkan.ecommerce.customer.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CartDto {
    private Long id;
    private CustomerDto customer;
    private BigDecimal totalAmount;
    private List<ItemDto> cartItems;
}