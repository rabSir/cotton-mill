package com.ctmill.interceptor;

import com.alibaba.fastjson2.JSON;
import com.ctmill.common.exception.AppExceptionEnum;
import com.ctmill.common.utils.JwtUtil;
import com.ctmill.common.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description Jwt验证拦截器
 * @Author Zyaire
 * @Date 2023/4/24
 */
@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        if (token != null){
            try {
                jwtUtil.parseToken(token);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.debug(request.getRequestURI()+"验证失败,禁止访问");
        response.setContentType("application/json;charset=utf-8");
        ResponseResult<String> result = ResponseResult.error(AppExceptionEnum.ILLEGAL_TOKEN);
        response.getWriter().write(JSON.toJSONString(result));
        return false;
    }
}
