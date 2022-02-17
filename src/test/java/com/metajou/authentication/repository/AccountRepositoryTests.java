package com.metajou.authentication.repository;

import com.metajou.authentication.config.AccountR2Config;
import com.metajou.authentication.entity.Account;
import com.metajou.authentication.entity.Role;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;

@DataR2dbcTest()
@ExtendWith({SpringExtension.class})
@ContextConfiguration
public class AccountRepositoryTests {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void createAccount(){
        Account account = new Account("minshigee",
                Date.valueOf(LocalDate.now()),
                "alstjr1642@ajou.ac.kr",
                Role.Admin);
        System.out.println(account);
        System.out.println(accountRepository);
        //repository.save(account);
    }

}
