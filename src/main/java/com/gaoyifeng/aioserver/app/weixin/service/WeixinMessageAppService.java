package com.gaoyifeng.aioserver.app.weixin.service;

import com.gaoyifeng.aioserver.app.weixin.dto.SignatureVerifyRequest;
import com.gaoyifeng.aioserver.app.weixin.dto.MessageProcessRequest;
import com.gaoyifeng.aioserver.app.weixin.dto.MessageProcessResponse;
import com.gaoyifeng.aioserver.app.weixin.assembler.MessageAssembler;
import com.gaoyifeng.aioserver.domain.weixin.model.entity.WeixinMessage;
import com.gaoyifeng.aioserver.domain.weixin.model.aggregate.MessageConversation;
import com.gaoyifeng.aioserver.domain.weixin.adapter.port.IMessageRepositoryPort;
import com.gaoyifeng.aioserver.domain.weixin.service.SignatureService;
import com.gaoyifeng.aioserver.domain.weixin.service.MessageService;
import com.gaoyifeng.aioserver.infrastructure.config.WeixinConfig;
import com.gaoyifeng.aioserver.infrastructure.util.XmlUtil;
import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信消息应用服务
 * 负责编排领域服务，处理完整的消息流程
 * 职责：
 * 1. 编排领域服务完成业务用例
 * 2. 处理事务边界
 * 3. 数据转换（DTO <-> 领域对象）
 */
@Slf4j
@Service
public class WeixinMessageAppService {

    @Autowired
    private SignatureService signatureService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private IMessageRepositoryPort weixinMessageRepository;

    @Autowired
    private MessageAssembler messageAssembler;

    @Autowired
    private WeixinConfig weixinConfig;

    /**
     * 验证微信签名
     * @param request 签名验证请求
     * @return 验证结果
     */
    public Result<String> verifySignature(SignatureVerifyRequest request) {
        try {
            log.info("开始验证微信签名：{}", request);

            // 参数验证
            if (request == null || !request.isValid()) {
                return Result.fail(ResultCode.PARAM_ERROR, "微信签名验证请求参数不完整");
            }

            // 调用领域服务验证签名
            boolean isValid = signatureService.verifySignature(
                    request.getSignature(),
                    request.getTimestamp(),
                    request.getNonce()
            );

            if (!isValid) {
                return Result.fail(ResultCode.BUSINESS_ERROR, "微信签名验证失败，请检查配置");
            }

            log.info("微信签名验证成功");
            return Result.success(request.getEchostr());

        } catch (Exception e) {
            log.error("微信签名验证异常", e);
            return Result.fail(ResultCode.SYSTEM_ERROR, "微信签名验证过程中发生系统异常");
        }
    }

    /**
     * 处理微信消息
     * 编排多个领域服务完成完整的消息处理流程
     * @param request 消息处理请求
     * @return 处理结果
     */
    public Result<MessageProcessResponse> processMessage(MessageProcessRequest request) {
        try {
            log.info("开始处理微信消息：{}", request);

            // 1. 参数验证
            if (request == null || !request.isValid()) {
                return Result.fail(ResultCode.PARAM_ERROR, "微信消息处理请求参数不完整");
            }

            // 2. 验证签名
            boolean signatureValid = signatureService.verifySignature(
                    request.getSignature(),
                    request.getTimestamp(),
                    request.getNonce()
            );
            if (!signatureValid) {
                return Result.fail(ResultCode.BUSINESS_ERROR, "微信签名验证失败，消息可能被篡改");
            }

            // 3. XML解析转换为领域对象（Infrastructure层）
            WeixinMessage receivedMessage = messageAssembler.xmlToMessage(request.getRequestBody());
            if (receivedMessage == null) {
                return Result.fail(ResultCode.BUSINESS_ERROR, "微信消息格式解析失败，请检查消息格式");
            }

            // 4. 领域层业务逻辑验证
            boolean messageValid = messageService.validateMessage(receivedMessage);
            if (!messageValid) {
                return Result.fail(ResultCode.BUSINESS_ERROR, "微信消息内容验证失败，消息不符合业务规则");
            }

            // 5. 查找或创建消息对话聚合
            MessageConversation conversation = findOrCreateConversation(receivedMessage.getFromUserName());

            // 6. 添加消息到对话聚合
            conversation.addMessage(receivedMessage);

            // 7. 领域服务处理消息内容
            String replyContent = messageService.processMessageContent(receivedMessage);

            // 8. 创建回复消息实体
            WeixinMessage replyMessage = messageAssembler.createReplyMessage(
                    receivedMessage, replyContent, weixinConfig.getOriginalId());

            // 9. 添加回复消息到对话
            conversation.addMessage(replyMessage);

            // 10. 持久化聚合根
            boolean saved = weixinMessageRepository.saveConversation(conversation);
            if (!saved) {
                log.warn("消息对话保存失败，对话ID：{}", conversation.getConversationId());
            }

            // 11. 转换为XML格式（Infrastructure层）
            String replyXml = XmlUtil.beanToXml(replyMessage);

            // 12. 构建响应DTO
            MessageProcessResponse response = MessageProcessResponse.builder()
                    .replyXml(replyXml)
                    .conversationId(conversation.getConversationId())
                    .originalContent(receivedMessage.getSummary())
                    .replyContent(replyContent)
                    .status("success")
                    .message("处理成功")
                    .build();

            log.info("微信消息处理完成，对话ID：{}，回复内容：{}",
                    conversation.getConversationId(), replyContent);

            return Result.success(response);

        } catch (Exception e) {
            log.error("微信消息处理异常", e);
            return Result.fail(ResultCode.SYSTEM_ERROR, "微信消息处理过程中发生系统异常");
        }
    }

    /**
     * 查找或创建消息对话聚合
     */
    private MessageConversation findOrCreateConversation(String openId) {
        // 查找最近的活跃对话
        MessageConversation conversation = weixinMessageRepository.findLatestConversationByOpenId(openId);

        if (conversation == null || !conversation.isActive()) {
            // 创建新的对话
            conversation = MessageConversation.create(openId, weixinConfig.getOriginalId());
        }

        return conversation;
    }

}
