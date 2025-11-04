package com.gaoyifeng.aioserver.infrastructure.dao.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 微信消息对话PO对象
 * 对应数据库表：weixin_conversation
 *
 * @author gaoyifeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WeixinConversationPO {

    private Long id;

    private String conversationId;

    private String openId;

    private String originalId;

    private Integer status;

    private LocalDateTime firstMessageTime;

    private LocalDateTime lastMessageTime;

    private Integer messageCount;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private Integer deleted;
}