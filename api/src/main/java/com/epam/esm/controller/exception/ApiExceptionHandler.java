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
        String code;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException();

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        apiException.setErrorMessage(e.getMessage());

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(ServiceException e) {
        String code;
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException();

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        apiException.setErrorMessage(e.getMessage());

        return new ResponseEntity<>(apiException, httpStatus);
    }

    private String codeDefinition(Exception e, HttpStatus httpStatus) {
        String res = "00";
        String className = e.getStackTrace()[1].getClassName();
        int httpStatusCode = httpStatus.value();

        if(className.contains("GiftCertificate")){
            res = "01";
        }

        if(className.contains("Tag")){
            res = "02";
        }

        res = httpStatusCode + res;

        return res;
    }
}
