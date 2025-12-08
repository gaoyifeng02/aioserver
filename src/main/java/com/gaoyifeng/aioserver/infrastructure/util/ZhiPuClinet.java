package com.gaoyifeng.aioserver.infrastructure.util;

import ai.z.openapi.ZhipuAiClient;
import ai.z.openapi.service.model.ChatCompletionCreateParams;
import ai.z.openapi.service.model.ChatCompletionResponse;
import ai.z.openapi.service.model.ChatMessage;
import ai.z.openapi.service.model.ChatMessageRole;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaoyifeng.aioserver.app.config.ZhiPuConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 智谱AI客户端工具类
 * 提供与智谱AI交互的功能
 */
@Slf4j
@Component
public class ZhiPuClinet {

    @Autowired
    private ZhiPuConfig zhiPuConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * AI聊天对话
     * @param message 用户消息
     * @return AI回复内容
     */
    public String chat(String message) {
        try {
            log.info("调用智谱AI，消息长度：{}", message.length());

            // 创建客户端实例
            ZhipuAiClient client = ZhipuAiClient.builder()
                    .apiKey(zhiPuConfig.getApiKey())
                    .build();

            // 创建聊天完成请求
            ChatCompletionCreateParams request = ChatCompletionCreateParams.builder()
                    .model(zhiPuConfig.getModel())
                    .maxTokens(zhiPuConfig.getMaxTokens())
                    .temperature(zhiPuConfig.getTemperature().floatValue())
                    .messages(Arrays.asList(
                            ChatMessage.builder()
                                    .role(ChatMessageRole.USER.value())
                                    .content(message)
                                    .build()
                    ))
                    .build();

            // 发送请求
            ChatCompletionResponse response = client.chat().createChatCompletion(request);

            // 处理响应
            if (response.isSuccess()) {
                Object messageObj = response.getData().getChoices().get(0).getMessage();

                // 使用JSON对象解析提取content内容
                String reply = extractContentFromChatMessage(messageObj);

                log.info("智谱AI回复成功，回复长度：{}", reply.length());
                return reply;
            } else {
                log.error("智谱AI调用失败：{}", response.getMsg());
                throw new RuntimeException("智谱AI调用失败：" + response.getMsg());
            }

        } catch (Exception e) {
            log.error("智谱AI调用异常", e);
            throw new RuntimeException("智谱AI服务暂时不可用：" + e.getMessage(), e);
        }
    }

    /**
     * 从ChatMessage对象中提取content内容
     * 使用JSON对象解析，遵循DDD原则
     * @param messageObj ChatMessage对象
     * @return content内容
     */
    private String extractContentFromChatMessage(Object messageObj) {
        if (messageObj == null) {
            return "AI回复为空";
        }

        try {
            // 将ChatMessage对象序列化为JSON字符串
            String messageJson = objectMapper.writeValueAsString(messageObj);
            log.debug("ChatMessage JSON: {}", messageJson);

            // 解析JSON并提取content字段
            JsonNode jsonNode = objectMapper.readTree(messageJson);
            JsonNode contentNode = jsonNode.get("content");

            if (contentNode != null && !contentNode.isNull()) {
                return contentNode.asText();
            } else {
                log.warn("ChatMessage对象中未找到content字段: {}", messageJson);
                return messageObj.toString();
            }

        } catch (Exception e) {
            log.error("解析ChatMessage对象失败，使用原始toString: {}", messageObj, e);
            // 降级处理：返回原始对象的字符串表示
            return messageObj.toString();
        }
    }

    /**
     * 兼容性方法 - 保留原有send方法
     * @deprecated 使用chat方法替代
     */
    @Deprecated
    public void send() {
        chat("你好，请介绍一下自己");
    }
}
