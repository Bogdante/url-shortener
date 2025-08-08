package com.example.urlshortener.url.services;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisCacheService {
    private final StringRedisTemplate stringRedisTemplate;

    public RedisCacheService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void set(String shortUrlPathVariable, String fullUrl) {
        stringRedisTemplate.opsForValue().set(shortUrlPathVariable, fullUrl);
    }

    public Optional<String> get(String shortUrlPathVariable) {
        String value =  stringRedisTemplate.opsForValue().get(shortUrlPathVariable);
        return Optional.ofNullable(value);
    }

    public void clear(String shortUrlPathVariable) {
        stringRedisTemplate.delete(shortUrlPathVariable);
    }
}
