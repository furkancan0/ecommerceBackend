package com.furkan.ecommerce.auth;

import com.furkan.ecommerce.customer.Customer;
import com.furkan.ecommerce.customer.CustomerRepository;
import com.furkan.ecommerce.jwt.JwtService;
import com.furkan.ecommerce.role.Role;
import com.furkan.ecommerce.role.RoleEnum;
import com.furkan.ecommerce.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private  final CustomerRepository customerRepository;
    private  final RoleRepository roleRepository;
    private  final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        var user = Customer.builder()
                .name(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdDate(LocalDateTime.now())
                .role(optionalRole.get())
                .build();
        var savedUser = customerRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //FirstStep
        //We need to validate our request (validate whether password & username is correct)
        //Verify whether user present in the database
        //Which AuthenticationProvider -> DaoAuthenticationProvider (Inject)
        //We need to authenticate using authenticationManager injecting this authenticationProvider
        //SecondStep
        //Verify whether userName and password is correct => UserNamePasswordAuthenticationToken
        //Verify whether user present in db
        //generateToken
        //Return the token
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = customerRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();

    }
}