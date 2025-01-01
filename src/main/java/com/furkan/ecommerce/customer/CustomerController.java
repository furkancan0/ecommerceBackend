package com.furkan.ecommerce.customer;

import com.furkan.ecommerce.mappers.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CartMapper cartMapper;

    @Transactional(readOnly = true)
    @GetMapping("/me")
    public ResponseEntity<CustomerDto> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = (Customer) authentication.getPrincipal();
        CustomerDto customerDto = cartMapper.customerToCustomerDto(customer);
        return ResponseEntity.ok(customerDto);
    }
}
