package com.classifyName.nameClassifier;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerErrorHaddler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> emptyNameHandle(){
        return  ResponseEntity.badRequest().body( new ErrorResponse("error", "Missing or empty name parameter"));
        }
}
