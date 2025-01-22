package com.example.board.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
//@Slf4j
public class CommonExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFound(EntityNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
//        log.error(e.getMessage());
        return "common/error_page";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgument(IllegalArgumentException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "common/error_page";
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String validException(MethodArgumentNotValidException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "common/error_page";
    }
    @ExceptionHandler(BindException.class)
    public String bindException(BindException e, Model model) {
        e.printStackTrace();
        model.addAttribute("errorMessage", e.getMessage());
        return "common/error_page";
    }
}
