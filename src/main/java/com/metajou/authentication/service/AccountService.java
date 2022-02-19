package com.metajou.authentication.service;

import com.metajou.authentication.repository.AccountRepository;
import com.metajou.authentication.util.AccountUtil;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private AccountUtil accountUtil = AccountUtil.getInstance();
    private RedisConnectionFactory redisConnectionFactory;

    public AccountService(AccountRepository accountRepository, RedisConnectionFactory redisConnectionFactory) {
        this.accountRepository = accountRepository;
        this.redisConnectionFactory = redisConnectionFactory;
    }

}
