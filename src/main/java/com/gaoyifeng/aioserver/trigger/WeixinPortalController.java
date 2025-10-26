package com.gaoyifeng.aioserver.trigger;

import com.gaoyifeng.aioserver.api.IWeixinService;
import com.gaoyifeng.aioserver.app.weixin.dto.MessageProcessRequest;
import com.gaoyifeng.aioserver.app.weixin.dto.MessageProcessResponse;
import com.gaoyifeng.aioserver.app.weixin.dto.SignatureVerifyRequest;
import com.gaoyifeng.aioserver.app.weixin.service.WeixinMessageAppService;
import com.gaoyifeng.aioserver.types.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务对接控制器 - DDD架构重构版
 * 实现IWeixinService API接口
 * 对接地址：/api/v1/weixin/portal/receive
 *
 * 采用DDD架构，控制器只负责：
 * 1. 接收HTTP请求
 * 2. 参数转换和验证
 * 3. 调用应用服务
 * 4. 返回响应结果
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/weixin/portal")
public class WeixinPortalController implements IWeixinService {

    @Autowired
    private WeixinMessageAppService weixinMessageAppService;

    /**
     * 微信服务器验证 - GET请求
     * 验证服务器地址的有效性
     *
     * @param signature 微信签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return 验证成功返回echostr，失败返回null
     */
    @GetMapping(value = "receive", produces = "text/plain;charset=utf-8")
    public String validate(@RequestParam(value = "signature", required = false) String signature,
                          @RequestParam(value = "timestamp", required = false) String timestamp,
                          @RequestParam(value = "nonce", required = false) String nonce,
                          @RequestParam(value = "echostr", required = false) String echostr) {
        try {
            log.info("接收到微信服务器验证请求：signature={}, timestamp={}, nonce={}, echostr={}",
                    signature, timestamp, nonce, echostr);

            // 调用API接口方法
            Result<String> response = verifySignature(signature, timestamp, nonce, echostr);

            if (response.isSuccess()) {
                log.info("微信服务器验证成功");
                return response.getData();
            } else {
                log.warn("微信服务器验证失败：{}", response.getInfo());
                return null;
            }

        } catch (Exception e) {
            log.error("微信服务器验证异常", e);
            return null;
        }
    }

    /**
     * 接收微信消息 - POST请求
     * 处理用户发送的消息并返回回复
     *
     * @param requestBody 请求体（XML格式）
     * @param signature 微信签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param openid 用户OpenID
     * @param encType 加密类型（可选）
     * @param msgSignature 消息签名（可选）
     * @return 回复消息XML
     */
    @PostMapping(value = "receive", produces = "application/xml; charset=UTF-8")
    public String receiveMessage(@RequestBody String requestBody,
                                @RequestParam("signature") String signature,
                                @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce,
                                @RequestParam("openid") String openid,
                                @RequestParam(name = "encrypt_type", required = false) String encType,
                                @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        try {
            log.info("接收到微信消息请求：openid={}, body={}", openid, requestBody);

            // 调用API接口方法
            Result<String> response = processMessage(signature, timestamp, nonce, openid, requestBody);

            if (response.isSuccess()) {
                log.info("微信消息处理成功，回复内容：{}", response.getData());
                return response.getData();
            } else {
                log.warn("微信消息处理失败：{}", response.getInfo());
                return "";
            }

        } catch (Exception e) {
            log.error("微信消息处理异常，openid={}, body={}", openid, requestBody, e);
            return "";
        }
    }

    // ==================== 实现IWeixinService接口方法 ====================

    @Override
    public Result<String> verifySignature(String signature, String timestamp, String nonce, String echostr) {
        try {
            // 构建签名验证请求
            SignatureVerifyRequest request = SignatureVerifyRequest.builder()
                    .signature(signature)
                    .timestamp(timestamp)
                    .nonce(nonce)
                    .echostr(echostr)
                    .build();

            // 调用应用服务进行验证
            return weixinMessageAppService.verifySignature(request);

        } catch (Exception e) {
            log.error("微信签名验证异常", e);
            return Result.fail("9999", "系统异常");
        }
    }

    @Override
    public Result<String> processMessage(String signature, String timestamp, String nonce, String openid, String requestBody) {
        try {
            // 构建消息处理请求
            MessageProcessRequest request = MessageProcessRequest.builder()
                    .requestBody(requestBody)
                    .signature(signature)
                    .timestamp(timestamp)
                    .nonce(nonce)
                    .openid(openid)
                    .build();

            // 调用应用服务处理消息
            Result<MessageProcessResponse> result = weixinMessageAppService.processMessage(request);

            if (result.isSuccess()) {
                String replyXml = result.getData().getReplyXml();
                return Result.success(replyXml);
            } else {
                return Result.fail(result.getCode(), result.getInfo());
            }

        } catch (Exception e) {
            log.error("微信消息处理异常，openid={}, body={}", openid, requestBody, e);
            return Result.fail("9999", "系统异常");
        }
    }
}