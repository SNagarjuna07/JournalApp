package com.edigest.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // For setting serialization and deserialization to access from both SpringBoot -> Redis and Redis -> SpringBoot 
    @Bean
    public RedisTemplate<String, String> redisTemplateConfig(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(factory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;

    }
}
