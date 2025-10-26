package com.gaoyifeng.aioserver.infrastructure.repository;

import com.gaoyifeng.aioserver.domain.weixin.adapter.port.IMessageRepositoryPort;
import com.gaoyifeng.aioserver.domain.weixin.model.aggregate.MessageConversation;
import com.gaoyifeng.aioserver.domain.weixin.model.entity.WeixinMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 微信消息仓储实现 - Infrastructure层
 * 实现Port接口，使用内存存储（类似参考项目的简化实现）
 *
 * @author gaoyifeng
 */
@Slf4j
@Repository
public class WeixinMessageRepositoryImpl implements IMessageRepositoryPort {

    // 内存存储（简化实现，类似参考项目）
    private final ConcurrentMap<String, MessageConversation> conversationCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, WeixinMessage> messageCache = new ConcurrentHashMap<>();

    @Override
    public boolean saveConversation(MessageConversation conversation) {
        try {
            if (conversation == null || conversation.getConversationId() == null) {
                log.warn("保存对话失败：对话或对话ID为空");
                return false;
            }

            conversationCache.put(conversation.getConversationId(), conversation);
            log.info("保存消息对话成功，对话ID：{}", conversation.getConversationId());
            return true;
        } catch (Exception e) {
            log.error("保存消息对话失败", e);
            return false;
        }
    }

    @Override
    public MessageConversation findConversation(String conversationId) {
        try {
            if (conversationId == null || conversationId.trim().isEmpty()) {
                log.warn("查找对话失败：对话ID为空");
                return null;
            }

            MessageConversation conversation = conversationCache.get(conversationId);
            log.debug("查找消息对话，对话ID：{}，结果：{}", conversationId, conversation != null ? "找到" : "未找到");
            return conversation;
        } catch (Exception e) {
            log.error("查找消息对话失败，对话ID：{}", conversationId, e);
            return null;
        }
    }

    @Override
    public MessageConversation findLatestConversationByOpenId(String openId) {
        try {
            if (openId == null || openId.trim().isEmpty()) {
                log.warn("查找对话失败：OpenID为空");
                return null;
            }

            // 简化实现：遍历所有对话，找到该OpenID的最新活跃对话
            return conversationCache.values().stream()
                    .filter(conv -> openId.equals(conv.getOpenId()) && conv.isActive())
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            log.error("查找最新对话失败，OpenID：{}", openId, e);
            return null;
        }
    }

    @Override
    public boolean saveMessage(WeixinMessage message) {
        try {
            if (message == null || message.getMsgId() == null) {
                log.warn("保存消息失败：消息或消息ID为空");
                return false;
            }

            messageCache.put(message.getMsgId(), message);
            log.info("保存微信消息成功，消息ID：{}", message.getMsgId());
            return true;
        } catch (Exception e) {
            log.error("保存微信消息失败", e);
            return false;
        }
    }

    @Override
    public WeixinMessage findMessage(String msgId) {
        try {
            if (msgId == null || msgId.trim().isEmpty()) {
                log.warn("查找消息失败：消息ID为空");
                return null;
            }

            WeixinMessage message = messageCache.get(msgId);
            log.debug("查找微信消息，消息ID：{}，结果：{}", msgId, message != null ? "找到" : "未找到");
            return message;
        } catch (Exception e) {
            log.error("查找微信消息失败，消息ID：{}", msgId, e);
            return null;
        }
    }

    @Override
    public List<WeixinMessage> findMessagesByConversationId(String conversationId) {
        try {
            // 简化实现：返回所有消息
            return new ArrayList<>(messageCache.values());
        } catch (Exception e) {
            log.error("查找对话消息失败，对话ID：{}", conversationId, e);
            return null;
        }
    }

    @Override
    public List<MessageConversation> findConversationsByOpenId(String openId) {
        try {
            // 简化实现：过滤返回该用户的对话
            return conversationCache.values().stream()
                    .filter(conv -> openId.equals(conv.getOpenId()))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } catch (Exception e) {
            log.error("查找用户对话失败，OpenID：{}", openId, e);
            return null;
        }
    }

    /**
     * 获取存储统计信息（用于监控）
     */
    public String getStorageStats() {
        return String.format("内存存储 - 对话数量：%d，消息数量：%d",
                conversationCache.size(), messageCache.size());
    }
}