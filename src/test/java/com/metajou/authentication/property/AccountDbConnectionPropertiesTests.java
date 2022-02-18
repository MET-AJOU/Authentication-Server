package com.metajou.authentication.property;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:secret.properties")
@EnableConfigurationProperties(value = {AccountDbConnectionProperties.class})
public class AccountDbConnectionPropertiesTests {
    @Autowired
    AccountDbConnectionProperties connectionProperties;

    @Value("${spring.datasource.account.driver}")
    private String driver;
    @Value("${spring.datasource.account.database}")
    private String database;
    @Value("${spring.datasource.account.host}")
    private String host;
    @Value("${spring.datasource.account.port}")
    private Integer port;
    @Value("${spring.datasource.account.user}")
    private String user;
    @Value("${spring.datasource.account.password}")
    private String password;

    @Test
    public void testAccountDbConnectionPropertiesAvaliable() {
        Assertions.assertNotNull(connectionProperties);
        Assertions.assertEquals(connectionProperties.getDriver(), driver);
        Assertions.assertEquals(connectionProperties.getDatabase(), database);
        Assertions.assertEquals(connectionProperties.getHost(), host);
        Assertions.assertEquals(connectionProperties.getPort(), port);
        Assertions.assertEquals(connectionProperties.getUser(), user);
        Assertions.assertEquals(connectionProperties.getPassword(), password);
    }
}
