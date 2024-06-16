package com.ghazal.spring6restmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {
    @ExceptionHandler
    ResponseEntity handleJPATransactionViolation(TransactionSystemException exception){
        ResponseEntity.BodyBuilder badRequestResponseEntity = ResponseEntity.badRequest();
        if (exception.getCause().getCause() instanceof ConstraintViolationException){
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception.getCause().getCause();
            List errorList = constraintViolationException.getConstraintViolations()
                    .stream()
                    .map(constraintViolation -> {
                        Map<String, String> errorMap = new HashMap<>();
                        errorMap.put(constraintViolation.getPropertyPath().toString(),
                                constraintViolation.getMessage());
                        return errorMap;
                            }).toList();
            return badRequestResponseEntity.body(errorList);
        }
        return badRequestResponseEntity.build();
    }
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){
        List<Map<String, String>> errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();
        return ResponseEntity.badRequest().body(errorList); //exception.getBindingResult().getFieldErrors()
    }
}
