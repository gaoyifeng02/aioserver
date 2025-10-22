package com.gaoyifeng.aioserver.domain.weixin.model.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信消息实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WeixinMessage {

    /**
     * 消息ID
     */
    @XStreamAlias("MsgId")
    private String msgId;

    /**
     * 发送方账号（OpenID）
     */
    @XStreamAlias("FromUserName")
    private String fromUserName;

    /**
     * 接收方账号（公众号原始ID）
     */
    @XStreamAlias("ToUserName")
    private String toUserName;

    /**
     * 创建时间
     */
    @XStreamAlias("CreateTime")
    private String createTime;

    /**
     * 消息内容
     */
    @XStreamAlias("Content")
    private String content;

    /**
     * 消息类型
     */
    @XStreamAlias("MsgType")
    private String msgType;

    /**
     * 验证消息是否有效
     */
    public boolean isValid() {
        return fromUserName != null && !fromUserName.trim().isEmpty()
                && toUserName != null && !toUserName.trim().isEmpty()
                && msgId != null && !msgId.trim().isEmpty()
                && content != null;
    }

    /**
     * 获取消息摘要（前50个字符）
     */
    public String getSummary() {
        if (content == null) {
            return "";
        }
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }

    /**
     * 判断是否为用户发送给公众号的消息
     */
    public boolean isUserToOfficialAccount() {
        return fromUserName != null && toUserName != null
                && !fromUserName.equals(toUserName);
    }
}