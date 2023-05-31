package com.example.students.exception;

public class FileParsingException extends RuntimeException {

    public FileParsingException(Throwable cause) {
        super(cause);
    }

    public FileParsingException(String message) {
        super(message);
    }
}
