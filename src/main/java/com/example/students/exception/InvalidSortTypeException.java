package com.example.students.exception;

public class InvalidSortTypeException extends RuntimeException {
    public InvalidSortTypeException(String message) {
        super(message);
    }
}
