package com.gaoyifeng.aioserver.app.weixin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信签名验证请求DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignatureVerifyRequest {

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
     * 随机字符串（验证成功需要返回）
     */
    private String echostr;

    /**
     * 验证请求参数是否有效
     */
    public boolean isValid() {
        return signature != null && !signature.trim().isEmpty()
                && timestamp != null && !timestamp.trim().isEmpty()
                && nonce != null && !nonce.trim().isEmpty()
                && echostr != null && !echostr.trim().isEmpty();
    }
}