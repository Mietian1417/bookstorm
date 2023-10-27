package com.xiyu.demo.exception;


import com.xiyu.demo.result.ErrorCode;

/**
 * 定义异常类的信息
 *      有了这个类之后只要自定义自己想要的异常类, 再帮父类构造一下构造方法即可
 */
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-09-24
 * Time: 16:17
 *
 * @author 陈子豪
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final ErrorCode message;

    public CustomException(ErrorCode message) {
        super(message.toString());
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return message;
    }

}

