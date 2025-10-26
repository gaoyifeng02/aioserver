package com.gaoyifeng.aioserver.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 微信消息PO对象
 * 对应数据库表：weixin_message
 *
 * @author gaoyifeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("weixin_message")
public class WeixinMessagePO {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("msg_id")
    private String msgId;

    @TableField("conversation_id")
    private String conversationId;

    @TableField("to_user_name")
    private String toUserName;

    @TableField("from_user_name")
    private String fromUserName;

    @TableField("msg_type")
    private String msgType;

    @TableField("content")
    private String content;

    @TableField("media_id")
    private String mediaId;

    @TableField("format")
    private String format;

    @TableField("recognition")
    private String recognition;

    @TableField("thumb_media_id")
    private String thumbMediaId;

    @TableField("location_x")
    private BigDecimal locationX;

    @TableField("location_y")
    private BigDecimal locationY;

    @TableField("scale")
    private Integer scale;

    @TableField("label")
    private String label;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("url")
    private String url;

    @TableField("pic_url")
    private String picUrl;

    @TableField("create_time")
    private Integer createTime;

    @TableField("msg_direction")
    private Integer msgDirection;

    @TableField("processed")
    private Integer processed;

    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}