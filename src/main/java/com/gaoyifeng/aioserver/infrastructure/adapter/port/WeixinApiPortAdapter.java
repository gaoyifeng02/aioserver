package com.gaoyifeng.aioserver.infrastructure.adapter.port;

import com.gaoyifeng.aioserver.domain.weixin.adapter.port.IWeixinApiPort;
import com.gaoyifeng.aioserver.infrastructure.config.WeixinConfig;
import com.gaoyifeng.aioserver.infrastructure.util.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WeixinConfig weixinConfig;

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

    // ==================== 微信登录相关方法 ====================

    @Override
    public String createLoginQrCode(String ticket) throws Exception {
        try {
            log.info("创建微信登录二维码，ticket：{}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null");

            if (ticket == null || ticket.trim().isEmpty()) {
                throw new IllegalArgumentException("登录票据不能为空");
            }

            // TODO: 实际的微信二维码创建API调用
            // 这里应该调用微信API创建临时二维码
            // 目前返回模拟的二维码URL
            String qrCodeUrl = String.format("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s", ticket);

            log.info("微信登录二维码创建成功，ticket：{}, url长度：{}",
                    ticket.substring(0, Math.min(8, ticket.length())) + "...",
                    qrCodeUrl.length());

            return qrCodeUrl;

        } catch (Exception e) {
            log.error("创建微信登录二维码失败，ticket：{}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null", e);
            throw new Exception("创建微信登录二维码失败：" + e.getMessage(), e);
        }
    }

    @Override
    public boolean sendLoginSuccessTemplate(String openId, String nickname) throws Exception {
        try {
            log.info("发送登录成功模板消息，openId：{}, nickname：{}",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null",
                    nickname);

            if (openId == null || openId.trim().isEmpty()) {
                throw new IllegalArgumentException("用户OpenID不能为空");
            }

            // TODO: 实际的微信模板消息发送
            // 这里应该调用微信API发送模板消息
            // 暂时模拟发送成功
            String templateId = weixinConfig.getLoginSuccessTemplateId();
            if (templateId != null && !templateId.trim().isEmpty()) {
                log.info("发送登录成功模板消息，templateId：{}, openId：{}", templateId,
                        openId.substring(0, Math.min(8, openId.length())) + "...");
                return true;
            } else {
                log.warn("登录成功模板消息ID未配置，跳过发送");
                return false;
            }

        } catch (Exception e) {
            log.error("发送登录成功模板消息失败，openId：{}, nickname：{}",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null",
                    nickname, e);
            throw new Exception("发送登录成功模板消息失败：" + e.getMessage(), e);
        }
    }
}