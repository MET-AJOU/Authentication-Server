package com.metajou.authserver.service;

import com.metajou.authserver.config.R2dbcConfig;
import com.metajou.authserver.entity.MainUser;
import com.metajou.authserver.entity.SubUser;
import com.metajou.authserver.entity.oauth.OAuth2Provider;
import com.metajou.authserver.entity.oauth.OAuth2UserInfo;
import com.metajou.authserver.property.CustomR2dbcProperties;
import com.metajou.authserver.repository.MainUserRepository;
import com.metajou.authserver.repository.SubUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:secret.properties") // Property 정보는 secret.properties에 저장되어있음.
@DataR2dbcTest(excludeAutoConfiguration = R2dbcAutoConfiguration.class) // Application Context에 property 2개가 잡히는 문제를 해결하기 위함.
@EnableR2dbcRepositories(basePackages = {"com.metajou.*"}) // R2dbc Repo를 Test에 사용하기 위해 해줌
@EnableConfigurationProperties(value = {CustomR2dbcProperties.class})
@ContextConfiguration(
        classes = {
                R2dbcConfig.class,
        }
)
public class UserServiceTests {

    @Autowired
    private MainUserRepository mainUserRepository;
    @Autowired
    private SubUserRepository subUserRepository;

    @Test
    public void getSubUserTest() {

    }

    @Test
    public void getMainUserTest() {

    }

    @Test
    public void registerUserTest() {
        createMainUserTest().doOnNext(mainUser -> createSubUserTest(mainUser.getId())).as(StepVerifier::create).verifyComplete();
    }

    private Mono<SubUser> createSubUserTest(Long mainId) {
        SubUser testSubUser = new SubUser("adfnadnofnoieqfnioqenofinq", OAuth2Provider.GOOGLE, "minshigee@gmail.com", mainId);
        return subUserRepository.save(testSubUser);
    }

    private Mono<MainUser> createMainUserTest() {
        MainUser testMainUser = new MainUser("minshigee");
        return mainUserRepository.save(testMainUser);
    }

}
