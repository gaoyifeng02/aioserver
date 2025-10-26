package com.gaoyifeng.aioserver.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("weixin_conversation")
public class WeixinConversationPO {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("conversation_id")
    private String conversationId;

    @TableField("open_id")
    private String openId;

    @TableField("original_id")
    private String originalId;

    @TableField("status")
    private Integer status;

    @TableField("first_message_time")
    private LocalDateTime firstMessageTime;

    @TableField("last_message_time")
    private LocalDateTime lastMessageTime;

    @TableField("message_count")
    private Integer messageCount;

    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}