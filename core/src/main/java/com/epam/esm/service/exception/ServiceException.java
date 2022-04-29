package com.epam.esm.service.exception;

public class ServiceException extends Exception{

    private String resourceBundleCode;
    private Object[] args;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceException(Throwable cause, String resourceBundleCode, Object[] args){
        this(cause);
        this.resourceBundleCode = resourceBundleCode;
        this.args = args;
    }

    public ServiceException(String message, Throwable cause, String resourceBundleCode, Object[] args){
        this(message, cause);
        this.resourceBundleCode = resourceBundleCode;
        this.args = args;
    }

    public ServiceException(String resourceBundleCode, Object[] args){
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
