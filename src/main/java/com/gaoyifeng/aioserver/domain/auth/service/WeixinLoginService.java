package com.gaoyifeng.aioserver.domain.auth.service;

import com.gaoyifeng.aioserver.domain.auth.adapter.port.IUserRepository;
import com.gaoyifeng.aioserver.domain.auth.adapter.port.IWeixinLoginPort;
import com.gaoyifeng.aioserver.domain.auth.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

/**
 * 微信登录服务 - DDD充血模型
 * 参考study项目：WeixinLoginService实现
 * 负责编排微信登录相关的业务逻辑
 */
@Slf4j
@Service
public class WeixinLoginService {

    @Autowired
    private IWeixinLoginPort weixinLoginPort;

    @Autowired
    private IUserRepository userRepository;

    /**
     * 创建微信登录二维码
     * 参考study项目：createQrCodeTicket()方法
     * @return 登录票据
     * @throws Exception 创建失败时抛出异常
     */
    public String createLoginQrCode() throws Exception {
        try {
            log.info("开始创建微信登录二维码");

            // 生成唯一登录票据
            String ticket = generateTicket();

            // 调用端口创建二维码
            String qrCodeUrl = weixinLoginPort.createQrCode(ticket);

            log.info("微信登录二维码创建成功：ticket={}, qrCodeUrl长度={}",
                    ticket.substring(0, Math.min(8, ticket.length())) + "...",
                    qrCodeUrl != null ? qrCodeUrl.length() : 0);

            return ticket;

        } catch (Exception e) {
            log.error("创建微信登录二维码失败", e);
            throw new Exception("创建微信登录二维码失败：" + e.getMessage(), e);
        }
    }

    /**
     * 检查微信登录状态
     * 参考study项目：checkLogin()方法
     * @param ticket 登录票据
     * @return 用户OpenID（如果已登录），否则返回null
     */
    public String checkLoginStatus(String ticket) {
        try {
            log.info("检查微信登录状态：ticket={}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null");

            if (ticket == null || ticket.trim().isEmpty()) {
                log.warn("登录票据为空");
                return null;
            }

            // 通过端口检查登录状态
            String openId = weixinLoginPort.getLoginStatus(ticket);

            if (openId != null && !openId.trim().isEmpty()) {
                log.info("微信登录状态：已登录，openId={}",
                        openId.substring(0, Math.min(8, openId.length())) + "...");
            } else {
                log.info("微信登录状态：未登录");
            }

            return openId;

        } catch (Exception e) {
            log.error("检查微信登录状态异常：ticket={}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null", e);
            return null;
        }
    }

    /**
     * 处理微信登录回调
     * 参考study项目：saveLoginState()方法
     * @param ticket 登录票据
     * @param openId 用户OpenID
     * @param unionId 用户UnionID（可选）
     * @param nickname 用户昵称（可选）
     * @param avatar 用户头像（可选）
     * @throws Exception 处理失败时抛出异常
     */
    public void handleLoginCallback(String ticket, String openId, String unionId, String nickname, String avatar) throws Exception {
        try {
            log.info("处理微信登录回调：ticket={}, openId={}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null");

            // 参数验证
            if (ticket == null || ticket.trim().isEmpty()) {
                throw new IllegalArgumentException("登录票据不能为空");
            }
            if (openId == null || openId.trim().isEmpty()) {
                throw new IllegalArgumentException("用户OpenID不能为空");
            }

            // 保存登录状态
            weixinLoginPort.saveLoginState(ticket, openId);

            // 查找或创建用户
            User user = userRepository.findByWeixinOpenId(openId);
            if (user == null) {
                // 创建新用户（这里需要根据实际业务逻辑调整）
                log.info("用户首次微信登录，openId={}", openId.substring(0, Math.min(8, openId.length())) + "...");
                // 暂不实现自动创建用户逻辑，等待后续完善
            }

            // 发送登录成功模板消息
            weixinLoginPort.sendLoginTemplate(openId, nickname);

            log.info("微信登录回调处理成功：ticket={}, openId={}",
                    ticket.substring(0, Math.min(8, ticket.length())) + "...",
                    openId.substring(0, Math.min(8, openId.length())) + "...");

        } catch (Exception e) {
            log.error("处理微信登录回调异常：ticket={}, openId={}",
                    ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null",
                    openId != null ? openId.substring(0, Math.min(8, openId.length())) + "..." : "null", e);
            throw new Exception("处理微信登录回调失败：" + e.getMessage(), e);
        }
    }

    /**
     * 生成唯一登录票据
     * @return 登录票据
     */
    private String generateTicket() {
        // 使用UUID + 时间戳生成唯一票据
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "wx_login_" + timestamp + "_" + uuid;
    }

    /**
     * 验证登录票据格式
     * @param ticket 登录票据
     * @return 是否有效
     */
    private boolean isValidTicket(String ticket) {
        return ticket != null
                && ticket.trim().isEmpty() == false
                && ticket.startsWith("wx_login_")
                && ticket.length() > 20;
    }
}