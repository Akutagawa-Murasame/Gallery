package com.mura.gallery.exception;

/**
 * @author Akutagawa Murasame
 */
public class NotTheFileException extends RuntimeException {
    public NotTheFileException() {
        super();
    }

    public NotTheFileException(String message) {
        super(message);
    }

    public NotTheFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotTheFileException(Throwable cause) {
        super(cause);
    }

    protected NotTheFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
