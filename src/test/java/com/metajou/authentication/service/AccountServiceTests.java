package com.metajou.authentication.service;

import com.metajou.authentication.config.AccountR2Config;
import com.metajou.authentication.entity.Account;
import com.metajou.authentication.property.AccountDbConnectionProperties;
import com.metajou.authentication.repository.AccountRepository;
import com.metajou.authentication.util.AccountUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

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
    public void testAccountRepositoryCreate() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] rStr = new byte[10];
        new Random().nextBytes(rStr);
        String generatedStr = new String(Base64.getEncoder().encode(rStr));

        String email = generatedStr + "@gmail.com";
        Account ori = accountUtil.generateCreateAccountDto(email);

        accountRepository.save(ori)
                .as(StepVerifier::create)
                .assertNext(account -> {
                    Assertions.assertEquals(accountUtil.generateUsername(email), account.getUserName());
                    Assertions.assertEquals(ori.getRegisterDate(), account.getRegisterDate());
                    Assertions.assertEquals(ori.getUserToken(), account.getUserToken());
                    System.err.println(account);
                }).verifyComplete();
    }

    @Test
    public void testAccountRepositoryGet(){
        accountRepository.findAll().take(1).as(StepVerifier::create)
                .assertNext(account -> {
                     Assertions.assertNotNull(account.getId());
                     System.err.println(account);
                }).verifyComplete();
    }

}
