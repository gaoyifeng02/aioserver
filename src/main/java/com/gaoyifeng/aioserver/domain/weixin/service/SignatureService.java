package com.gaoyifeng.aioserver.domain.weixin.service;

import com.gaoyifeng.aioserver.domain.weixin.adapter.port.IWeixinApiPort;
import com.gaoyifeng.aioserver.domain.weixin.model.valobj.Signature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 微信签名验证领域服务
 * 遵循DDD原则，依赖Port接口实现技术解耦
 */
@Slf4j
@Service
public class SignatureService {

    @Value("${weixin.config.token}")
    private String token;

    @Value("${weixin.config.signature.max-age:300}") // 默认5分钟有效期
    private long signatureMaxAge;

    @Autowired
    private IWeixinApiPort weixinApiPort;

    /**
     * 验证微信签名
     * @param signature 微信签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @return 验证结果
     */
    public boolean verifySignature(String signature, String timestamp, String nonce) {
        try {
            // 创建签名值对象进行业务验证
            Signature signatureVO = Signature.builder()
                    .signature(signature)
                    .timestamp(timestamp)
                    .nonce(nonce)
                    .build();

            // 业务规则验证
            if (!signatureVO.isComplete()) {
                log.warn("微信签名验证失败：签名信息不完整");
                return false;
            }

            if (!signatureVO.isValidTimestamp()) {
                log.warn("微信签名验证失败：时间戳格式无效");
                return false;
            }

            // 时间戳有效期验证（业务规则）
            if (!isTimestampValid(timestamp)) {
                log.warn("微信签名验证失败：时间戳已过期");
                return false;
            }

            // 委托给Port接口进行技术实现
            boolean isValid = weixinApiPort.verifySignature(signature, timestamp, nonce, token);
            log.info("微信签名验证完成，结果：{}", isValid);
            return isValid;

        } catch (Exception e) {
            log.error("微信签名验证异常", e);
            return false;
        }
    }

    /**
     * 验证时间戳是否在有效期内
     * @param timestamp 时间戳
     * @return 是否有效
     */
    private boolean isTimestampValid(String timestamp) {
        try {
            long requestTime = Long.parseLong(timestamp);
            long currentTime = System.currentTimeMillis() / 1000;
            long diffTime = Math.abs(currentTime - requestTime);
            return diffTime <= signatureMaxAge;
        } catch (NumberFormatException e) {
            log.warn("时间戳格式无效：{}", timestamp);
            return false;
        }
    }

    /**
     * 验证签名格式是否符合业务要求
     * @param signature 签名
     * @return 是否符合格式要求
     */
    public boolean isValidSignatureFormat(String signature) {
        return signature != null && signature.length() == 40; // SHA1摘要长度
    }
}