package com.furkan.ecommerce.customer;

import com.furkan.ecommerce.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository userRepository;

}