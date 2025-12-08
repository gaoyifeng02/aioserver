package com.gaoyifeng.aioserver.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 智谱AI配置类
 * 用于从配置文件读取智谱AI相关配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zhipu.ai")
public class ZhiPuConfig {

    /**
     * 智谱AI API密钥
     */
    private String apiKey;

    /**
     * 使用的模型名称
     */
    private String model = "glm-4.6";

    /**
     * 最大token数
     */
    private Integer maxTokens = 2000;

    /**
     * 温度参数，控制回复的随机性
     */
    private Double temperature = 0.7;

    /**
     * 请求超时时间（毫秒）
     */
    private Integer timeout = 30000;
}