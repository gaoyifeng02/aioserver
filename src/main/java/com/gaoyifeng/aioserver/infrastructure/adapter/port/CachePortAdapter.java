package com.gaoyifeng.aioserver.infrastructure.adapter.port;

import com.gaoyifeng.aioserver.domain.weixin.adapter.port.ICachePort;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 缓存端口适配器实现 - Infrastructure层
 * 使用Guava Cache实现本地缓存
 *
 * @author gaoyifeng
 */
@Slf4j
@Component
public class CachePortAdapter implements ICachePort {

    private final Cache<String, Object> cache;

    public CachePortAdapter() {
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .recordStats()
                .build();
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            cache.put(key, value);
            log.debug("缓存设置成功，key：{}，过期时间：{} {}", key, timeout, timeUnit);
        } catch (Exception e) {
            log.error("缓存设置失败，key：{}", key, e);
        }
    }

    @Override
    public void put(String key, Object value) {
        put(key, value, 1, TimeUnit.HOURS);
    }

    @Override
    public Object get(String key) {
        try {
            Object value = cache.getIfPresent(key);
            log.debug("缓存查询，key：{}，结果：{}", key, value != null ? "命中" : "未命中");
            return value;
        } catch (Exception e) {
            log.error("缓存查询失败，key：{}", key, e);
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        Object value = get(key);
        if (value != null && clazz.isInstance(value)) {
            return (T) value;
        }
        return null;
    }

    @Override
    public boolean remove(String key) {
        try {
            cache.invalidate(key);
            log.debug("缓存删除成功，key：{}", key);
            return true;
        } catch (Exception e) {
            log.error("缓存删除失败，key：{}", key, e);
            return false;
        }
    }

    @Override
    public boolean exists(String key) {
        return cache.getIfPresent(key) != null;
    }

    @Override
    public void clear() {
        try {
            cache.invalidateAll();
            log.info("缓存清空成功");
        } catch (Exception e) {
            log.error("缓存清空失败", e);
        }
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        // Guava Cache 不支持单独设置过期时间，这里只是兼容接口
        log.warn("Guava Cache不支持单独设置过期时间，key：{}", key);
        return true;
    }

    /**
     * 获取缓存统计信息
     */
    public String getCacheStats() {
        return cache.stats().toString();
    }
}