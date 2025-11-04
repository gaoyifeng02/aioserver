package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.UserPO;
import org.apache.ibatis.annotations.*;

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
    @Select("SELECT id, username, password, create_time, update_time FROM user WHERE username = #{username} LIMIT 1")
    UserPO findByUsername(String username);

    /**
     * 根据ID查找用户
     * @param id 用户ID
     * @return 用户PO
     */
    @Select("SELECT id, username, password, create_time, update_time FROM user WHERE id = #{id} LIMIT 1")
    UserPO findById(String id);

    /**
     * 插入新用户
     * @param userPO 用户PO
     * @return 影响行数
     */
    @Insert("INSERT INTO user (id, username, password, create_time, update_time) VALUES (#{userPO.id}, #{userPO.username}, #{userPO.password}, #{userPO.createTime}, #{userPO.updateTime})")
    int insert(@Param("userPO") UserPO userPO);

    /**
     * 更新用户信息
     * @param userPO 用户PO
     * @return 影响行数
     */
    @Update("UPDATE user SET username = #{userPO.username}, password = #{userPO.password}, update_time = #{userPO.updateTime} WHERE id = #{userPO.id}")
    int update(@Param("userPO") UserPO userPO);

    /**
     * 根据用户名统计数量
     * @param username 用户名
     * @return 数量
     */
    @Select("SELECT COUNT(1) FROM user WHERE username = #{username}")
    int countByUsername(String username);

    /**
     * 根据ID统计数量
     * @param id 用户ID
     * @return 数量
     */
    @Select("SELECT COUNT(1) FROM user WHERE id = #{id}")
    int countById(String id);
}