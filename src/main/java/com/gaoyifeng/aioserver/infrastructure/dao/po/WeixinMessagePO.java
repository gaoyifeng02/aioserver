package com.gaoyifeng.aioserver.infrastructure.dao.po;

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
public class WeixinMessagePO {

    private Long id;

    private String msgId;

    private String conversationId;

    private String toUserName;

    private String fromUserName;

    private String msgType;

    private String content;

    private String mediaId;

    private String format;

    private String recognition;

    private String thumbMediaId;

    private BigDecimal locationX;

    private BigDecimal locationY;

    private Integer scale;

    private String label;

    private String title;

    private String description;

    private String url;

    private String picUrl;

    private Integer createTime;

    private Integer msgDirection;

    private Integer processed;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private Integer deleted;
}