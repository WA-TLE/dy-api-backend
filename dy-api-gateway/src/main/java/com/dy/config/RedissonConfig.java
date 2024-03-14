package com.dy.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 配置
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
@Slf4j
@Data
public class RedissonConfig {

    private String host;

    private String port;

    private String password; // 添加密码属性

    @Bean
    public RedissonClient redissonClient() {
        // 1. 创建配置
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%s", host, port);
        log.info("redis 连接地址: {}", redisAddress);
//        config.useSingleServer().setAddress(redisAddress).setDatabase(3);
        config.useSingleServer().setAddress(redisAddress).setDatabase(0).setPassword(password); // 设置密码
        // 2. 创建实例
        return Redisson.create(config);
    }
}
