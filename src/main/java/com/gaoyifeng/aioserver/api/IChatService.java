package com.gaoyifeng.aioserver.api;

import com.gaoyifeng.aioserver.api.dto.chat.request.ChatRequest;
import com.gaoyifeng.aioserver.api.dto.chat.response.ChatResponse;
import com.gaoyifeng.aioserver.types.common.Result;

/**
 * AI聊天服务接口
 * 定义AI聊天相关的API规范
 */
public interface IChatService {

    /**
     * AI聊天对话
     * @param request 聊天请求
     * @return AI回复结果
     */
    Result<ChatResponse> chat(ChatRequest request);
}