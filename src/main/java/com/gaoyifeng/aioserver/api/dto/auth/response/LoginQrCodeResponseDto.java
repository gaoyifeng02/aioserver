package com.gaoyifeng.aioserver.api.dto.auth.response;

import lombok.Data;

/**
 * 微信登录二维码响应DTO
 * 参考study项目：IAuthService.weixinQrCodeTicket()返回值
 */
@Data
public class LoginQrCodeResponseDto {

    /**
     * 登录票据（用于后续状态检查）
     */
    private String ticket;

    /**
     * 二维码URL（前端用于显示二维码）
     */
    private String qrCodeUrl;

    /**
     * 二维码过期时间（秒）
     */
    private Integer expireTime;

    /**
     * 二维码图片内容（可选，base64编码）
     */
    private String qrCodeImage;

    /**
     * 构造函数
     */
    public LoginQrCodeResponseDto() {
    }

    /**
     * 构造函数
     * @param ticket 登录票据
     * @param qrCodeUrl 二维码URL
     */
    public LoginQrCodeResponseDto(String ticket, String qrCodeUrl) {
        this.ticket = ticket;
        this.qrCodeUrl = qrCodeUrl;
        this.expireTime = 300; // 默认5分钟过期
    }

    /**
     * 构造函数
     * @param ticket 登录票据
     * @param qrCodeUrl 二维码URL
     * @param expireTime 过期时间（秒）
     */
    public LoginQrCodeResponseDto(String ticket, String qrCodeUrl, Integer expireTime) {
        this.ticket = ticket;
        this.qrCodeUrl = qrCodeUrl;
        this.expireTime = expireTime;
    }

    /**
     * 检查二维码是否已过期
     * @return 是否过期
     */
    public boolean isExpired() {
        return this.ticket == null || this.ticket.trim().isEmpty();
    }

    /**
     * 获取摘要信息
     * @return 摘要字符串
     */
    public String getSummary() {
        return String.format("LoginQrCodeResponse{ticket='%s', hasQrCodeUrl=%s, expireTime=%d}",
                ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null",
                qrCodeUrl != null && !qrCodeUrl.isEmpty(),
                expireTime);
    }
}