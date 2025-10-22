package com.gaoyifeng.aioserver.domain.weixin.adapter.repository;

import com.gaoyifeng.aioserver.domain.weixin.model.aggregate.MessageConversation;
import com.gaoyifeng.aioserver.domain.weixin.model.entity.WeixinMessage;

/**
 * 微信消息仓储接口 - Domain层
 * 定义消息持久化的抽象接口，具体实现在Infrastructure层
 */
public interface WeixinMessageRepository {

    /**
     * 保存消息对话
     * @param conversation 消息对话聚合根
     * @return 保存结果
     */
    boolean saveConversation(MessageConversation conversation);

    /**
     * 查找消息对话
     * @param conversationId 对话ID
     * @return 消息对话聚合根
     */
    MessageConversation findConversation(String conversationId);

    /**
     * 根据OpenID查找最近的对话
     * @param openId 用户OpenID
     * @return 消息对话聚合根
     */
    MessageConversation findLatestConversationByOpenId(String openId);

    /**
     * 保存单个消息
     * @param message 微信消息实体
     * @return 保存结果
     */
    boolean saveMessage(WeixinMessage message);

    /**
     * 查找消息
     * @param msgId 消息ID
     * @return 微信消息实体
     */
    WeixinMessage findMessage(String msgId);
}