package com.xiyu.demo.result;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-09-28
 * Time: 18:58
 *
 * @author 陈子豪
 */
public class ErrorCode {
    public int code;
    public String message;

    public ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static final ErrorCode BOOK_NOT_FOUND = new ErrorCode(-1, "书籍不存在! ");

    public static final ErrorCode USER_EXISTS = new ErrorCode(-1, "用户名已存在! ");

    public static final ErrorCode USER_ADD_FAIL = new ErrorCode(-1, "添加用户失败, 请稍后再试! ");

    public static final ErrorCode USER_OR_PASSWORD_ERROR = new ErrorCode(-1, "用户名或密码错误! ");

    public static final ErrorCode VERIFYCODECODE_ERROR = new ErrorCode(-2, "验证码校验失败, 请重新输入! ");

    public static final ErrorCode BIND_ERROR = new ErrorCode(500101, "参数校验异常: %s !");

    public static final ErrorCode SERVER_ERROR = new ErrorCode(500101, "服务端异常! ");

    public static final ErrorCode DATABASE_ERROR = new ErrorCode(500102, "数据库异常! ");

    public static final ErrorCode UPLOAD_ERROR = new ErrorCode(500103, "图片上传失败!");

    public static final ErrorCode UPLOAD_TIMEOUT = new ErrorCode(500104, "图片上传时间过长, 已在后继续上传! ");

    public static final ErrorCode PASSWORDS_DO_NOT_MATCH = new ErrorCode(-1, "两次密码不一致! ");

    public static final ErrorCode OLD_PASSWORD_ERROR = new ErrorCode(-1, "密码错误! ");

    public static final ErrorCode ADDRESS_EXIST_ORDER_ERROR = new ErrorCode(-1, "该地址已经存在订单, 不可删除! ");;



    // BIND_ERROR, 自定义注解参数校验异常, 异常信息填充
    public ErrorCode fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.message, args);
        return new ErrorCode(code, message);
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    // @Validated 注解参数校验异常, 异常信息填充
    public ErrorCode subArgs(String message) {
        int code = this.code;
        message = message.substring(message.indexOf(":") + 2);
        return new ErrorCode(code, message);
    }
}
