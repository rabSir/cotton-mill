package com.ctmill.common.vo;

import com.ctmill.common.exception.AppExceptionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 公共返回类
 * @Author Zyaire
 * @Date 2023/4/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {
    //返回码
    private Integer code;
    //返回消息
    private String message;
    //返回参数
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 成功返回结果信息，无数据信息
     * @param message
     * @return
     */
    public static<T> ResponseResult<T> success(String message){
        return new ResponseResult<>(200,message,null);
    }

    /**
     * 成功返回结果信息，有数据信息
     * @param message
     * @param data
     * @return
     */
    public static<T> ResponseResult<T> success(String message, T data){
        return new ResponseResult<>(200,message,data);
    }

    /**
     * 自定义全局异常，无数据返回
     * @param appExceptionEnum
     * @return
     */
    public static<T> ResponseResult<T> error(AppExceptionEnum appExceptionEnum){
        return new ResponseResult<>(appExceptionEnum.getCode(), appExceptionEnum.getMsg(),null);
    }

    /**
     * 失败返回结果信息，无数据信息
     * @param code
     * @param message
     * @return
     */
    public static<T> ResponseResult<T> error(Integer code,String message){
        return new ResponseResult<>(code,message,null);
    }

    /**
     * 自定义返回错误编码
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static<T> ResponseResult<T> error(Integer code,String message,T data){
        return new ResponseResult<>(code,message,data);
    }
}
