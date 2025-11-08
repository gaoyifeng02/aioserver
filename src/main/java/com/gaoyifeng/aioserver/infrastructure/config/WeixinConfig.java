package com.gaoyifeng.aioserver.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信配置类 - Infrastructure层
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "weixin.config")
public class WeixinConfig {

    /**
     * 微信公众号Token
     */
    private String token;

    /**
     * 微信公众号原始ID
     */
    private String originalId;

    /**
     * 微信公众号AppID
     */
    private String appid;

    /**
     * 微信公众号AppSecret
     */
    private String appsecret;

    /**
     * 消息加密密钥 (可选)
     */
    private String encodingAESKey;

    /**
     * 是否开启消息加密
     */
    private Boolean encryptEnabled = false;

    // ==================== 微信登录相关配置 ====================

    /**
     * 登录成功模板消息ID
     */
    private String loginSuccessTemplateId;

    /**
     * 登录二维码过期时间（秒）
     */
    private Integer qrCodeExpireTime = 300; // 默认5分钟

    /**
     * 登录票据缓存过期时间（分钟）
     */
    private Integer loginTicketExpireMinutes = 5;

    /**
     * 微信API基础URL
     */
    private String apiBaseUrl = "https://api.weixin.qq.com/cgi-bin";

    /**
     * 登录回调URL（可选，用于自定义回调处理）
     */
    private String loginCallbackUrl;

    /**
     * 是否启用登录功能
     */
    private Boolean loginEnabled = true;

    /**
     * 获取微信访问令牌URL
     * @return 访问令牌URL
     */
    public String getAccessTokenUrl() {
        return String.format("%s/token?grant_type=client_credential&appid=%s&secret=%s",
                apiBaseUrl, appid, appsecret);
    }

    /**
     * 获取创建二维码URL
     * @return 创建二维码URL
     */
    public String getCreateQrCodeUrl() {
        return String.format("%s/qrcode/create?access_token=ACCESS_TOKEN", apiBaseUrl);
    }

    /**
     * 获取发送模板消息URL
     * @return 发送模板消息URL
     */
    public String getSendTemplateMessageUrl() {
        return String.format("%s/message/template/send?access_token=ACCESS_TOKEN", apiBaseUrl);
    }

    /**
     * 获取用户信息URL
     * @return 用户信息URL
     */
    public String getUserInfoUrl() {
        return String.format("%s/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN", apiBaseUrl);
    }
}