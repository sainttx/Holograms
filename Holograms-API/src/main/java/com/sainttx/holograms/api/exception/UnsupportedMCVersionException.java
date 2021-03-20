package com.sainttx.holograms.api.exception;

public class UnsupportedMCVersionException extends RuntimeException {
    public UnsupportedMCVersionException() {
    }

    public UnsupportedMCVersionException(String message) {
        super(message);
    }

    public UnsupportedMCVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedMCVersionException(Throwable cause) {
        super(cause);
    }

    public UnsupportedMCVersionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
