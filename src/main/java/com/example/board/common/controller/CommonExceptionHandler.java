package com.example.board.common.controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFound(EntityNotFoundException e) {
        return e.getMessage();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgument(IllegalArgumentException e) {
        return e.getMessage();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String validException(MethodArgumentNotValidException e) {
        return e.getMessage();
    }
}
