package com.gaoyifeng.aioserver.infrastructure.dao.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户持久化对象
 * 对应数据库表结构
 */
@Data
public class UserPO {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}