package com.epam.project.exceptions;

public class IncorrectPropertyException extends Exception {
    public IncorrectPropertyException() {
        super("Unable to read property");
    }
    public IncorrectPropertyException(String message) {
        super(message);
    }
}