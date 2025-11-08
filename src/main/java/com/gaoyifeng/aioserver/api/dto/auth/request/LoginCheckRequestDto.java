package com.gaoyifeng.aioserver.api.dto.auth.request;

import lombok.Data;

/**
 * 登录状态检查请求DTO
 * 参考study项目：IAuthService.checkLogin(String ticket)参数
 */
@Data
public class LoginCheckRequestDto {

    /**
     * 登录票据（由createWeixinQrCode()生成）
     */
    private String ticket;

    /**
     * 构造函数
     */
    public LoginCheckRequestDto() {
    }

    /**
     * 构造函数
     * @param ticket 登录票据
     */
    public LoginCheckRequestDto(String ticket) {
        this.ticket = ticket;
    }

    /**
     * 验证请求参数
     * @return 是否有效
     */
    public boolean isValid() {
        return ticket != null && !ticket.trim().isEmpty();
    }

    /**
     * 获取摘要信息
     * @return 摘要字符串
     */
    public String getSummary() {
        return String.format("LoginCheckRequest{ticket='%s'}",
                ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null");
    }
}