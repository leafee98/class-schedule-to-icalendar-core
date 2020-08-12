package com.github.leafee98.CSTI.core.exceptions;

public class InvalidScheduleFileStruct extends RuntimeException {

    private static final long serialVersionUID = -1553216499745121336L;

    public InvalidScheduleFileStruct() {
        super();
    }

    public InvalidScheduleFileStruct(String message) {
        super(message);
    }

    public InvalidScheduleFileStruct(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidScheduleFileStruct(Throwable cause) {
        super(cause);
    }

    protected InvalidScheduleFileStruct(String message, Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
