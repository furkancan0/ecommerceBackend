package com.furkan.ecommerce.customer;

import com.furkan.ecommerce.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerDto {
    private String name;
    private String email;
    private Role role;
    private Long cartId;
}
