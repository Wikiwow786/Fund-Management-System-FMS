package com.fms.exception;

public class FmsException extends RuntimeException{
    public FmsException(String errorCode, String errorMessage) {
        super(String.format(errorCode + ". " + errorMessage));
    }

    public FmsException(String errorMessage) {
        super(errorMessage);
    }
}
