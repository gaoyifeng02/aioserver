package com.gaoyifeng.aioserver.domain.auth.adapter.port;

import com.gaoyifeng.aioserver.domain.auth.model.entity.User;

/**
 * 用户仓储接口 - Domain层
 * 定义用户数据访问的抽象接口
 */
public interface IUserRepository {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户实体，如果不存在返回null
     */
    User findByUsername(String username);

    /**
     * 根据用户ID查找用户
     * @param id 用户ID
     * @return 用户实体，如果不存在返回null
     */
    User findById(String id);

    /**
     * 保存用户实体
     * @param user 用户实体
     */
    void save(User user);

    /**
     * 根据用户名判断用户是否存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 根据用户ID判断用户是否存在
     * @param id 用户ID
     * @return 是否存在
     */
    boolean existsById(String id);
}