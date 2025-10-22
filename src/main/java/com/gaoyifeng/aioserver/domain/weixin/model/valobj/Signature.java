package com.gaoyifeng.aioserver.domain.weixin.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信签名值对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Signature {

    /**
     * 签名
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
     * 验证签名信息是否完整
     */
    public boolean isComplete() {
        return signature != null && !signature.trim().isEmpty()
                && timestamp != null && !timestamp.trim().isEmpty()
                && nonce != null && !nonce.trim().isEmpty();
    }

    /**
     * 获取签名长度
     */
    public int getSignatureLength() {
        return signature != null ? signature.length() : 0;
    }

    /**
     * 时间戳是否有效（非空且为数字）
     */
    public boolean isValidTimestamp() {
        return timestamp != null && timestamp.matches("\\d+");
    }
}