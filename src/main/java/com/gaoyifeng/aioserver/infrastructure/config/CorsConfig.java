package com.gaoyifeng.aioserver.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * 跨域配置类
 * 配置CORS允许跨域请求
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 配置跨域映射
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有路径
                .allowedOrigins("*") // 允许所有来源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD") // 允许的HTTP方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(false) // 当allowedOrigins为"*"时，allowCredentials必须为false
                .maxAge(3600) // 预检请求的有效期，单位秒
                .exposedHeaders("Content-Type", "Authorization", "X-Requested-With"); // 暴露的响应头
    }

    /**
     * 配置CORS源
     * 提供更灵活的跨域配置
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 允许的来源
        configuration.setAllowedOrigins(Arrays.asList("*"));

        // 允许的HTTP方法
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"
        ));

        // 允许的请求头
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // 当允许所有来源时，不允许携带Cookie
        configuration.setAllowCredentials(false);

        // 暴露的响应头
        configuration.setExposedHeaders(Arrays.asList(
                "Content-Type", "Authorization", "X-Requested-With",
                "Access-Control-Allow-Origin"
        ));

        // 预检请求缓存时间
        configuration.setMaxAge(3600L);

        // 注册配置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}