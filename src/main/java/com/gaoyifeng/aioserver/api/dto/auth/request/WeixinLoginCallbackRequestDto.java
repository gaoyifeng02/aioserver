package com.gaoyifeng.aioserver.api.dto.auth.request;

import lombok.Data;

/**
 * 微信登录回调请求DTO
 * 用于处理微信扫码后的回调通知
 */
@Data
public class WeixinLoginCallbackRequestDto {

    /**
     * 登录票据（与createWeixinQrCode()生成的ticket对应）
     */
    private String ticket;

    /**
     * 用户OpenID（微信返回）
     */
    private String openId;

    /**
     * 用户UnionID（可选，微信返回）
     */
    private String unionId;

    /**
     * 用户昵称（微信返回）
     */
    private String nickname;

    /**
     * 用户头像URL（微信返回）
     */
    private String avatar;

    /**
     * 扫码时间戳
     */
    private Long scanTime;

    /**
     * 构造函数
     */
    public WeixinLoginCallbackRequestDto() {
    }

    /**
     * 构造函数
     * @param ticket 登录票据
     * @param openId 用户OpenID
     */
    public WeixinLoginCallbackRequestDto(String ticket, String openId) {
        this.ticket = ticket;
        this.openId = openId;
        this.scanTime = System.currentTimeMillis();
    }

    /**
     * 构造函数
     * @param ticket 登录票据
     * @param openId 用户OpenID
     * @param unionId 用户UnionID
     * @param nickname 用户昵称
     * @param avatar 用户头像
     */
    public WeixinLoginCallbackRequestDto(String ticket, String openId, String unionId, String nickname, String avatar) {
        this.ticket = ticket;
        this.openId = openId;
        this.unionId = unionId;
        this.nickname = nickname;
        this.avatar = avatar;
        this.scanTime = System.currentTimeMillis();
    }

    /**
     * 验证回调请求参数
     * @return 是否有效
     */
    public boolean isValid() {
        return ticket != null && !ticket.trim().isEmpty()
                && openId != null && !openId.trim().isEmpty();
    }

    /**
     * 是否包含完整的用户信息
     * @return 是否包含完整信息
     */
    public boolean hasCompleteUserInfo() {
        return isValid()
                && nickname != null && !nickname.trim().isEmpty()
                && avatar != null && !avatar.trim().isEmpty();
    }

    /**
     * 获取摘要信息
     * @return 摘要字符串
     */
    public String getSummary() {
        return String.format("WeixinLoginCallback{ticket='%s', openId='%s', unionId='%s', nickname='%s', hasAvatar=%s}",
                ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null",
                openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null",
                unionId != null ? unionId.substring(0, Math.min(8, unionId.length())) + "..." : "null",
                nickname,
                avatar != null && !avatar.isEmpty());
    }
}