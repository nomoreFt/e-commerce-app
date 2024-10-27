package com.nomoreft.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}
