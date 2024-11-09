package com.huskyui.chatserver.exception;


public class CommonException extends RuntimeException{
    private int code;
    private String msg;

    public CommonException( int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
