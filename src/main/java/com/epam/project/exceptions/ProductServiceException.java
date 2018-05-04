package com.epam.project.exceptions;

public class ProductServiceException extends Exception {
    public ProductServiceException() {
        super();
    }
    public ProductServiceException(String message) {
        super(message);
    }
}
