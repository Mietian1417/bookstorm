package com.xiyu.demo.result;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-09-26
 * Time: 17:06
 *
 * @author 陈子豪
 */
public class Result<T> {
    public int code;
    public String message;
    public T data;

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(0, "success", data);
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return new Result<T>(errorCode.code, errorCode.message);
    }
}
