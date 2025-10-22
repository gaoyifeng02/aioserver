package com.gaoyifeng.aioserver.app.weixin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信消息处理响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageProcessResponse {

    /**
     * 回复消息XML
     */
    private String replyXml;

    /**
     * 对话ID
     */
    private String conversationId;

    /**
     * 原始消息内容摘要
     */
    private String originalContent;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 处理状态
     */
    private String status;

    /**
     * 处理消息
     */
    private String message;

    /**
     * 创建成功响应
     */
    public static MessageProcessResponse success(String replyXml, String conversationId,
                                               String originalContent, String replyContent) {
        return MessageProcessResponse.builder()
                .replyXml(replyXml)
                .conversationId(conversationId)
                .originalContent(originalContent)
                .replyContent(replyContent)
                .status("success")
                .message("处理成功")
                .build();
    }

    /**
     * 创建失败响应
     */
    public static MessageProcessResponse fail(String message) {
        return MessageProcessResponse.builder()
                .status("fail")
                .message(message)
                .build();
    }
}