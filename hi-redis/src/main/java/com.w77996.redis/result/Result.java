package com.w77996.redis.result;

import lombok.Data;

/**
 * @description:
 * @author: w77996
 * @create: 2018-10-16 13:05
 **/
@Data
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <R> Result<R> success(R data) {
        Result<R> r = new Result<>();
        return r;
    }
}
