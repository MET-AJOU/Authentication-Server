package com.metajou.authentication.service;

import com.metajou.authentication.config.AccountR2Config;
import com.metajou.authentication.entity.Account;
import com.metajou.authentication.property.AccountDbConnectionProperties;
import com.metajou.authentication.repository.AccountRepository;
import com.metajou.authentication.util.AccountUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:secret.properties")
@DataR2dbcTest
@EnableR2dbcRepositories(basePackages = {"com.metajou.*"})
@EnableConfigurationProperties(value = {AccountDbConnectionProperties.class})
@ContextConfiguration(
        classes = {
                AccountR2Config.class,
        }
)
public class AccountServiceTests {

    @Autowired
    private AccountRepository accountRepository;
    private AccountUtil accountUtil = AccountUtil.getInstance();

    @Test
    public void test() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        accountRepository.save(accountUtil.generateCreateAccountDto("smallesthuman@gmail.com")).subscribe(account -> System.err.println(account));
        while (true);
    }

}
