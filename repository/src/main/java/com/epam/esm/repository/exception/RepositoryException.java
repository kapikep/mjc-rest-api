package com.epam.esm.repository.exception;

/**
 * Throws by repository layer
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class RepositoryException extends Exception {
    public RepositoryException() {
        super();
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
