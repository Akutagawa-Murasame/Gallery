package com.mura.gallery.exception;

/**
 * @author Akutagawa Murasame
 */
public class RegistrationFailedException extends RuntimeException {
    public RegistrationFailedException() {
    }

    public RegistrationFailedException(String message) {
        super(message);
    }

    public RegistrationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationFailedException(Throwable cause) {
        super(cause);
    }

    public RegistrationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
