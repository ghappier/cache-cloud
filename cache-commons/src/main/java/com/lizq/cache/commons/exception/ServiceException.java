package com.lizq.cache.commons.exception;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;

    public ServiceException() {
        super("业务异常");
    }

    public ServiceException(String msg) {
        super(msg);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
