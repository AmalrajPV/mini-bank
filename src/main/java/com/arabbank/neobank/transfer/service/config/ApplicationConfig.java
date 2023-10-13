package com.arabbank.neobank.transfer.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("config.app.info")
@Data
public class ApplicationConfig {
    private String accountUrl;
    private String customerUrl;
    private String offerUrl;
    private String name;
    private String version;
}
