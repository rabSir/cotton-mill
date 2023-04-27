package com.ctmill.common.exception;

import com.ctmill.common.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @Description 全局异常统一处理类
 * @Author Zyaire
 * @Date 2023/4/27
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private  static  final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 处理自定义业务异常
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(value = AppException.class)
    @ResponseBody
    public <T> ResponseResult<T> exceptionHandler(HttpServletRequest req,AppException e) {
        logger.error("发生业务异常！原因：{}",e.getMsg());
        return ResponseResult.error(e.getCode(), e.getMsg());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public <T> ResponseResult<T> exceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因：",e);
        return ResponseResult.error(AppExceptionEnum.BODY_NOT_MATCH);
    }

    /**
     * 处理数字格式化异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NumberFormatException.class)
    @ResponseBody
    public <T> ResponseResult<T> exceptionHandler(HttpServletRequest req, NumberFormatException e){
        logger.error("数字格式化异常！原因：",e);
        return ResponseResult.error(AppExceptionEnum.PARAMS_NOT_CONVERT);
    }

    /**
     * 未知异常统一处理
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public <T> ResponseResult<T> exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("发生未知异常(未捕获异常)！原因：",e);
        return ResponseResult.error(AppExceptionEnum.INTERNAL_SERVER_ERROR);
    }

}
