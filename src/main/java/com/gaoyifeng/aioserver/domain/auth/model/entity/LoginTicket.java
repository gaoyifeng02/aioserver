package com.gaoyifeng.aioserver.domain.auth.model.entity;

import com.gaoyifeng.aioserver.domain.auth.model.valobj.TicketStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录票据实体 - DDD充血模型
 * 管理微信登录票据的生命周期
 */
@Data
public class LoginTicket {

    /**
     * 票据ID
     */
    private String id;

    /**
     * 登录票据（对外提供的ticket）
     */
    private String ticket;

    /**
     * 票据状态
     */
    private TicketStatus status = TicketStatus.CREATED;

    /**
     * 用户OpenID（登录成功后填充）
     */
    private String openId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 扫码时间
     */
    private LocalDateTime scanTime;

    /**
     * 确认时间
     */
    private LocalDateTime confirmTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 二维码URL
     */
    private String qrCodeUrl;

    /**
     * 默认构造函数
     */
    public LoginTicket() {
        this.createTime = LocalDateTime.now();
        this.expireTime = this.createTime.plusMinutes(5); // 默认5分钟过期
    }

    /**
     * 构造函数
     * @param ticket 登录票据
     * @param qrCodeUrl 二维码URL
     */
    public LoginTicket(String ticket, String qrCodeUrl) {
        this();
        this.id = generateId();
        this.ticket = ticket;
        this.qrCodeUrl = qrCodeUrl;
    }

    /**
     * 创建登录票据的工厂方法
     * @param ticket 登录票据
     * @param qrCodeUrl 二维码URL
     * @return 登录票据实体
     */
    public static LoginTicket create(String ticket, String qrCodeUrl) {
        if (ticket == null || ticket.trim().isEmpty()) {
            throw new IllegalArgumentException("登录票据不能为空");
        }
        return new LoginTicket(ticket, qrCodeUrl);
    }

    /**
     * 标记为已扫码
     * @return 操作是否成功
     */
    public boolean markAsScanned() {
        if (!canTransitionTo(TicketStatus.SCANNED)) {
            return false;
        }

        this.status = TicketStatus.SCANNED;
        this.scanTime = LocalDateTime.now();
        return true;
    }

    /**
     * 标记为已确认（登录成功）
     * @param openId 用户OpenID
     * @return 操作是否成功
     */
    public boolean markAsConfirmed(String openId) {
        if (!canTransitionTo(TicketStatus.CONFIRMED)) {
            return false;
        }

        if (openId == null || openId.trim().isEmpty()) {
            return false;
        }

        this.status = TicketStatus.CONFIRMED;
        this.openId = openId;
        this.confirmTime = LocalDateTime.now();
        return true;
    }

    /**
     * 标记为已过期
     * @return 操作是否成功
     */
    public boolean markAsExpired() {
        if (!canTransitionTo(TicketStatus.EXPIRED)) {
            return false;
        }

        this.status = TicketStatus.EXPIRED;
        return true;
    }

    /**
     * 标记为已取消
     * @return 操作是否成功
     */
    public boolean markAsCancelled() {
        if (!canTransitionTo(TicketStatus.CANCELLED)) {
            return false;
        }

        this.status = TicketStatus.CANCELLED;
        return true;
    }

    /**
     * 检查是否已过期
     * @return 是否已过期
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime) || status == TicketStatus.EXPIRED;
    }

    /**
     * 检查是否活跃（等待操作）
     * @return 是否活跃
     */
    public boolean isActive() {
        return !isExpired() && status.isActive();
    }

    /**
     * 检查是否已确认登录
     * @return 是否已确认
     */
    public boolean isConfirmed() {
        return status == TicketStatus.CONFIRMED;
    }

    /**
     * 检查是否可以转换到目标状态
     * @param targetStatus 目标状态
     * @return 是否可以转换
     */
    public boolean canTransitionTo(TicketStatus targetStatus) {
        return status.canTransitionTo(targetStatus);
    }

    /**
     * 获取剩余有效时间（秒）
     * @return 剩余时间，已过期返回0
     */
    public long getRemainingSeconds() {
        if (isExpired()) {
            return 0;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(expireTime)) {
            return java.time.Duration.between(now, expireTime).getSeconds();
        }

        return 0;
    }

    /**
     * 获取摘要信息
     * @return 摘要字符串
     */
    public String getSummary() {
        return String.format("LoginTicket{id='%s', ticket='%s', status='%s', hasOpenId=%s, remainingSeconds=%d}",
                id != null ? id.substring(0, Math.min(8, id.length())) + "..." : "null",
                ticket != null ? ticket.substring(0, Math.min(8, ticket.length())) + "..." : "null",
                status.getDescription(),
                openId != null && !openId.isEmpty(),
                getRemainingSeconds());
    }

    /**
     * 生成实体ID
     * @return 实体ID
     */
    private String generateId() {
        return "ticket_" + System.currentTimeMillis() + "_" + Thread.currentThread().getId();
    }

    /**
     * 验证票据数据是否有效
     * @return 是否有效
     */
    public boolean isValid() {
        return id != null && !id.trim().isEmpty()
                && ticket != null && !ticket.trim().isEmpty()
                && createTime != null
                && expireTime != null
                && status != null;
    }
}