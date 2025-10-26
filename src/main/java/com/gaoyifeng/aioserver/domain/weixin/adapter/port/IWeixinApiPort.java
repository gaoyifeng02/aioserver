package com.gaoyifeng.aioserver.domain.weixin.adapter.port;

/**
 * 微信API网关端口接口 - Domain层
 * 定义微信相关技术操作的能力契约
 *
 * @author gaoyifeng
 */
public interface IWeixinApiPort {

    /**
     * 发送模板消息
     * @param openId 接收者OpenID
     * @param templateId 模板消息ID
     * @param data 模板数据
     * @return 发送结果
     */
    boolean sendTemplateMessage(String openId, String templateId, String data);

    /**
     * 获取用户信息
     * @param openId 用户OpenID
     * @return 用户信息JSON字符串
     */
    String getUserInfo(String openId);

    /**
     * 验证消息签名
     * @param signature 微信签名
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @param token 配置的Token
     * @return 验证结果
     */
    boolean verifySignature(String signature, String timestamp, String nonce, String token);
}