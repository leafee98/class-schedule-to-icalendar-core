package com.github.leafee98.CSTI.core.exceptions;

public class InvalidPropertyParams extends RuntimeException {

    private static final long serialVersionUID = -995228257258251568L;

    public InvalidPropertyParams() {
        super();
    }

    public InvalidPropertyParams(String message) {
        super(message);
    }

    public InvalidPropertyParams(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPropertyParams(Throwable cause) {
        super(cause);
    }

    protected InvalidPropertyParams(String message, Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
