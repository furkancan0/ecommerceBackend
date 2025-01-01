package com.furkan.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class ProductPurchaseException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ProductPurchaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}