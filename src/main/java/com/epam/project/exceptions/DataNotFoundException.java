package com.epam.project.exceptions;

public class DataNotFoundException extends Exception {
    public DataNotFoundException() {
        super("Data not found in DB by specified key");
    }
    public DataNotFoundException(String message) {
        super(message);
    }
}
