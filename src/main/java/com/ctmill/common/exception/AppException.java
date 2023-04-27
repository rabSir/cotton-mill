package com.ctmill.common.exception;

/**
 * @Description 自定义异常类
 * @Author Zyaire
 * @Date 2023/4/27
 */
public class AppException extends RuntimeException{
    protected Integer code;
    protected String errorMsg;


    public AppException(AppExceptionEnum appExceptionEnum){
        super();
        this.code = appExceptionEnum.getCode();
        this.errorMsg = appExceptionEnum.getMsg();

    }

    public AppException(Integer code,String msg){
        super();
        this.code = code;
        this.errorMsg = msg;

    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return errorMsg;
    }

}
