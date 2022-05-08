package com.epam.esm.service.exception;

import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ValidateException extends Exception{
    private String resourceBundleCode;
    private Object[] args;
    List<String> resourceBundleCodeList;

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

    public ValidateException(String resourceBundleCode, @Nullable Object...args){
        this.resourceBundleCode = resourceBundleCode;
        this.args = args;
    }

    public ValidateException(Throwable cause, String message, List<String> resourceBundleCodeList) {
        this(cause, message);
        this.resourceBundleCodeList = resourceBundleCodeList;
    }

    public ValidateException(List<String> resourceBundleCodeList) {
        this.resourceBundleCodeList = resourceBundleCodeList;
    }

    public String getResourceBundleCode() {
        return resourceBundleCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public List<String> getResourceBundleCodeList() {
        return resourceBundleCodeList;
    }
}
