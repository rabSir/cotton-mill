package com.ctmill.common.exception;


/**
 * 业务异常处理，枚举类
 */
public enum AppExceptionEnum {
    USERNAME_NOT_EXISTS(10001,"用户名不存在"),
    BODY_NOT_MATCH(40000,"请求的数据格式不符"),
    PARAMS_NOT_CONVERT(40002,"类型转换不正确"),
    ILLEGAL_TOKEN(50008,"非法令牌"),
    OTHER_CLIENTS_LOGGED_IN(50012,"其他客户端登录"),
    TOKEN_EXPIRED(50014,"令牌过期"),
    INTERNAL_SERVER_ERROR(501, "服务器内部错误");


    private final Integer code ;
    private final String msg ;

    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

    AppExceptionEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
