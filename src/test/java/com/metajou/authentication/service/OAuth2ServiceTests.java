package com.metajou.authentication.service;

import com.metajou.authentication.config.AccountR2Config;
import com.metajou.authentication.entity.oauth2.OAuth2Account;
import com.metajou.authentication.property.AccountDbConnectionProperties;
import com.metajou.authentication.repository.OAuth2AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

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
public class OAuth2ServiceTests {
    @Autowired
    OAuth2AccountRepository oAuth2AccountRepository;

    @Test
    public void testOAuth2RepositoryCreate(){
        OAuth2Account account = new OAuth2Account("dsanwj","minzik2","minzik2@gmail.com","blabla",1L);
        oAuth2AccountRepository.save(account).as(StepVerifier::create)
                .assertNext(oAuth2Account -> {
                    Assertions.assertNotNull(oAuth2Account.getNickName());
                    System.err.println(oAuth2Account);
                }).verifyComplete();
    }

    @Test
    public void testOAuth2RepositoryGet(){
        oAuth2AccountRepository.findOAuth2AccountByName("106016498754833795297").as(StepVerifier::create)
                .assertNext(oAuth2Account -> {
                    Assertions.assertNotNull(oAuth2Account.getNickName());
                    System.err.println(oAuth2Account);
                }).verifyComplete();
    }

}
