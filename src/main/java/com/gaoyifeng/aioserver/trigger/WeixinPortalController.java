package com.gaoyifeng.aioserver.trigger;

import com.gaoyifeng.aioserver.api.IWeixinService;
import com.gaoyifeng.aioserver.domain.weixin.model.entity.WeixinMessage;
import com.gaoyifeng.aioserver.domain.weixin.model.aggregate.MessageConversation;
import com.gaoyifeng.aioserver.domain.weixin.adapter.port.IMessageRepositoryPort;
import com.gaoyifeng.aioserver.domain.weixin.service.SignatureService;
import com.gaoyifeng.aioserver.domain.weixin.service.MessageService;
import com.gaoyifeng.aioserver.app.config.WeixinConfig;
import com.gaoyifeng.aioserver.infrastructure.util.XmlUtil;
import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务对接控制器 - 简化架构版
 * 实现IWeixinService API接口
 * 对接地址：/api/v1/weixin/portal/receive
 *
 * 采用简化架构，控制器负责：
 * 1. 接收HTTP请求
 * 2. 参数转换和验证
 * 3. 直接调用领域服务
 * 4. 返回响应结果
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/weixin/portal")
public class WeixinPortalController implements IWeixinService {

    @Autowired
    private SignatureService signatureService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private IMessageRepositoryPort weixinMessageRepository;

    @Autowired
    private WeixinConfig weixinConfig;

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
                log.warn("微信服务器验证失败：{}", response.getMessage());
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
                log.warn("微信消息处理失败：{}", response.getMessage());
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
            log.info("开始验证微信签名：signature={}, timestamp={}, nonce={}", signature, timestamp, nonce);

            // 参数验证
            if (signature == null || signature.trim().isEmpty()
                    || timestamp == null || timestamp.trim().isEmpty()
                    || nonce == null || nonce.trim().isEmpty()
                    || echostr == null || echostr.trim().isEmpty()) {
                return Result.fail(ResultCode.PARAM_ERROR, "微信签名验证请求参数不完整");
            }

            // 调用领域服务验证签名
            boolean isValid = signatureService.verifySignature(signature, timestamp, nonce);

            if (!isValid) {
                return Result.fail(ResultCode.BUSINESS_ERROR, "微信签名验证失败，请检查配置");
            }

