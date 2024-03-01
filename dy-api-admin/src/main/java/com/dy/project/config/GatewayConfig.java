package com.dy.project.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: dy
 * @Date: 2024/3/1 21:37
 * @Description:
 */
@Configuration
@ConfigurationProperties(prefix = "dy.gateway")
@Data
public class GatewayConfig {
    private String host;
}
