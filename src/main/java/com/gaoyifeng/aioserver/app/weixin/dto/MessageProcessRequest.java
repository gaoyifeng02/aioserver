package com.gaoyifeng.aioserver.app.weixin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信消息处理请求DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageProcessRequest {

    /**
     * 请求体内容（XML格式）
     */
    private String requestBody;

    /**
     * 微信签名
     */
    private String signature;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 随机数
     */
    private String nonce;

    /**
     * 用户OpenID
     */
    private String openid;

    /**
     * 加密类型（可选）
     */
    private String encryptType;

    /**
     * 消息签名（可选）
     */
    private String msgSignature;

    /**
     * 验证请求参数是否有效
     */
    public boolean isValid() {
        return requestBody != null && !requestBody.trim().isEmpty()
                && signature != null && !signature.trim().isEmpty()
                && timestamp != null && !timestamp.trim().isEmpty()
                && nonce != null && !nonce.trim().isEmpty()
                && openid != null && !openid.trim().isEmpty();
    }
}