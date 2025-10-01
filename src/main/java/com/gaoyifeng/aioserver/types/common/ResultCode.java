package com.gaoyifeng.aioserver.types.common;

/**
 * @author gaoyifeng
 * @Classname ResultCode
 * @Description 结果封装状态码枚举类，包含常用HTTP状态码
 * @Date 2025/6/25 17:05
 * @Created by gaoyifeng
 */
public enum ResultCode {

    // 成功状态码
    _200("200", "请求成功"),
    
    // 客户端错误状态码
    _400("400", "错误的请求"),
    _401("401", "未授权，请先登录"),
    _403("403", "禁止访问，权限不足"),
    _404("404", "请求的资源不存在"),
    _405("405", "不支持的请求方法"),
    _408("408", "请求超时"),
    _429("429", "请求过于频繁，请稍后再试"),
    
    // 服务器错误状态码
    _500("500", "服务器内部错误"),
    _501("501", "服务未实现"),
    _502("502", "网关错误"),
    _503("503", "服务不可用"),
    _504("504", "网关超时"),
    
    // 业务相关状态码（可根据实际业务需求扩展）
    PARAM_ERROR("10001", "参数错误"),
    BUSINESS_ERROR("10002", "业务处理失败"),
    UNAUTHORIZED("10003", "未授权或授权已过期"),
    FORBIDDEN("10004", "禁止访问"),
    NOT_FOUND("10005", "资源不存在"),
    DUPLICATE_KEY("10006", "数据已存在"),
    DATA_INTEGRITY_ERROR("10007", "数据完整性错误"),
    SYSTEM_ERROR("10008", "系统内部错误");

    private final String code;
    private final String info;

    ResultCode(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    /**
     * 根据状态码获取对应的枚举
     *
     * @param code 状态码
     * @return 对应的ResultCode枚举，如果不存在则返回null
     */
    public static ResultCode getByCode(String code) {
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.getCode().equals(code)) {
                return resultCode;
            }
        }
        return null;
    }
}