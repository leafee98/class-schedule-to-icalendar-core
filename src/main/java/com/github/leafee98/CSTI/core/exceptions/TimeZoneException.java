package com.github.leafee98.CSTI.core.exceptions;

public class TimeZoneException extends RuntimeException {

    private static final long serialVersionUID = 546781747196321695L;

    public TimeZoneException() {
        super();
    }

    public TimeZoneException(String message) {
        super(message);
    }

    public TimeZoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeZoneException(Throwable cause) {
        super(cause);
    }

    protected TimeZoneException(String message, Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
