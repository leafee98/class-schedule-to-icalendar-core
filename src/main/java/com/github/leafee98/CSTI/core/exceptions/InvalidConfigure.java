package com.github.leafee98.CSTI.core.exceptions;

public class InvalidConfigure extends RuntimeException {

    private static final long serialVersionUID = -5132496754132654987L;

    public InvalidConfigure() {
        super();
    }

    public InvalidConfigure(String message) {
        super(message);
    }

    public InvalidConfigure(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidConfigure(Throwable cause) {
        super(cause);
    }

    protected InvalidConfigure(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
