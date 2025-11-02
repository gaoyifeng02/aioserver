package com.gaoyifeng.aioserver.api.dto.auth;

import lombok.Data;

/**
 * 用户DTO - 包含token信息
 */
@Data
public class UserDto {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户token（登录后返回）
     */
    private String token;

    /**
     * 用户类型（可选字段）
     */
    private String userType = "normal";

    /**
     * 登录时间（可选字段）
     */
    private Long loginTime;
}
