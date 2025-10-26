package com.gaoyifeng.aioserver.infrastructure.adapter.port;

import com.gaoyifeng.aioserver.domain.weixin.adapter.port.IWeixinApiPort;
import com.gaoyifeng.aioserver.infrastructure.util.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 微信API端口适配器实现 - Infrastructure层
 * 实现微信相关技术操作
 *
 * @author gaoyifeng
 */
@Slf4j
@Component
public class WeixinApiPortAdapter implements IWeixinApiPort {

    @Override
    public boolean sendTemplateMessage(String openId, String templateId, String data) {
        try {
            // TODO: 实现模板消息发送逻辑
            log.info("发送模板消息，openId：{}，templateId：{}，data：{}", openId, templateId, data);

            // 这里可以通过Retrofit2调用微信API
            // 暂时返回成功，待后续实现
            return true;
        } catch (Exception e) {
            log.error("发送模板消息失败，openId：{}，templateId：{}", openId, templateId, e);
            return false;
        }
    }

    @Override
    public String getUserInfo(String openId) {
        try {
            // TODO: 实现获取用户信息逻辑
            log.info("获取用户信息，openId：{}", openId);

            // 这里可以通过Retrofit2调用微信API获取用户信息
            // 暂时返回空字符串，待后续实现
            return "";
        } catch (Exception e) {
            log.error("获取用户信息失败，openId：{}", openId, e);
            return null;
        }
    }

    @Override
    public boolean verifySignature(String signature, String timestamp, String nonce, String token) {
        try {
            log.debug("验证微信签名，signature：{}，timestamp：{}，nonce：{}", signature, timestamp, nonce);

            // 调用技术工具类实现签名验证
            boolean isValid = SignatureUtil.check(token, signature, timestamp, nonce);

            log.debug("微信签名验证结果：{}", isValid);
            return isValid;
        } catch (Exception e) {
            log.error("微信签名验证异常", e);
            return false;
        }
    }
}