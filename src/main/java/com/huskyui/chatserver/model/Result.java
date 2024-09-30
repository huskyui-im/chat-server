package com.huskyui.chatserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    /**
     * 是否成功
     **/
    private int code;
    /**
     * 错误信息
     **/
    private String errorMsg;
    /**
     * 请求状态 200-成功 400-失败
     **/
    private Integer status;

    /**
     * 当前时间戳
     **/
    private Long timestamp;
    /**
     * 返回结果
     **/
    private Object data;

    public static Result ok() {
        return new Result(200, null, 200, System.currentTimeMillis(),null);
    }

    public static Result ok(Object data) {
        return new Result(200, null, 200,System.currentTimeMillis(),data);
    }

    public static Result fail(int code,String errorMsg) {
        return new Result(code, errorMsg, 400,System.currentTimeMillis(),null);
    }

}
