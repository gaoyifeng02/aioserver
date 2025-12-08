package com.gaoyifeng.aioserver.api.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * AI聊天响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    /**
     * AI回复内容
     */
    private String reply;

    /**
     * 回复时间戳
     */
    private LocalDateTime timestamp;

    /**
     * 构造函数 - 生成当前时间戳
     * @param reply AI回复内容
     */
    public ChatResponse(String reply) {
        this.reply = reply;
        this.timestamp = LocalDateTime.now();
    }
}