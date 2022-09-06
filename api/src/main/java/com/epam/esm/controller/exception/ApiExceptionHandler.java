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
import org.springframework.http.converter.HttpMessageConverter;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.epam.esm.repository.constant.Constant.COLON;
import static com.epam.esm.repository.constant.Constant.DOT;
import static com.epam.esm.repository.constant.Constant.GIFT_CERTIFICATES;
import static com.epam.esm.repository.constant.Constant.LEFT_SQUARE_BRACKET;
import static com.epam.esm.repository.constant.Constant.ORDERS;
import static com.epam.esm.repository.constant.Constant.SPACE;
import static com.epam.esm.repository.constant.Constant.TAG;
import static com.epam.esm.repository.constant.Constant.USERS;

/**
 * Handles application exceptions
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    private static final String ERROR_INCORRECT_METHOD = "error.incorrect.method";
    private static final String INCORRECT_PATH = "incorrect.path";
    private static final String REQUIRED_REQUEST_BODY_IS_MISSING = "Required request body is missing";
    private static final String ERROR_REQUEST_BODY_MISSING = "error.request.body.missing";
    private static final String MUST_BE_WITH = "must.be.with";
    private static final String WORD_SIZE = "word.size";

    private final MessageSource source;

    public ApiExceptionHandler(MessageSource source) {
        this.source = source;
    }

    /**
     * Handle HttpMessageNotReadableException. Thrown by {@link HttpMessageConverter} implementations when the
     * {@link HttpMessageConverter#read} method fails.
     *
     * @param e HttpMessageNotReadableException
     * @return ApiException
     */
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
                resMes = ref.getFieldName() + COLON + getMessageForParse(inv.getTargetType());
            }
        } else {
            resMes = e.getMessage();
            if (resMes != null) {
                if (resMes.contains(REQUIRED_REQUEST_BODY_IS_MISSING)) {
                    resMes = source.getMessage(ERROR_REQUEST_BODY_MISSING, null,
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

    /**
     * Handle TypeMismatchException.
     * Exception raised while resolving a controller method argument.
     *
     * @param e MethodArgumentTypeMismatchException
     * @return ApiException
     */
    @ExceptionHandler
    public ResponseEntity<ApiException> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String code;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException();
        String resMes = e.getCause().getMessage();
        Class<?> reqType = e.getRequiredType();
        if (reqType != null) {
            resMes = e.getName() + COLON + getMessageForParse(e.getRequiredType());
        }

        code = codeDefinition(e, httpStatus);
        apiException.setErrorCode(code);
        apiException.setErrorMessage(resMes);

        return new ResponseEntity<>(apiException, httpStatus);
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param e NoHandlerFoundException.
     * @return ApiException
     */
    @ExceptionHandler
    public ResponseEntity<ApiException> handleNoHandlerFoundException(NoHandlerFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException();
        apiException.setErrorMessage(source.getMessage(INCORRECT_PATH, new Object[]{e.getMessage()}, LocaleContextHolder.getLocale()));
        apiException.setErrorCode(codeDefinition(e, httpStatus));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    /**
     * Handle HttpRequestMethodNotSupportedException.
     * Exception thrown when a request handler does not support a specific request method.
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return ApiException
     */
    @ExceptionHandler
    public ResponseEntity<ApiException> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        ApiException apiException = new ApiException();
        apiException.setErrorMessage(source.getMessage(ERROR_INCORRECT_METHOD, null, LocaleContextHolder.getLocale()));
        apiException.setErrorCode(codeDefinition(e, httpStatus));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    /**
     * Handle ConstraintViolationException.
     * Exception reports the result of constraint violations.
     *
     * @param e ConstraintViolationException.
     * @return ApiException.
     */
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
            if (leafNode.getParent().toString().contains(LEFT_SQUARE_BRACKET)) {
                fieldName = leafNode.getParent().asString() + DOT + leafNode.asString();
            } else {
                fieldName = leafNode.asString();
            }
            errors.put(fieldName, vio.getMessage());
        });

        apiException.setErrorMessage(errors.toString());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    /**
     * Handle MethodArgumentNotValidException
     * Exception to be thrown when validation on an argument annotated with @Valid fails. Extends
     *
     * @param e MethodArgumentNotValidException
     * @return ApiException.
     */
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

    /**
     * Handle ValidateException.
     * Exception throws by validator when incorrect parameters.
     *
     * @param e ValidateException.
     * @return ApiException.
     */
    @ExceptionHandler
    public ResponseEntity<ApiException> handleValidateException(ValidateException e) {
        String message;
        String code;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
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
     * Handle ServiceException.
     * Exception throws by service layer.
     *
     * @param e ServiceException.
     * @return ApiException.
     */
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

    /**
     * Handle DataIntegrityViolationException.
     * Exception thrown when an attempt to insert or update data results in violation of an integrity constraint.
     *
     * @param e DataIntegrityViolationException.
     * @return ApiException.
     */
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
     * Handle other Exception
     *
     * @param e Exception
     * @return ApiException
     */
    @ExceptionHandler
    public ResponseEntity<ApiException> handleException(Exception e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException();
        apiException.setErrorMessage(e.getMessage());
        apiException.setErrorCode(codeDefinition(e, httpStatus));
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

        if (path.contains(GIFT_CERTIFICATES)) {
            res = "01";
        } else if (path.contains(TAG)) {
            res = "02";
        } else if (path.contains(USERS)) {
            res = "03";
        }

        if (path.contains(ORDERS)) {
            res = "04";
        }

        res = httpStatusCode + res;
        return res;
    }

    private String getMessageForParse(Class<?> clazz) {
        String lowCaseClassName = clazz.getSimpleName().toLowerCase();

        return source.getMessage(MUST_BE_WITH, null, LocaleContextHolder.getLocale()) +
                SPACE + lowCaseClassName + SPACE +
                source.getMessage(WORD_SIZE, null, LocaleContextHolder.getLocale());
    }
}
