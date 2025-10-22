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
}