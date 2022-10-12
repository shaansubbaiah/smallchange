package org.smallchange.dao;

public class DatabaseException extends RuntimeException {
    private static final long serialVersionUID = -7377410647937045423L;
    public DatabaseException() {
    }
    public DatabaseException(String message) {
        super(message);
    }
    public DatabaseException(Throwable cause) {
        super(cause);
    }
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
    protected DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}