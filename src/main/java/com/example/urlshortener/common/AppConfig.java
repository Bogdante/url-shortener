package com.example.urlshortener.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom")
@Getter
@Setter
public class AppConfig {
    private String baseUrl;
}
