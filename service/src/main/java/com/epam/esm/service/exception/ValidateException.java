package com.epam.esm.service.exception;

import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Throws by validator when incorrect parameters.
 *
 * @author Artsemi Kapitula.
 * @version 1.0
 */
public class ValidateException extends Exception{

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
    /**
     * List messages for i18n to look up, e.g. 'calculator.noRateSet'.
     * MessageSource users are encouraged to base message names on qualified class or package names,
     * avoiding potential conflicts and ensuring maximum clarity.
     */
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
