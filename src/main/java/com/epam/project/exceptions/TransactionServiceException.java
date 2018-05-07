package com.epam.project.exceptions;

public class TransactionServiceException extends Exception {
    public TransactionServiceException() {
        super();
    }
    public TransactionServiceException(String message) {
        super(message);
    }
}
