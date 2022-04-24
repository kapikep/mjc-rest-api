package com.epam.esm.controller.exception;

import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(ValidateException e) {
        ApiException apiException = new ApiException();
        apiException.setErrorMessage(e.getMessage());

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(ServiceException e) {
        ApiException apiException = new ApiException();
        apiException.setErrorMessage(e.getMessage());

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }
}
