package com.gaoyifeng.aioserver.trigger;

import com.gaoyifeng.aioserver.api.IChatService;
import com.gaoyifeng.aioserver.api.dto.chat.request.ChatRequest;
import com.gaoyifeng.aioserver.api.dto.chat.response.ChatResponse;
import com.gaoyifeng.aioserver.domain.chat.service.ChatService;
import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * AI聊天控制器 - DDD架构实现
 * 实现AI聊天功能接口
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/ai")
public class ChatController implements IChatService {

    @Autowired
    private ChatService chatService;

    /**
     * AI聊天对话
     * @param request 聊天请求
     * @return AI回复结果
     */
    @Override
    @PostMapping("/chat")
    public Result<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            log.info("接收到AI聊天请求：{}", request.getSummary());

            // 参数验证
            if (request == null) {
                return Result.fail(ResultCode.PARAM_ERROR, "请求参数不能为空");
            }

            String message = request.getMessage();
            if (message == null || message.trim().isEmpty()) {
                return Result.fail(ResultCode.PARAM_ERROR, "消息内容不能为空");
            }

            // 调用服务层处理
            ChatResponse response = chatService.processChatMessage(request);

            log.info("AI聊天处理成功，回复长度：{}", response.getReply().length());
            return Result.success(response);

        } catch (IllegalArgumentException e) {
            log.warn("AI聊天请求参数错误：{}", e.getMessage());
            return Result.fail(ResultCode.PARAM_ERROR, e.getMessage());
        } catch (RuntimeException e) {
            log.warn("AI聊天业务错误：{}", e.getMessage());
            return Result.fail(ResultCode.BUSINESS_ERROR, e.getMessage());
        } catch (Exception e) {
            log.error("AI聊天处理异常", e);
            return Result.fail(ResultCode.SYSTEM_ERROR, "AI聊天服务暂时不可用，请稍后再试");
        }
    }
}