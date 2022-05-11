package com.epam.esm.controller.exception;

/**
 * Throws by Api layer
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class ApiException {
    private String errorMessage;
    private String errorCode;

    public ApiException() {
    }

    public ApiException(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
