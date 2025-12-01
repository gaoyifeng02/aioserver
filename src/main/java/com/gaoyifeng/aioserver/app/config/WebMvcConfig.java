package com.gaoyifeng.aioserver.app.config;

import com.gaoyifeng.aioserver.infrastructure.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置类
 * 注册拦截器等配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")  // 拦截所有路径
                .excludePathPatterns(
                        "/api/v1/idaas/auth/login",      // 登录接口
                        "/api/v1/idaas/auth/register",   // 注册接口
                        "/api/demo/**",                   // Demo接口
                        "/error",                         // 错误页面
                        "/swagger-ui/**",                 // Swagger UI
                        "/swagger-resources/**",          // Swagger资源
                        "/v2/api-docs",                   // API文档
                        "/webjars/**"                     // WebJars资源
                );
    }
}