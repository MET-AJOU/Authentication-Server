package com.metajou.authentication.service;

import com.metajou.authentication.entity.Account;
import com.metajou.authentication.repository.AccountRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.PostConstruct;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private RedisConnectionFactory redisConnectionFactory;
    public AccountService(AccountRepository accountRepository, RedisConnectionFactory redisConnectionFactory) {
        this.accountRepository = accountRepository;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @PostConstruct
    public void test(){
        accountRepository.findAll().subscribe(account -> System.err.println(account));
//        R2dbcEntityTemplate template = new R2dbcEntityTemplate(factory);
//        template.select(Account.class).first().subscribe(account -> System.err.println(account));
    }
}
