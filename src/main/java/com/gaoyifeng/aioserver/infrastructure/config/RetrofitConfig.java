package com.gaoyifeng.aioserver.infrastructure.config;

import com.gaoyifeng.aioserver.infrastructure.gateway.IWeixinApiGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Retrofit2配置类 - Infrastructure层
 * 配置微信API网关
 * 参考study项目Retrofit2Config实现
 */
@Slf4j
@Configuration
public class RetrofitConfig {

    private static final String WEIXIN_API_BASE_URL = "https://api.weixin.qq.com/";

    /**
     * 创建Retrofit实例用于微信API调用
     * @return Retrofit实例
     */
    @Bean
    public Retrofit weixinRetrofit() {
        log.info("初始化微信API Retrofit客户端，基础URL: {}", WEIXIN_API_BASE_URL);
        return new Retrofit.Builder()
                .baseUrl(WEIXIN_API_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    /**
     * 创建微信API网关实例
     * @param weixinRetrofit Retrofit实例
     * @return 微信API网关
     */
    @Bean
    public IWeixinApiGateway weixinApiGateway(Retrofit weixinRetrofit) {
        log.info("创建微信API网关实例");
        return weixinRetrofit.create(IWeixinApiGateway.class);
    }

}