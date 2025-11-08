package com.gaoyifeng.aioserver.infrastructure.adapter.port;

import com.gaoyifeng.aioserver.domain.auth.adapter.port.IWeixinLoginPort;
import com.gaoyifeng.aioserver.domain.weixin.adapter.port.IWeixinApiPort;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 微信登录端口适配器 - Infrastructure层
 * 实现认证域的微信登录技术操作
 */
@Slf4j
@Component
public class WeixinLoginPortAdapter implements IWeixinLoginPort {

    @Autowired
    private IWeixinApiPort weixinApiPort;

    /**
     * 登录状态缓存
     * Key: ticket, Value: openId
     */
    private final Cache<String, String> loginCache;

    public WeixinLoginPortAdapter() {
        this.loginCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(5, TimeUnit.MINUTES) // 5分钟过期
                .build();
    }

    @Override
    public String createQrCode(String ticket) throws Exception {
        try {
            log.info("创建微信登录二维码：ticket={}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null");

            if (ticket == null || ticket.trim().isEmpty()) {
                throw new IllegalArgumentException("登录票据不能为空");
            }

            // 调用微信API端口创建二维码
            String qrCodeUrl = weixinApiPort.createLoginQrCode(ticket);

            if (qrCodeUrl == null || qrCodeUrl.trim().isEmpty()) {
                throw new Exception("创建二维码失败，返回的URL为空");
            }

            log.info("微信登录二维码创建成功：ticket={}, url长度={}",
                    ticket.substring(0, Math.min(8, ticket.length())) + "...",
                    qrCodeUrl.length());

            return qrCodeUrl;

        } catch (Exception e) {
            log.error("创建微信登录二维码失败：ticket={}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null", e);
            throw new Exception("创建微信登录二维码失败：" + e.getMessage(), e);
        }
    }

    @Override
    public String getLoginStatus(String ticket) {
        try {
            log.info("获取登录状态：ticket={}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null");

            if (ticket == null || ticket.trim().isEmpty()) {
                return null;
            }

            // 从缓存中获取登录状态
            String openId = loginCache.getIfPresent(ticket);

            if (openId != null && !openId.trim().isEmpty()) {
                log.info("登录状态：已登录，ticket={}, openId={}",
                        ticket.substring(0, Math.min(8, ticket.length())) + "...",
                        openId.substring(0, Math.min(8, openId.length())) + "...");
            } else {
                log.info("登录状态：未登录，ticket={}",
                        ticket.substring(0, Math.min(8, ticket.length())) + "...");
            }

            return openId;

        } catch (Exception e) {
            log.error("获取登录状态异常：ticket={}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null", e);
            return null;
        }
    }

    @Override
    public void saveLoginState(String ticket, String openId) throws Exception {
        try {
            log.info("保存登录状态：ticket={}, openId={}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null");

            if (ticket == null || ticket.trim().isEmpty()) {
                throw new IllegalArgumentException("登录票据不能为空");
            }
            if (openId == null || openId.trim().isEmpty()) {
                throw new IllegalArgumentException("用户OpenID不能为空");
            }

            // 保存到缓存
            loginCache.put(ticket, openId);

            log.info("登录状态保存成功：ticket={}, openId={}",
                    ticket.substring(0, Math.min(8, ticket.length())) + "...",
                    openId.substring(0, Math.min(8, openId.length())) + "...");

        } catch (Exception e) {
            log.error("保存登录状态异常：ticket={}, openId={}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null", e);
            throw new Exception("保存登录状态失败：" + e.getMessage(), e);
        }
    }

    @Override
    public void sendLoginTemplate(String openId, String nickname) throws Exception {
        try {
            log.info("发送登录成功模板消息：openId={}, nickname={}",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null",
                    nickname);

            if (openId == null || openId.trim().isEmpty()) {
                throw new IllegalArgumentException("用户OpenID不能为空");
            }

            // 调用微信API端口发送模板消息
            boolean success = weixinApiPort.sendLoginSuccessTemplate(openId, nickname);

            if (success) {
                log.info("登录成功模板消息发送成功：openId={}",
                        openId.substring(0, Math.min(8, openId.length())) + "...");
            } else {
                log.warn("登录成功模板消息发送失败：openId={}",
                        openId.substring(0, Math.min(8, openId.length())) + "...");
            }

        } catch (Exception e) {
            log.error("发送登录成功模板消息异常：openId={}, nickname={}",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null",
                    nickname, e);
            throw new Exception("发送登录成功模板消息失败：" + e.getMessage(), e);
        }
    }

    @Override
    public String getWeixinUserInfo(String openId) {
        try {
            log.info("获取微信用户信息：openId={}",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null");

            if (openId == null || openId.trim().isEmpty()) {
                return null;
            }

            // 调用微信API端口获取用户信息
            String userInfo = weixinApiPort.getUserInfo(openId);

            if (userInfo != null && !userInfo.trim().isEmpty()) {
                log.info("微信用户信息获取成功：openId={}, info长度={}",
                        openId.substring(0, Math.min(8, openId.length())) + "...",
                        userInfo.length());
            } else {
                log.warn("微信用户信息获取失败：openId={}",
                        openId.substring(0, Math.min(8, openId.length())) + "...");
            }

            return userInfo;

        } catch (Exception e) {
            log.error("获取微信用户信息异常：openId={}",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null", e);
            return null;
        }
    }

    /**
     * 清理过期的登录状态
     */
    public void cleanExpiredLoginStates() {
        try {
            log.info("开始清理过期的登录状态");
            // Guava Cache会自动清理过期项，这里只是记录日志
            long size = loginCache.size();
            log.info("当前登录状态缓存大小：{}", size);
        } catch (Exception e) {
            log.error("清理过期登录状态异常", e);
        }
    }

    /**
     * 获取缓存统计信息
     * @return 缓存统计
     */
    public String getCacheStats() {
        return loginCache.stats().toString();
    }
}