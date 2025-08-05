package com.example.urlshortener.common;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom")
@Getter
public class AppConfig {
    private String baseUrl;
}
