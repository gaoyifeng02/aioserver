package com.gaoyifeng.aioserver.infrastructure.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Guava 配置类
 *
 * @author gaoyifeng
 */
@Configuration
public class GuavaConfig {

    /**
     * 配置默认缓存
     */
    @Bean("defaultCache")
    public com.google.common.cache.Cache<String, Object> defaultCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .recordStats()
                .build();
    }

    /**
     * 配置短期缓存（5分钟过期）
     */
    @Bean("shortTermCache")
    public com.google.common.cache.Cache<String, Object> shortTermCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .recordStats()
                .build();
    }

    /**
     * 配置长期缓存（24小时过期）
     */
    @Bean("longTermCache")
    public com.google.common.cache.Cache<String, Object> longTermCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .recordStats()
                .build();
    }
}