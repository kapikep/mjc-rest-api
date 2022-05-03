package com.epam.esm.controller.exception;

import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {
    private final MessageSource source;

    public ApiExceptionHandler(MessageSource source) {
        this.source = source;
    }
    
    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(HttpMessageNotReadableException e) {
        String code;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException();

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        apiException.setErrorMessage(e.getMessage());

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleExceptionNotFound(NoHandlerFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException();
        apiException.setErrorMessage(source.getMessage("incorrect.path", new Object[] {e.getMessage()}, LocaleContextHolder.getLocale()));
        apiException.setErrorCode(codeDefinition(e, httpStatus));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(ValidateException e) {
        String message;
        String code;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException();

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        if(!e.getResourceBundleCodeList().isEmpty()){
            StringBuilder builder = new StringBuilder();
            List<String> strings = e.getResourceBundleCodeList();
            Iterator<String> iter = strings.iterator();
            while (iter.hasNext()){
                String s = iter.next();
                s = source.getMessage(s, null, LocaleContextHolder.getLocale());
                builder.append(s);
                if(iter.hasNext()){
                    builder.append(" ,");
                }
            }
            message = builder.toString();
            apiException.setErrorMessage(message);
        }else {
            if(e.getResourceBundleCode() != null){
                message = source.getMessage(e.getResourceBundleCode(), e.getArgs(), LocaleContextHolder.getLocale());
                apiException.setErrorMessage(message);
            }else {
                apiException.setErrorMessage(e.getMessage());
            }
        }

        return new ResponseEntity<>(apiException, httpStatus);
    }

//    @ExceptionHandler
//    public ResponseEntity<ApiException> handleException(ServiceException e) {
//        String code;
//        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
//        ApiException apiException = new ApiException();
//
//        code = codeDefinition(e, httpStatus);
//        apiException.setErrorCode(code);
//        apiException.setErrorMessage(setErrorMessage(e, e.getResourceBundleCode(), e.getArgs()));
//
//        return new ResponseEntity<>(apiException, httpStatus);
//    }

//    private String setErrorMessage(Exception e, String getResourceBundleCode, Object[] args) {
//        if (getResourceBundleCode != null) {
//            return source.getMessage(getResourceBundleCode, args, LocaleContextHolder.getLocale());
//        } else {
//            return e.getMessage();
//        }
//    }
    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(ServiceException e) {
        String code;
        String message;
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException();

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        if(e.getResourceBundleCode() != null){
            message = source.getMessage(e.getResourceBundleCode(), e.getArgs(), LocaleContextHolder.getLocale());
            apiException.setErrorMessage(message);
        }else {
            apiException.setErrorMessage(e.getMessage());
        }

        return new ResponseEntity<>(apiException, httpStatus);
    }
//    private ResponseEntity getApiExceptionResponseEntity(HttpStatus httpStatus, Exception e){
//        String code;
//        ApiException apiException = new ApiException();
//        code = codeDefinition(e, httpStatus);
//        apiException.setErrorCode(code);
//
//        return new ResponseEntity<>(apiException, httpStatus);
//    }

    private String codeDefinition(Exception e, HttpStatus httpStatus) {
        String res = "00";
        String className = e.getStackTrace()[1].getClassName();
        int httpStatusCode = httpStatus.value();

        if (className.contains("GiftCertificate")) {
            res = "01";
        }

        if (className.contains("Tag")) {
            res = "02";
        }
        res = httpStatusCode + res;

        return res;
    }
}
