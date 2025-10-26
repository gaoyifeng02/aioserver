package com.gaoyifeng.aioserver.api;

import com.gaoyifeng.aioserver.types.common.Result;

/**
 * 微信服务API接口
 * 定义对外提供的微信相关服务能力
 *
 * @author gaoyifeng
 */
public interface IWeixinService {

    /**
     * 验证微信服务器签名
     * @param signature 微信签名
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @param echostr 随机字符串
     * @return 验证结果
     */
    Result<String> verifySignature(String signature, String timestamp, String nonce, String echostr);

    /**
     * 处理微信消息
     * @param signature 微信签名
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @param openid 用户OpenID
     * @param requestBody 请求体
     * @return 处理结果
     */
    Result<String> processMessage(String signature, String timestamp, String nonce, String openid, String requestBody);
}