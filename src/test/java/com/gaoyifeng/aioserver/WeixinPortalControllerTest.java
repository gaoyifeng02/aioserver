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
        String token = "test-token";
        String timestamp = "1234567890";
        String nonce = "abc123";
        String signature = "test-signature";

        // 这里应该生成正确的签名，但为了测试简化处理
        boolean result = signatureService.verifySignature(token, signature, timestamp, nonce);

        // 由于签名是错误的，应该返回false
        assertFalse(result);
    }

    @Test
    void testMessageService() {
        // 测试消息解析
        String xmlContent = "<xml>" +
                "<ToUserName><![CDATA[test-to-user]]></ToUserName>" +
                "<FromUserName><![CDATA[test-from-user]]></FromUserName>" +
                "<CreateTime>1234567890</CreateTime>" +
                "<MsgType><![CDATA[text]]></MsgType>" +
                "<Content><![CDATA[你好]]></Content>" +
                "<MsgId>1234567890123456</MsgId>" +
                "</xml>";

        WeixinMessage message = messageService.parseMessage(xmlContent);

        assertNotNull(message);
        assertEquals("test-from-user", message.getFromUserName());
        assertEquals("test-to-user", message.getToUserName());
        assertTrue(message.isValid());
    }

    @Test
    void testMessageProcessing() {
        // 测试消息处理
        WeixinMessage message = WeixinMessage.builder()
                .msgId("test-msg-id")
                .fromUserName("test-openid")
                .toUserName("test-original-id")
                .createTime("1234567890")
                .messageContent(MessageContent.builder()
                        .content("你好")
                        .msgType("text")
                        .build())
                .build();

        String replyContent = messageService.processMessage(message);

        assertNotNull(replyContent);
        assertTrue(replyContent.contains("欢迎使用"));
    }

    @Test
    void testMessageContentValueObject() {
        // 测试消息内容值对象
        MessageContent content = MessageContent.builder()
                .content("测试消息")
                .msgType("text")
                .build();

        assertTrue(content.isValid());
        assertTrue(content.isTextMessage());
        assertEquals(4, content.getLength());

        // 测试无效内容
        MessageContent invalidContent = MessageContent.builder()
                .content("")
                .msgType("text")
                .build();

        assertFalse(invalidContent.isValid());
    }
}