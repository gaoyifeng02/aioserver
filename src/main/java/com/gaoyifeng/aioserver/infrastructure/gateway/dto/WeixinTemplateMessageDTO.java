package com.gaoyifeng.aioserver.infrastructure.gateway.dto;

import lombok.Data;

import java.util.Map;

/**
 * 微信模板消息DTO对象
 * 参考study项目实现
 */
@Data
public class WeixinTemplateMessageDTO {

    /**
     * 接收者openid
     */
    private String touser;

    /**
     * 模板ID
     */
    private String template_id;

    /**
     * 模板跳转链接（可选）
     */
    private String url;

    /**
     * 模板数据
     */
    private Map<String, Map<String, String>> data;

    public WeixinTemplateMessageDTO(String touser, String template_id) {
        this.touser = touser;
        this.template_id = template_id;
    }

    /**
     * 添加模板数据
     * @param data 数据集合
     * @param key 键
     * @param value 值
     */
    public static void put(Map<String, Map<String, String>> data, String key, String value) {
        data.put(key, Map.of("value", value));
    }

    public static class TemplateKey {
        public static final String USER = "user";
    }
}