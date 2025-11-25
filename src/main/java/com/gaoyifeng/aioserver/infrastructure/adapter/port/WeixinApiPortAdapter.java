package com.gaoyifeng.aioserver.infrastructure.adapter.port;

import com.gaoyifeng.aioserver.domain.weixin.adapter.port.IWeixinApiPort;
import com.gaoyifeng.aioserver.app.config.WeixinConfig;
import com.gaoyifeng.aioserver.infrastructure.gateway.IWeixinApiGateway;
import com.gaoyifeng.aioserver.infrastructure.gateway.dto.WeixinQrCodeRequestDTO;
import com.gaoyifeng.aioserver.infrastructure.gateway.dto.WeixinQrCodeResponseDTO;
import com.gaoyifeng.aioserver.infrastructure.gateway.dto.WeixinTemplateMessageDTO;
import com.gaoyifeng.aioserver.infrastructure.gateway.dto.WeixinTokenResponseDTO;
import com.gaoyifeng.aioserver.infrastructure.util.SignatureUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 微信API端口适配器实现 - Infrastructure层
 * 实现微信相关技术操作，集成study项目的真实API调用
 *
 * @author gaoyifeng
 */
@Slf4j
@Component
public class WeixinApiPortAdapter implements IWeixinApiPort {

    @Autowired
    private WeixinConfig weixinConfig;

    @Autowired
    private IWeixinApiGateway weixinApiGateway;

    /**
     * AccessToken缓存
     * Key: appid, Value: accessToken
     */
    private final Cache<String, String> accessTokenCache;

    public WeixinApiPortAdapter() {
        this.accessTokenCache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(110, TimeUnit.MINUTES) // 微信token有效期2小时，这里110分钟提前刷新
                .build();
    }

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

            // 1. 获取AccessToken
            String accessToken = getAccessToken();

            // 2. 构建二维码请求
            WeixinQrCodeRequestDTO qrCodeRequest = WeixinQrCodeRequestDTO.builder()
                    .expire_seconds(2592000) // 30天过期
                    .action_name(WeixinQrCodeRequestDTO.ActionNameTypeVO.QR_STR_SCENE.getCode())
                    .action_info(WeixinQrCodeRequestDTO.ActionInfo.builder()
                            .scene(WeixinQrCodeRequestDTO.ActionInfo.Scene.builder()
                                    .scene_str(ticket)
                                    .build())
                            .build())
                    .build();

            // 3. 调用微信API创建二维码
            WeixinQrCodeResponseDTO response = weixinApiGateway.createQrCode(accessToken, qrCodeRequest).execute().body();

            if (response == null || response.getTicket() == null || response.getTicket().trim().isEmpty()) {
                throw new Exception("微信API返回的ticket为空");
            }

            String qrCodeUrl = String.format("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s", response.getTicket());

            log.info("微信登录二维码创建成功，ticket：{}, url长度：{}",
                    response.getTicket().substring(0, Math.min(8, response.getTicket().length())) + "...",
                    qrCodeUrl.length());

            return qrCodeUrl;

        } catch (IOException e) {
            log.error("创建微信登录二维码失败，网络异常，ticket：{}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null", e);
            throw new Exception("创建微信登录二维码失败，网络异常：" + e.getMessage(), e);
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

            String templateId = weixinConfig.getLoginSuccessTemplateId();
            if (templateId == null || templateId.trim().isEmpty()) {
                log.warn("登录成功模板消息ID未配置，跳过发送");
                return false;
            }

            // 1. 获取AccessToken
            String accessToken = getAccessToken();

            // 2. 构建模板消息
            Map<String, Map<String, String>> data = new HashMap<>();
            WeixinTemplateMessageDTO.put(data, WeixinTemplateMessageDTO.TemplateKey.USER,
                    nickname != null ? nickname : "用户");

            WeixinTemplateMessageDTO templateMessage = new WeixinTemplateMessageDTO(openId, templateId);
            templateMessage.setUrl("https://gaga.plus"); // 可以配置化
            templateMessage.setData(data);

            // 3. 发送模板消息
            weixinApiGateway.sendMessage(accessToken, templateMessage).execute();

            log.info("登录成功模板消息发送成功，templateId：{}, openId：{}", templateId,
                    openId.substring(0, Math.min(8, openId.length())) + "...");
            return true;

        } catch (IOException e) {
            log.error("发送登录成功模板消息失败，网络异常，openId：{}, nickname：{}",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null",
                    nickname, e);
            throw new Exception("发送登录成功模板消息失败，网络异常：" + e.getMessage(), e);
        } catch (Exception e) {
            log.error("发送登录成功模板消息失败，openId：{}, nickname：{}",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null",
                    nickname, e);
            throw new Exception("发送登录成功模板消息失败：" + e.getMessage(), e);
        }
    }

    /**
     * 获取微信AccessToken
     * 优先从缓存获取，缓存失效则重新调用API获取
     * @return AccessToken
     * @throws Exception 获取失败时抛出异常
     */
    private String getAccessToken() throws Exception {
        try {
            String appid = weixinConfig.getAppid();
            String appSecret = weixinConfig.getAppsecret();

            if (appid == null || appid.trim().isEmpty() || appSecret == null || appSecret.trim().isEmpty()) {
                throw new IllegalArgumentException("微信appid或appsecret未配置");
            }

            // 尝试从缓存获取
            String accessToken = accessTokenCache.getIfPresent(appid);
            if (accessToken != null && !accessToken.trim().isEmpty()) {
                log.debug("从缓存获取AccessToken成功，appid：{}", appid);
                return accessToken;
            }

            // 缓存失效，重新获取
            log.info("AccessToken缓存失效，重新获取，appid：{}", appid);
            WeixinTokenResponseDTO tokenResponse = weixinApiGateway
                    .getToken("client_credential", appid, appSecret)
                    .execute()
                    .body();

            if (tokenResponse == null || tokenResponse.getAccess_token() == null ||
                tokenResponse.getAccess_token().trim().isEmpty()) {
                throw new Exception("获取AccessToken失败，返回数据为空");
            }

            // 检查是否有错误
            if (tokenResponse.getErrcode() != null && !tokenResponse.getErrcode().trim().isEmpty()) {
                throw new Exception("获取AccessToken失败，错误码：" + tokenResponse.getErrcode() +
                        "，错误信息：" + tokenResponse.getErrmsg());
            }

            accessToken = tokenResponse.getAccess_token();

            // 缓存新的AccessToken
            accessTokenCache.put(appid, accessToken);

            log.info("获取AccessToken成功，appid：{}，token长度：{}",
                    appid, accessToken.length());
            return accessToken;

        } catch (IOException e) {
            log.error("获取AccessToken失败，网络异常", e);
            throw new Exception("获取AccessToken失败，网络异常：" + e.getMessage(), e);
        } catch (Exception e) {
            log.error("获取AccessToken失败", e);
            throw new Exception("获取AccessToken失败：" + e.getMessage(), e);
        }
    }
}