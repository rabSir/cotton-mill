package com.ctmill.common.config;

import com.ctmill.interceptor.JwtValidateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Description 拦截器配置
 * @Author Zyaire
 * @Date 2023/4/24
 */
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Resource
    private JwtValidateInterceptor jwtValidateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(jwtValidateInterceptor);
        //拦截规则
        registration.addPathPatterns("/**")
                //排除拦截地址
                .excludePathPatterns(
                        "/user/login",
                        "/user/info",
                        "/user/logout",
                        "/doc.html",
                        "/webjars/**",
                        "/error",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/**"
                );
    }
}
