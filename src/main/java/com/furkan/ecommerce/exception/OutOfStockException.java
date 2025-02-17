
package com.furkan.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutOfStockException extends RuntimeException {

    private final HttpStatus httpStatus;

    public OutOfStockException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
