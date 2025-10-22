package com.gaoyifeng.aioserver.domain.weixin.model.aggregate;

import com.gaoyifeng.aioserver.domain.weixin.model.entity.WeixinMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信消息对话聚合根
 * 管理一次完整的消息对话流程
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageConversation {

    /**
     * 对话ID
     */
    private String conversationId;

    /**
     * 用户OpenID
     */
    private String openId;

    /**
     * 公众号原始ID
     */
    private String originalId;

    /**
     * 消息列表
     */
    private List<WeixinMessage> messages;

    /**
     * 对话开始时间
     */
    private String startTime;

    /**
     * 对话状态：active-活跃，closed-已关闭
     */
    private String status;

    /**
     * 初始化对话
     */
    public static MessageConversation create(String openId, String originalId) {
        MessageConversation conversation = MessageConversation.builder()
                .conversationId(generateConversationId())
                .openId(openId)
                .originalId(originalId)
                .messages(new ArrayList<>())
                .startTime(String.valueOf(System.currentTimeMillis()))
                .status("active")
                .build();
        return conversation;
    }

    /**
     * 添加消息到对话
     */
    public void addMessage(WeixinMessage message) {
        if (message != null && message.isValid()) {
            messages.add(message);
        }
    }

    /**
     * 获取最新消息
     */
    public WeixinMessage getLatestMessage() {
        return messages.isEmpty() ? null : messages.get(messages.size() - 1);
    }

    /**
     * 获取消息数量
     */
    public int getMessageCount() {
        return messages.size();
    }

    /**
     * 关闭对话
     */
    public void close() {
        this.status = "closed";
    }

    /**
     * 判断对话是否活跃
     */
    public boolean isActive() {
        return "active".equals(status);
    }

    /**
     * 生成对话ID
     */
    private static String generateConversationId() {
        return "conv_" + System.currentTimeMillis() + "_" + Thread.currentThread().getId();
    }
}