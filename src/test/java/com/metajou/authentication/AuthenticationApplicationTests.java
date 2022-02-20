package com.metajou.authentication;

import com.metajou.authentication.entity.Account;
import com.metajou.authentication.entity.Role;
import com.metajou.authentication.repository.AccountRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest(
        properties =
        "spring.config.location=" +
        "classpath:/application.properties" +
        ",classpath:/secret.properties"
)
class AuthenticationApplicationTests {
    @Autowired
    ConnectionFactory factory;
    @Autowired
    AccountRepository accountRepository;

    @Test
    void contextLoads() {
        Account ac = new Account();
    }

}
