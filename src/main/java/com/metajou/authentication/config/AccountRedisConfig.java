package com.metajou.authentication.config;

import com.metajou.authentication.entity.Account;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class AccountRedisConfig {
    @Primary
    @Bean("accountRedisConnectionFactory")
    public ReactiveRedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory("localhost",6379);
    }

    @Bean
    public ReactiveRedisTemplate<String, Account> reactiveRedisTemplate(ReactiveRedisConnectionFactory accountRedisConnectionFactory) {
        return new ReactiveRedisTemplate<>(
                accountRedisConnectionFactory,
                RedisSerializationContext.fromSerializer(new Jackson2JsonRedisSerializer(Account.class))
        );
    }
}
