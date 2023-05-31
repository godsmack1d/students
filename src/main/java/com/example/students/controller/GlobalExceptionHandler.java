package com.example.students.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleMultipartException(RuntimeException ex, Model model) {
        model.addAttribute("message", "Error processing request: " + ex.getMessage());
        return "error";
    }

}
