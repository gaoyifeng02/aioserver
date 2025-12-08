package com.gaoyifeng.aioserver.api.dto.chat.request;

import lombok.Data;

/**
 * AI聊天请求DTO
 */
@Data
public class ChatRequest {

    /**
     * 用户消息内容
     */
    private String message;

    /**
     * 获取请求摘要信息
     * @return 摘要
     */
    public String getSummary() {
        String messagePreview = message != null && message.length() > 20
            ? message.substring(0, 20) + "..."
            : message;
        return String.format("ChatRequest{message='%s'}", messagePreview);
    }
}