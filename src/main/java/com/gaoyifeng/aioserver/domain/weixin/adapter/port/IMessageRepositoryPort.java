package com.gaoyifeng.aioserver.domain.weixin.adapter.port;

import com.gaoyifeng.aioserver.domain.weixin.model.aggregate.MessageConversation;
import com.gaoyifeng.aioserver.domain.weixin.model.entity.WeixinMessage;

import java.util.List;

/**
 * 微信消息仓储端口接口 - Domain层
 * 遵循六边形架构，定义基础设施层必须提供的能力契约
 *
 * @author gaoyifeng
 */
public interface IMessageRepositoryPort {

    /**
     * 保存消息对话聚合根
     * @param conversation 消息对话聚合根
     * @return 保存结果
     */
    boolean saveConversation(MessageConversation conversation);

    /**
     * 查找消息对话聚合根
     * @param conversationId 对话ID
     * @return 消息对话聚合根
     */
    MessageConversation findConversation(String conversationId);

    /**
     * 根据OpenID查找最近的活跃对话
     * @param openId 用户OpenID
     * @return 消息对话聚合根
     */
    MessageConversation findLatestConversationByOpenId(String openId);

    /**
     * 保存单个消息实体
     * @param message 微信消息实体
     * @return 保存结果
     */
    boolean saveMessage(WeixinMessage message);

    /**
     * 查找单个消息实体
     * @param msgId 消息ID
     * @return 微信消息实体
     */
    WeixinMessage findMessage(String msgId);

    /**
     * 根据对话ID查找消息列表
     * @param conversationId 对话ID
     * @return 消息列表
     */
    List<WeixinMessage> findMessagesByConversationId(String conversationId);

    /**
     * 根据OpenID查找用户的所有对话
     * @param openId 用户OpenID
     * @return 对话列表
     */
    List<MessageConversation> findConversationsByOpenId(String openId);
}