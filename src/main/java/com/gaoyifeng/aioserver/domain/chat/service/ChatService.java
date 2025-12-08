package com.gaoyifeng.aioserver.domain.chat.service;

import com.gaoyifeng.aioserver.api.dto.chat.request.ChatRequest;
import com.gaoyifeng.aioserver.api.dto.chat.response.ChatResponse;
import com.gaoyifeng.aioserver.infrastructure.gateway.ZhiPuClinet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AI聊天领域服务
 * 处理AI聊天相关的业务逻辑
 */
@Slf4j
@Service
public class ChatService {

    @Autowired
    private ZhiPuClinet zhiPuClinet;

    /**
     * 处理AI聊天请求
     * @param request 聊天请求
     * @return AI回复响应
     */
    public ChatResponse processChatMessage(ChatRequest request) {
        try {
            log.info("接收到AI聊天请求：{}", request.getSummary());

            // 验证消息内容
            validateMessage(request.getMessage());

            // 调用智谱AI获取回复
            String aiReply = zhiPuClinet.chat(request.getMessage());

            // 构建响应
            ChatResponse response = new ChatResponse(aiReply);

            log.info("AI聊天处理成功，回复长度：{}", aiReply.length());
            return response;

        } catch (IllegalArgumentException e) {
            log.warn("AI聊天请求参数错误：{}", e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            // 捕获来自ZhiPuClinet的业务异常，直接传递
            log.error("AI聊天处理异常，请求：{}", request.getSummary(), e);
            throw e;
        } catch (Exception e) {
            // 捕获其他未知异常，返回友好提示
            log.error("AI聊天处理未知异常，请求：{}", request.getSummary(), e);
            throw new RuntimeException("AI聊天服务暂时不可用，请稍后重试");
        }
    }

    /**
     * 验证消息内容的合法性
     * @param message 消息内容
     */
    private void validateMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("消息内容不能为空");
        }

        // 检查消息长度
        if (message.length() > 2000) {
            throw new IllegalArgumentException("消息内容不能超过2000字符");
        }

        // 检查是否包含恶意内容（简单示例）
        if (containsMaliciousContent(message)) {
            throw new IllegalArgumentException("消息内容包含不当信息");
        }
    }

    /**
     * 检查消息是否包含恶意内容
     * @param message 消息内容
     * @return 是否包含恶意内容
     */
    private boolean containsMaliciousContent(String message) {
        // 这里可以实现更复杂的恶意内容检测逻辑
        // 目前只是简单的示例
        String lowerMessage = message.toLowerCase();
        return lowerMessage.contains("暴力") || lowerMessage.contains("色情");
    }
}