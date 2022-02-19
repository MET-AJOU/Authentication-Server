package com.metajou.authentication.service;

import com.metajou.authentication.entity.Account;
import com.metajou.authentication.entity.dto.CreateAccountDto;
import com.metajou.authentication.repository.AccountRepository;
import com.metajou.authentication.util.AccountUtil;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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
