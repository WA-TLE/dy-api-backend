package com.dy;

import com.dy.client.DyApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: dy
 * @Date: 2023/12/23 16:12
 * @Description:
 */
@Configuration
@ConfigurationProperties("dyapi.client")
@Data
@ComponentScan
public class DyApiClientConfig {
    private String accessKey;
    private String secretKey;


    @Bean
    public DyApiClient dyApiClient() {
        return new DyApiClient(accessKey, secretKey);
    }

}
