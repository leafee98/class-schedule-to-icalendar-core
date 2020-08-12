package com.github.leafee98.CSTI.core.exceptions;

public class InvalidComponentParams extends RuntimeException {

    private static final long serialVersionUID = -995228257258251568L;

    public InvalidComponentParams() {
        super();
    }

    public InvalidComponentParams(String message) {
        super(message);
    }

    public InvalidComponentParams(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidComponentParams(Throwable cause) {
        super(cause);
    }

    protected InvalidComponentParams(String message, Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
