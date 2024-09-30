package com.huskyui.chatserver.exception;


public class CommonException extends RuntimeException{
    private int code;
    private String msg;

    public CommonException(String message, int code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }
}
