package com.metajou.authentication;

import com.metajou.authentication.entity.Account;
import com.metajou.authentication.entity.Role;
import com.metajou.authentication.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest(
        properties =
        "spring.config.location=" +
        "classpath:/application.properties" +
        ",classpath:/secret.properties"
)
class AuthenticationApplicationTests {

    @Test
    void contextLoads() {
    }

}
