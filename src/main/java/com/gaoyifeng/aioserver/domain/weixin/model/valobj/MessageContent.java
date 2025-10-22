package com.gaoyifeng.aioserver.domain.weixin.model.valobj;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信消息内容值对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageContent {

    /**
     * 消息内容
     */
    @XStreamAlias("Content")
    private String content;

    /**
     * 消息类型；text、image、voice、video、shortvideo、location、link
     */
    @XStreamAlias("MsgType")
    private String msgType;

    /**
     * 验证消息内容是否有效
     */
    public boolean isValid() {
        return content != null && !content.trim().isEmpty() && msgType != null && !msgType.trim().isEmpty();
    }

    /**
     * 获取消息长度
     */
    public int getLength() {
        return content != null ? content.length() : 0;
    }

    /**
     * 是否为文本消息
     */
    public boolean isTextMessage() {
        return "text".equals(msgType);
    }
}