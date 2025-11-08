package com.gaoyifeng.aioserver.domain.auth.model.valobj;

/**
 * 登录类型枚举
 * 支持用户名密码登录和微信登录
 */
public enum LoginType {

    /**
     * 用户名密码登录
     */
    USERNAME_PASSWORD("用户名密码登录", 1),

    /**
     * 微信登录
     */
    WEIXIN("微信登录", 2);

    private final String description;
    private final Integer code;

    /**
     * 构造函数
     * @param description 描述
     * @param code 编码
     */
    LoginType(String description, Integer code) {
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
     * 根据编码获取登录类型
     * @param code 编码
     * @return 登录类型
     */
    public static LoginType getByCode(Integer code) {
        if (code == null) {
            return USERNAME_PASSWORD; // 默认值
        }

        for (LoginType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }

        return USERNAME_PASSWORD; // 默认值
    }

    /**
     * 根据描述获取登录类型
     * @param description 描述
     * @return 登录类型
     */
    public static LoginType getByDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return USERNAME_PASSWORD; // 默认值
        }

        for (LoginType type : values()) {
            if (type.getDescription().equals(description.trim())) {
                return type;
            }
        }

        return USERNAME_PASSWORD; // 默认值
    }

    /**
     * 是否为微信登录
     * @return 是否为微信登录
     */
    public boolean isWeixin() {
        return this == WEIXIN;
    }

    /**
     * 是否为用户名密码登录
     * @return 是否为用户名密码登录
     */
    public boolean isUsernamePassword() {
        return this == USERNAME_PASSWORD;
    }

    @Override
    public String toString() {
        return String.format("LoginType{code=%d, description='%s'}", code, description);
    }
}