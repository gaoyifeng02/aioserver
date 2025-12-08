package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据访问映射器
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户PO
     */
    UserPO findByUsername(String username);

    /**
     * 根据ID查找用户
     * @param id 用户ID
     * @return 用户PO
     */
    UserPO findById(String id);

    /**
     * 插入新用户
     * @param userPO 用户PO
     * @return 影响行数
     */
    int insert(UserPO userPO);

    /**
     * 更新用户信息
     * @param userPO 用户PO
     * @return 影响行数
     */
    int update(UserPO userPO);

    /**
     * 根据用户名统计数量
     * @param username 用户名
     * @return 数量
     */
    int countByUsername(String username);

    /**
     * 根据ID统计数量
     * @param id 用户ID
     * @return 数量
     */
    int countById(String id);
}