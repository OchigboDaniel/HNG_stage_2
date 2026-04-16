package com.classifyName.nameClassifier;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CustomerErrorHaddler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleInvalidUUID() {
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> emptyNameHandle(){
        return  ResponseEntity.badRequest().body( new ErrorResponse("error", "Missing or empty name parameter"));
        }

}
