package com.gaoyifeng.aioserver.api.dto.auth.response;

import lombok.Data;

/**
 * 登录状态检查响应DTO
 * 参考study项目：IAuthService.checkLogin(String ticket)返回值
 */
@Data
public class LoginCheckResponseDto {

    /**
     * 登录状态：true-已登录，false-未登录
     */
    private Boolean isLoggedIn;

    /**
     * 用户OpenID（如果已登录）
     */
    private String openId;

    /**
     * 用户Token（如果已登录，用于后续认证）
     */
    private String token;

    /**
     * 状态描述信息
     */
    private String message;

    /**
     * 构造函数
     */
    public LoginCheckResponseDto() {
    }

    /**
     * 构造函数 - 未登录状态
     */
    public LoginCheckResponseDto(String message) {
        this.isLoggedIn = false;
        this.message = message;
    }

    /**
     * 构造函数 - 已登录状态
     * @param openId 用户OpenID
     * @param token 用户Token
     */
    public LoginCheckResponseDto(String openId, String token) {
        this.isLoggedIn = true;
        this.openId = openId;
        this.token = token;
        this.message = "登录成功";
    }

    /**
     * 创建未登录响应
     * @param message 状态消息
     * @return 未登录响应
     */
    public static LoginCheckResponseDto notLoggedIn(String message) {
        return new LoginCheckResponseDto(message);
    }

    /**
     * 创建已登录响应
     * @param openId 用户OpenID
     * @param token 用户Token
     * @return 已登录响应
     */
    public static LoginCheckResponseDto loggedIn(String openId, String token) {
        return new LoginCheckResponseDto(openId, token);
    }

    /**
     * 获取摘要信息
     * @return 摘要字符串
     */
    public String getSummary() {
        return String.format("LoginCheckResponse{isLoggedIn=%s, hasOpenId=%s, hasToken=%s, message='%s'}",
                isLoggedIn,
                openId != null && !openId.isEmpty(),
                token != null && !token.isEmpty(),
                message);
    }
}