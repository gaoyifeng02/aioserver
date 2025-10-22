package com.gaoyifeng.aioserver.domain.weixin.service;

import com.gaoyifeng.aioserver.domain.weixin.model.entity.WeixinMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 微信消息处理领域服务
 * 专注于业务逻辑处理，技术实现（XML解析等）委托给Infrastructure层
 */
@Slf4j
@Service
public class MessageService {

    /**
     * 验证消息是否符合业务规则
     * @param message 消息实体
     * @return 是否有效
     */
    public boolean validateMessage(WeixinMessage message) {
        if (message == null) {
            log.warn("消息验证失败：消息为空");
            return false;
        }

        if (!message.isValid()) {
            log.warn("消息验证失败：消息格式不正确");
            return false;
        }

        // 业务规则：验证消息长度
        if (message.getContent() != null && message.getContent().length() > 2048) {
            log.warn("消息验证失败：消息内容过长，长度：{}", message.getContent().length());
            return false;
        }

        log.debug("消息验证通过：{}", message.getSummary());
        return true;
    }

    /**
     * 处理消息内容（纯业务逻辑）
     * @param message 接收到的消息
     * @return 处理后的回复内容
     */
    public String processMessageContent(WeixinMessage message) {
        if (!validateMessage(message)) {
            return "抱歉，消息格式错误";
        }

        String content = message.getContent();
        if (content == null || content.trim().isEmpty()) {
            return "您好，请输入有效内容";
        }

        // 业务规则：根据消息内容生成回复
        String replyContent = generateReplyByContent(content.trim());

        log.info("消息处理完成，原消息：{}，回复：{}", message.getSummary(), replyContent);
        return replyContent;
    }

    /**
     * 根据消息内容生成回复的业务逻辑
     * @param content 消息内容
     * @return 回复内容
     */
    private String generateReplyByContent(String content) {
        // 关键词匹配业务逻辑
        if (isGreetingMessage(content)) {
            return "你好，欢迎使用微信服务！我是您的智能助手。";
        } else if (isHelpRequest(content)) {
            return generateHelpMessage();
        } else if (isMenuRequest(content)) {
            return generateMenuMessage();
        } else if (isFeedbackMessage(content)) {
            return "感谢您的反馈！我们会认真处理您的建议。";
        } else {
            return generateDefaultReply(content);
        }
    }

    /**
     * 判断是否为问候消息
     */
    private boolean isGreetingMessage(String content) {
        return "你好".equals(content) || "hello".equalsIgnoreCase(content)
                || "hi".equalsIgnoreCase(content) || "您好".equals(content);
    }

    /**
     * 判断是否为帮助请求
     */
    private boolean isHelpRequest(String content) {
        return "帮助".equals(content) || "help".equalsIgnoreCase(content)
                || "?" .equals(content) || "？".equals(content);
    }

    /**
     * 判断是否为菜单请求
     */
    private boolean isMenuRequest(String content) {
        return "菜单".equals(content) || "menu".equalsIgnoreCase(content);
    }

    /**
     * 判断是否为反馈消息
     */
    private boolean isFeedbackMessage(String content) {
        return content.startsWith("反馈") || content.startsWith("feedback")
                || content.startsWith("建议") || content.startsWith("投诉");
    }

    /**
     * 生成帮助消息
     */
    private String generateHelpMessage() {
        return "📖 功能说明：\n" +
               "1. 输入「你好」- 获取欢迎语\n" +
               "2. 输入「菜单」- 查看功能菜单\n" +
               "3. 输入「反馈xxx」- 提交建议\n" +
               "4. 其他内容 - 智能回复\n\n" +
               "💡 如有疑问，请输入「帮助」";
    }

    /**
     * 生成菜单消息
     */
    private String generateMenuMessage() {
        return "🎯 功能菜单：\n" +
               "┌─────────────┐\n" +
               "│ 1. 智能对话  │\n" +
               "│ 2. 服务查询  │\n" +
               "│ 3. 意见反馈  │\n" +
               "│ 4. 帮助说明  │\n" +
               "└─────────────┘\n\n" +
               "请输入对应数字或关键词";
    }

    /**
     * 生成默认回复
     */
    private String generateDefaultReply(String content) {
        return "📝 您说：「" + content + "」\n\n" +
               "我已收到您的消息，如需帮助请输入「帮助」";
    }
}