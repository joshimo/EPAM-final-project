package com.epam.project.exceptions;

public class DataDuplicationException extends Exception {
    public DataDuplicationException() {
        super("Trying to duplicate data in DB");
    }
    public DataDuplicationException(String message) {
        super(message);
    }
}
