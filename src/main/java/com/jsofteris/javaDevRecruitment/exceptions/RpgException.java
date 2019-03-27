package com.jsofteris.javaDevRecruitment.exceptions;

public class RpgException extends RuntimeException {
    public RpgException(String message) {
        super(message);
    }

    public RpgException(Throwable cause) {
        super(cause);
    }

    public RpgException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpgException() {
    }
}
