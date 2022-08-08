package com.epam.esm.controller.exception;

import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
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
    public ResponseEntity<ApiException> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String code;
        String resMes;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException();
        Throwable tr = e.getCause();
        if (tr != null) {
            resMes = e.getCause().getMessage();

            if (tr instanceof InvalidFormatException) {
                InvalidFormatException inv = (InvalidFormatException) tr;
                JsonMappingException.Reference ref = inv.getPath().get(0);
                resMes = ref.getFieldName() + ":" + getMessageForParse(inv.getTargetType());
            }
        } else {
            resMes = e.getMessage();
            if (resMes != null) {
                if (resMes.contains("Required request body is missing")) {
                    resMes = source.getMessage("error.request.body.missing", null,
                            LocaleContextHolder.getLocale());
                }
            }
        }
        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        apiException.setErrorMessage(resMes);

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleNullPointerException(NullPointerException e) {
        return null;
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
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
    public ResponseEntity<ApiException> handleNoHandlerFoundException(NoHandlerFoundException e) {
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
    public ResponseEntity<ApiException> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
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
    public ResponseEntity<ApiException> handleConstraintViolationException(ConstraintViolationException e) {
        String code;
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        ApiException apiException = new ApiException();

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        Set<ConstraintViolation<?>> exceptions = e.getConstraintViolations();
        Map<String, String> errors = new HashMap<>();

        exceptions.forEach(vio -> {
            NodeImpl leafNode = ((PathImpl) vio.getPropertyPath()).getLeafNode();
            String fieldName;
            if (leafNode.getParent().toString().contains("[")) {
                fieldName = leafNode.getParent().asString() + "." + leafNode.asString();
            } else {
                fieldName = leafNode.asString();
            }
            errors.put(fieldName, vio.getMessage());
        });

        apiException.setErrorMessage(errors.toString());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
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

    @ExceptionHandler
    public ResponseEntity<ApiException> handleValidateException(ValidateException e) {
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
    public ResponseEntity<ApiException> handleServiceException(ServiceException e) {
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

    @ExceptionHandler
    public ResponseEntity<ApiException> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String code;
        String message = null;
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException();

        Throwable root = e.getCause().getCause();

        if (root instanceof SQLIntegrityConstraintViolationException) {
            message = root.getMessage();
        }

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        if (message == null) {
            apiException.setErrorMessage(e.getMessage());
        } else {
            apiException.setErrorMessage(message);
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
        String path = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
        assert path != null;

        if (path.contains("gift-certificates")) {
            res = "01";
        } else if (path.contains("tag")) {
            res = "02";
        } else if (path.contains("users")) {
            res = "03";
        }

        if (path.contains("orders")) {
            res = "04";
        }

        res = httpStatusCode + res;
        return res;
    }

    private String getMessageForParse(Class<?> clazz) {
        String lowCaseClassName = clazz.getSimpleName().toLowerCase();

        String sb = source.getMessage("must.be.with", null, LocaleContextHolder.getLocale()) +
                " " + lowCaseClassName + " " +
                source.getMessage("word.size", null, LocaleContextHolder.getLocale());

        return sb;
    }
}
