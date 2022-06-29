package com.epam.esm.controller.exception;

import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.hibernate.validator.internal.engine.path.PathImpl;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        Throwable tr = e.getCause();
        String resMes = e.getCause().getMessage();

        if (tr instanceof InvalidFormatException) {
            InvalidFormatException inv = (InvalidFormatException) tr;
            JsonMappingException.Reference ref = inv.getPath().get(0);
            resMes = ref.getFieldName() + ":" + getMessageForParse(inv.getTargetType());
        }

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        apiException.setErrorMessage(resMes);

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(MethodArgumentTypeMismatchException e) {
        String code;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException();
        String resMes = e.getCause().getMessage();
        Class<?> reqType = e.getRequiredType();
        if (reqType != null) {
            resMes = e.getName() + ":" + getMessageForParse(e.getRequiredType());
        }

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        apiException.setErrorMessage(resMes);

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

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(ConstraintViolationException e) {
        String code;
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        ApiException apiException = new ApiException();

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        Set<ConstraintViolation<?>> exceptions = e.getConstraintViolations();
        Map<String, String> errors = new HashMap<>();

        exceptions.forEach(vio -> errors.put(((PathImpl)vio.getPropertyPath()).getLeafNode().toString(), vio.getMessage()));

        apiException.setErrorMessage(errors.toString());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(MethodArgumentNotValidException e) {
        String code;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException();

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);

        Map<String, String> errors = new HashMap<>();
        e.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        apiException.setErrorMessage(errors.toString());
        return new ResponseEntity<>(apiException, httpStatus);
    }

//    @ExceptionHandler
//    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException e) {
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//
//        LinkedHashMap<String, String> errors = new LinkedHashMap<>();
//        e.getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//
//        errors.put("errorCode", codeDefinition(e, httpStatus));
//        return errors;
//    }

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
        int httpStatusCode = httpStatus.value();

//        String mes = e.getStackTrace()[1].getClassName();
//        boolean b = Arrays.stream(e.getStackTrace()).anyMatch(x -> x.getClassName().contains("tag"));
//        System.out.println(b);
//
//        if (e instanceof BindException || e instanceof HttpMessageNotReadableException) {
//            mes = e.getMessage();
//        }
//
//        if (mes.contains("GiftCertificate")) {
//            res = "01";
//        } else if (mes.contains("Tag")) {
//            res = "02";
//        }

//        if (res.equals("00")) {
//            String path = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
//            assert path != null;
//            if (path.contains("gift-certificates")) {
//                res = "01";
//            } else if (path.contains("tag")) {
//                res = "02";
//            }
//        }

        String path = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
        assert path != null;
        if (path.contains("gift-certificates")) {
            res = "01";
        } else if (path.contains("tag")) {
            res = "02";
        }

        res = httpStatusCode + res;

        return res;
    }

    private String getMessageForParse(Class<?> clazz) {
        String lowCaseClassName = clazz.getSimpleName().toLowerCase();
        StringBuilder sb = new StringBuilder();

        sb.append(source.getMessage("must.be.with", null, LocaleContextHolder.getLocale()));
        sb.append(" ").append(lowCaseClassName).append(" ");
        sb.append(source.getMessage("word.size", null, LocaleContextHolder.getLocale()));

        return sb.toString();
    }
}
