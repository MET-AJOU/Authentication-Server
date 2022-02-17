package com.metajou.authentication.service;

import com.metajou.authentication.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private RedisConnectionFactory redisConnectionFactory;
    public AccountService(AccountRepository accountRepository, RedisConnectionFactory redisConnectionFactory) {
        this.accountRepository = accountRepository;
        this.redisConnectionFactory = redisConnectionFactory;
    }
}
