package com.mura.gallery.exception;

public class NotThePictureException extends NotTheFileException {
    public NotThePictureException() {
        super();
    }

    public NotThePictureException(String message) {
        super(message);
    }

    public NotThePictureException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotThePictureException(Throwable cause) {
        super(cause);
    }

    protected NotThePictureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
