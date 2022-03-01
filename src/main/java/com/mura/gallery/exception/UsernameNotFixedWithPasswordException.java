package com.mura.gallery.exception;

/**
 * @author Akutagawa Murasame
 * 用户名存在，但用户名密码不一致错误
 */
public class UsernameNotFixedWithPasswordException extends RuntimeException {
    public UsernameNotFixedWithPasswordException() {
        super();
    }

    public UsernameNotFixedWithPasswordException(String message) {
        super(message);
    }

    public UsernameNotFixedWithPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameNotFixedWithPasswordException(Throwable cause) {
        super(cause);
    }

    protected UsernameNotFixedWithPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
