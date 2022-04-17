package com.example.store.service.ex;

/** 非法访问的异常 */
public class AccessDeniedException1 extends ServiceException {
    public AccessDeniedException1() {
        super();
    }

    public AccessDeniedException1(String message) {
        super(message);
    }

    public AccessDeniedException1(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException1(Throwable cause) {
        super(cause);
    }

    protected AccessDeniedException1(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
