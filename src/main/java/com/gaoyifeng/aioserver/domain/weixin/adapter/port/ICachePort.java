package com.gaoyifeng.aioserver.domain.weixin.adapter.port;

import java.util.concurrent.TimeUnit;

/**
 * 缓存端口接口 - Domain层
 * 定义缓存操作的能力契约
 *
 * @author gaoyifeng
 */
public interface ICachePort {

    /**
     * 存储缓存项
     * @param key 缓存键
     * @param value 缓存值
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     */
    void put(String key, Object value, long timeout, TimeUnit timeUnit);

    /**
     * 存储缓存项（永不过期）
     * @param key 缓存键
     * @param value 缓存值
     */
    void put(String key, Object value);

    /**
     * 获取缓存项
     * @param key 缓存键
     * @return 缓存值
     */
    Object get(String key);

    /**
     * 获取缓存项（指定类型）
     * @param key 缓存键
     * @param clazz 返回值类型
     * @param <T> 泛型类型
     * @return 缓存值
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 删除缓存项
     * @param key 缓存键
     * @return 是否删除成功
     */
    boolean remove(String key);

    /**
     * 检查缓存项是否存在
     * @param key 缓存键
     * @return 是否存在
     */
    boolean exists(String key);

    /**
     * 清空所有缓存
     */
    void clear();

    /**
     * 设置过期时间
     * @param key 缓存键
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     * @return 是否设置成功
     */
    boolean expire(String key, long timeout, TimeUnit timeUnit);
}