package com.github.leafee98.CSTI.core.exceptions;

public class InvalidLessonSchedule extends RuntimeException {

    private static final long serialVersionUID = -8237491283000239416L;

    public InvalidLessonSchedule() {
        super();
    }

    public InvalidLessonSchedule(String message) {
        super(message);
    }

    public InvalidLessonSchedule(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLessonSchedule(Throwable cause) {
        super(cause);
    }

    protected InvalidLessonSchedule(String message, Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
