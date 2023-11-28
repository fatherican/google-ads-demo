package com.ads.google.exception;

/**
 * @author xiaokai
 */
public class AppException extends RuntimeException{

    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }
}