            log.info("微信签名验证成功");
            return Result.success(echostr);

        } catch (Exception e) {
            log.error("微信签名验证异常", e);
            return Result.fail(ResultCode.SYSTEM_ERROR, "微信签名验证过程中发生系统异常");
        }
    }

    @Override
    public Result<String> processMessage(String signature, String timestamp, String nonce, String openid, String requestBody) {
        try {
            log.info("开始处理微信消息：openid={}, requestBody={}", openid, requestBody);

            // 1. 参数验证
            if (requestBody == null || requestBody.trim().isEmpty()
                    || signature == null || signature.trim().isEmpty()
                    || timestamp == null || timestamp.trim().isEmpty()
                    || nonce == null || nonce.trim().isEmpty()
                    || openid == null || openid.trim().isEmpty()) {
                return Result.fail(ResultCode.PARAM_ERROR, "微信消息处理请求参数不完整");
            }

            // 2. 验证签名
            boolean signatureValid = signatureService.verifySignature(signature, timestamp, nonce);
            if (!signatureValid) {
                return Result.fail(ResultCode.BUSINESS_ERROR, "微信签名验证失败，消息可能被篡改");
            }

            // 3. XML解析转换为领域对象
            WeixinMessage receivedMessage = xmlToMessage(requestBody);
            if (receivedMessage == null) {
                return Result.fail(ResultCode.BUSINESS_ERROR, "微信消息格式解析失败，请检查消息格式");
            }

            // 4. 领域层业务逻辑验证
            boolean messageValid = messageService.validateMessage(receivedMessage);
            if (!messageValid) {
                return Result.fail(ResultCode.BUSINESS_ERROR, "微信消息内容验证失败，消息不符合业务规则");
            }

            // 5. 查找或创建消息对话聚合
            MessageConversation conversation = findOrCreateConversation(receivedMessage.getFromUserName());

            // 6. 添加消息到对话聚合
            conversation.addMessage(receivedMessage);

            // 7. 领域服务处理消息内容
            String replyContent = messageService.processMessageContent(receivedMessage);

            // 8. 创建回复消息实体
            WeixinMessage replyMessage = createReplyMessage(receivedMessage, replyContent, weixinConfig.getOriginalId());

            // 9. 添加回复消息到对话
            conversation.addMessage(replyMessage);

            // 10. 持久化聚合根
            boolean saved = weixinMessageRepository.saveConversation(conversation);
            if (!saved) {
                log.warn("消息对话保存失败，对话ID：{}", conversation.getConversationId());
            }

            // 11. 转换为XML格式
            String replyXml = XmlUtil.beanToXml(replyMessage);

            log.info("微信消息处理完成，对话ID：{}，回复内容：{}",
                    conversation.getConversationId(), replyContent);

            return Result.success(replyXml);

        } catch (Exception e) {
            log.error("微信消息处理异常，openid={}, body={}", openid, requestBody, e);
            return Result.fail(ResultCode.SYSTEM_ERROR, "微信消息处理过程中发生系统异常");
        }
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 查找或创建消息对话聚合
     */
    private MessageConversation findOrCreateConversation(String openId) {
        // 查找最近的活跃对话
        MessageConversation conversation = weixinMessageRepository.findLatestConversationByOpenId(openId);

        if (conversation == null || !conversation.isActive()) {
            // 创建新的对话
            conversation = MessageConversation.create(openId, weixinConfig.getOriginalId());
        }

        return conversation;
    }

    /**
     * XML字符串转换为微信消息实体
     * @param xmlContent XML内容
     * @return 微信消息实体
     */
    private WeixinMessage xmlToMessage(String xmlContent) {
        try {
            if (xmlContent == null || xmlContent.trim().isEmpty()) {
                log.warn("XML内容为空，无法转换");
                return null;
            }

            // 使用Infrastructure层的XmlUtil进行XML解析
            WeixinMessage message = XmlUtil.xmlToBeanSafe(xmlContent, WeixinMessage.class);

            if (message == null) {
                log.warn("XML解析失败，使用备用解析方式");
                message = parseMessageManually(xmlContent);
            }

            log.debug("XML转消息实体成功：{}", message != null ? message.getSummary() : "失败");
            return message;

        } catch (Exception e) {
            log.error("XML转消息实体异常：{}", xmlContent, e);
            return null;
        }
    }

    /**
     * 创建回复消息实体
     * @param originalMessage 原始消息
     * @param replyContent 回复内容
     * @param originalId 公众号原始ID
     * @return 回复消息实体
     */
    private WeixinMessage createReplyMessage(WeixinMessage originalMessage, String replyContent, String originalId) {
        try {
            if (originalMessage == null || replyContent == null || originalId == null) {
                log.warn("创建回复消息失败：参数不完整");
                return null;
            }

            // 构建回复消息实体
            WeixinMessage replyMessage = WeixinMessage.builder()
                    .msgId(generateMessageId())
                    .fromUserName(originalId)         // 从公众号发出
                    .toUserName(originalMessage.getFromUserName()) // 回复给用户
                    .createTime(String.valueOf(System.currentTimeMillis() / 1000L))
                    .content(replyContent)
                    .msgType("text")
                    .build();

            log.debug("创建回复消息成功：{}", replyMessage.getSummary());
            return replyMessage;

        } catch (Exception e) {
            log.error("创建回复消息异常", e);
            return null;
        }
    }

    /**
     * 手动解析消息（备用方案）
     * @param xmlContent XML内容
     * @return 微信消息实体
     */
    private WeixinMessage parseMessageManually(String xmlContent) {
        try {
            String fromUserName = extractXmlValue(xmlContent, "FromUserName");
            String toUserName = extractXmlValue(xmlContent, "ToUserName");
            String createTime = extractXmlValue(xmlContent, "CreateTime");
            String msgType = extractXmlValue(xmlContent, "MsgType");
            String content = extractXmlValue(xmlContent, "Content");
            String msgId = extractXmlValue(xmlContent, "MsgId");

            if (fromUserName == null || toUserName == null) {
                log.warn("手动解析失败：必要字段缺失");
                return null;
            }

            return WeixinMessage.builder()
                    .msgId(msgId != null ? msgId : generateMessageId())
                    .fromUserName(fromUserName)
                    .toUserName(toUserName)
                    .createTime(createTime != null ? createTime : String.valueOf(System.currentTimeMillis() / 1000L))
                    .content(content != null ? content : "")
                    .msgType(msgType != null ? msgType : "text")
                    .build();

        } catch (Exception e) {
            log.error("手动解析消息异常", e);
            return null;
        }
    }

    /**
     * 从XML中提取指定标签的值
     * @param xmlContent XML内容
     * @param tagName 标签名
     * @return 标签值
     */
    private String extractXmlValue(String xmlContent, String tagName) {
        try {
            String startTag = "<" + tagName + "><![CDATA[";
            String endTag = "]]></" + tagName + ">";

            int start = xmlContent.indexOf(startTag);
            if (start != -1) {
                start += startTag.length();
                int end = xmlContent.indexOf(endTag, start);
                if (end != -1) {
                    return xmlContent.substring(start, end);
                }
            }

            // 尝试无CDATA的格式
            startTag = "<" + tagName + ">";
            endTag = "</" + tagName + ">";
            start = xmlContent.indexOf(startTag);
            if (start != -1) {
                start += startTag.length();
                int end = xmlContent.indexOf(endTag, start);
                if (end != -1) {
                    return xmlContent.substring(start, end);
                }
            }

            return null;
        } catch (Exception e) {
            log.warn("提取XML值失败，标签：{}", tagName, e);
            return null;
        }
    }

    /**
     * 生成消息ID
     */
    private String generateMessageId() {
        return System.currentTimeMillis() + "_" + Thread.currentThread().getId();
    }
}