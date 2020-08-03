package com.github.leafee98.CSTI.core.exceptions;

public class InvalidLessonRange extends RuntimeException {

    private static final long serialVersionUID = -3784919202339592450L;
    
    public InvalidLessonRange() {
        super();
    }

    public InvalidLessonRange(String message) {
        super(message);
    }

    public InvalidLessonRange(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLessonRange(Throwable cause) {
        super(cause);
    }

    protected InvalidLessonRange(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
