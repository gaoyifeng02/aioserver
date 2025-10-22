package com.gaoyifeng.aioserver.app.weixin.assembler;

import com.gaoyifeng.aioserver.domain.weixin.model.entity.WeixinMessage;
import com.gaoyifeng.aioserver.infrastructure.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 消息对象转换器 - App层
 * 负责DTO与领域对象之间的转换，以及与技术层的交互
 */
@Slf4j
@Component
public class MessageAssembler {

    /**
     * XML字符串转换为微信消息实体
     * @param xmlContent XML内容
     * @return 微信消息实体
     */
    public WeixinMessage xmlToMessage(String xmlContent) {
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
    public WeixinMessage createReplyMessage(WeixinMessage originalMessage, String replyContent, String originalId) {
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