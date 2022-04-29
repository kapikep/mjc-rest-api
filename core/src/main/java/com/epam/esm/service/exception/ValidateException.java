package com.epam.esm.service.exception;

import org.springframework.lang.Nullable;

public class ValidateException extends Exception{
    private String resourceBundleCode;
    private Object[] args;

    public ValidateException() {
        super();
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(Throwable cause, String message) {
        super(message, cause);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    protected ValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ValidateException(Throwable cause, String resourceBundleCode, Object[] args){
        this(cause);
        this.resourceBundleCode = resourceBundleCode;
        this.args = args;
    }

    public ValidateException(String message, Throwable cause, String resourceBundleCode, @Nullable Object[] args){
        this(cause, message);
        this.resourceBundleCode = resourceBundleCode;
        this.args = args;
    }

    public ValidateException(String resourceBundleCode, @Nullable Object[] args){
        this.resourceBundleCode = resourceBundleCode;
        this.args = args;
    }

    public String getResourceBundleCode() {
        return resourceBundleCode;
    }

    public Object[] getArgs() {
        return args;
    }
}
