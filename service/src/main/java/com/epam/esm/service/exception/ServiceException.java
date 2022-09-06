package com.epam.esm.service.exception;
/**
 * Throws by service layer
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class ServiceException extends Exception{

    /**
     * The message code for i18n to look up, e.g. 'calculator.noRateSet'.
     * MessageSource users are encouraged to base message names on qualified class or package names,
     * avoiding potential conflicts and ensuring maximum clarity.
     */
    private String resourceBundleCode;
    /**
     *  An array of arguments that will be filled in for params within the message
     *  (params look like "{0}", "{1,date}", "{2,time}" within a message), or null if none
     */
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

    public ServiceException(Throwable cause, String resourceBundleCode, Object...args){
        this(cause);
        this.resourceBundleCode = resourceBundleCode;
        this.args = args;
    }

    public ServiceException(String message, Throwable cause, String resourceBundleCode, Object...args){
        this(message, cause);
        this.resourceBundleCode = resourceBundleCode;
        this.args = args;
    }

    public ServiceException(String resourceBundleCode, Object...args){
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
