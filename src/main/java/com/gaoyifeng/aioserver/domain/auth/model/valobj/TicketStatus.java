package com.gaoyifeng.aioserver.domain.auth.model.valobj;

/**
 * 登录票据状态枚举
 * 管理微信登录票据的生命周期状态
 */
public enum TicketStatus {

    /**
     * 已创建（等待扫码）
     */
    CREATED("已创建", 0),

    /**
     * 已扫码（等待确认）
     */
    SCANNED("已扫码", 1),

    /**
     * 已确认（登录成功）
     */
    CONFIRMED("已确认", 2),

    /**
     * 已过期
     */
    EXPIRED("已过期", 3),

    /**
     * 已取消
     */
    CANCELLED("已取消", 4);

    private final String description;
    private final Integer code;

    /**
     * 构造函数
     * @param description 描述
     * @param code 编码
     */
    TicketStatus(String description, Integer code) {
        this.description = description;
        this.code = code;
    }

    /**
     * 获取描述
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 获取编码
     * @return 编码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 根据编码获取票据状态
     * @param code 编码
     * @return 票据状态
     */
    public static TicketStatus getByCode(Integer code) {
        if (code == null) {
            return CREATED; // 默认值
        }

        for (TicketStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }

        return CREATED; // 默认值
    }

    /**
     * 是否为终态状态
     * @return 是否为终态
     */
    public boolean isFinal() {
        return this == CONFIRMED || this == EXPIRED || this == CANCELLED;
    }

    /**
     * 是否为活跃状态（等待操作）
     * @return 是否为活跃状态
     */
    public boolean isActive() {
        return this == CREATED || this == SCANNED;
    }

    /**
     * 是否可以转换为目标状态
     * @param targetStatus 目标状态
     * @return 是否可以转换
     */
    public boolean canTransitionTo(TicketStatus targetStatus) {
        if (targetStatus == null) {
            return false;
        }

        // 已确认的状态不能转换
        if (this == CONFIRMED) {
            return false;
        }

        // 已过期的状态只能转换到取消
        if (this == EXPIRED) {
            return targetStatus == CANCELLED;
        }

        // 已取消的状态不能转换
        if (this == CANCELLED) {
            return false;
        }

        // 已创建可以转换到任何状态
        if (this == CREATED) {
            return true;
        }

        // 已扫码可以转换到确认、过期、取消
        return this == SCANNED;
    }

    @Override
    public String toString() {
        return String.format("TicketStatus{code=%d, description='%s', isFinal=%s}",
                code, description, isFinal());
    }
}