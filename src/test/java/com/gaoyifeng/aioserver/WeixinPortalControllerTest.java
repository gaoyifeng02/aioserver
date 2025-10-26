package com.gaoyifeng.aioserver;

import com.gaoyifeng.aioserver.domain.weixin.model.entity.WeixinMessage;
import com.gaoyifeng.aioserver.domain.weixin.model.valobj.MessageContent;
import com.gaoyifeng.aioserver.domain.weixin.service.MessageService;
import com.gaoyifeng.aioserver.domain.weixin.service.SignatureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 微信服务域DDD架构测试
 */
@SpringBootTest
class WeixinPortalControllerTest {

    @Autowired
    private SignatureService signatureService;

    @Autowired
    private MessageService messageService;

    @Test
    void testSignatureService() {
        // 测试签名验证服务
        String timestamp = "1234567890";
        String nonce = "abc123";
        String signature = "test-signature";

        // 重构后的verifySignature方法参数顺序改变了
        boolean result = signatureService.verifySignature(signature, timestamp, nonce);

        // 由于签名是错误的，应该返回false
        assertFalse(result);
    }

    @Test
    void testMessageServiceValidation() {
        // 测试消息验证
        WeixinMessage message = WeixinMessage.builder()
                .msgId("test-msg-id")
                .fromUserName("test-openid")
                .toUserName("test-original-id")
                .createTime("1234567890")
                .content("你好")
                .msgType("text")
                .build();

        boolean isValid = messageService.validateMessage(message);
        assertTrue(isValid);

        // 测试无效消息
        WeixinMessage invalidMessage = WeixinMessage.builder()
                .msgId("")
                .fromUserName("")
                .toUserName("")
                .content("")
                .msgType("")
                .build();

        boolean isInvalidValid = messageService.validateMessage(invalidMessage);
        assertFalse(isInvalidValid);
    }

    @Test
    void testMessageProcessing() {
        // 测试消息处理
        WeixinMessage message = WeixinMessage.builder()
                .msgId("test-msg-id")
                .fromUserName("test-openid")
                .toUserName("test-original-id")
                .createTime("1234567890")
                .content("你好")
                .msgType("text")
                .build();

        String replyContent = messageService.processMessageContent(message);

        assertNotNull(replyContent);
        assertTrue(replyContent.contains("欢迎使用"));
    }
}