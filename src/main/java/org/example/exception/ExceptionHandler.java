package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Component

public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(value = IncorrectUsernameException.class)
    public ResponseEntity<ErrorResponse> handleDivideByZeroException(IncorrectUsernameException e) {

        ErrorResponse errorResponse = new ErrorResponse("INCORRECT_USER_NAME", e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
