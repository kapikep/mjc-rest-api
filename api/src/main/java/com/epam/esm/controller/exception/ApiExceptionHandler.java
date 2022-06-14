package com.epam.esm.controller.exception;

import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;

/**
 * Handles application exceptions
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
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
        apiException.setErrorMessage(e.getCause().getMessage());

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(NoHandlerFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException();
        apiException.setErrorMessage(source.getMessage("incorrect.path", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale()));
        apiException.setErrorCode(codeDefinition(e, httpStatus));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    /**
     * Exception thrown when a request handler does not support a specific request method.
     */
    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(HttpRequestMethodNotSupportedException e) {
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        ApiException apiException = new ApiException();
        apiException.setErrorMessage(source.getMessage("error.incorrect.method", null, LocaleContextHolder.getLocale()));
        apiException.setErrorCode(codeDefinition(e, httpStatus));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(Exception e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException();
        apiException.setErrorMessage(e.getMessage());
        apiException.setErrorCode(codeDefinition(e, httpStatus));
        return new ResponseEntity<>(apiException, httpStatus);
    }

//    @ExceptionHandler
//    public ResponseEntity<ApiException> handleValidationExceptions(MethodArgumentNotValidException e) {
//        String code;
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//        ApiException apiException = new ApiException();
//
//        code = codeDefinition(e, httpStatus);
//        apiException.setErrorCode(code);
//
//        Map<String, String> errors = new HashMap<>();
//        e.getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//
//        apiException.setErrorMessage(errors.toString());
//        return new ResponseEntity<>(apiException, httpStatus);
//    }

    @ExceptionHandler
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LinkedHashMap<String, String> errors = new LinkedHashMap<>();
        e.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        errors.put("errorCode", codeDefinition(e, httpStatus));
        return errors;
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(ValidateException e) {
        String message;
        String code;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException();

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        if (e.getResourceBundleCodeList() != null) {
            StringBuilder builder = new StringBuilder();
            List<String> strings = e.getResourceBundleCodeList();
            Iterator<String> iter = strings.iterator();
            while (iter.hasNext()) {
                String s = iter.next();
                s = source.getMessage(s, null, LocaleContextHolder.getLocale());
                builder.append(s);
                if (iter.hasNext()) {
                    builder.append(" ,");
                }
            }
            message = builder.toString();
            apiException.setErrorMessage(message);
        } else {
            if (e.getResourceBundleCode() != null) {
                message = source.getMessage(e.getResourceBundleCode(), e.getArgs(), LocaleContextHolder.getLocale());
                apiException.setErrorMessage(message);
            } else {
                apiException.setErrorMessage(e.getMessage());
            }
        }

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(ServiceException e) {
        String code;
        String message;
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException();

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        if (e.getResourceBundleCode() != null) {
            message = source.getMessage(e.getResourceBundleCode(), e.getArgs(), LocaleContextHolder.getLocale());
            apiException.setErrorMessage(message);
        } else {
            apiException.setErrorMessage(e.getMessage());
        }

        return new ResponseEntity<>(apiException, httpStatus);
    }

    /**
     * Create custom error code
     *
     * @return custom error code
     */
    private String codeDefinition(Exception e, HttpStatus httpStatus) {
        String res = "00";
        String className = e.getStackTrace()[1].getClassName();
        int httpStatusCode = httpStatus.value();

//        boolean b = Arrays.stream(e.getStackTrace()).anyMatch(x -> x.getClassName().contains("tag"));
//        System.out.println(b);

        if(e instanceof BindException){
            System.out.println("bind");
            System.out.println(((BindException) e).getBindingResult());
            className = e.getMessage();
            System.out.println(e.getMessage());
        }

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
