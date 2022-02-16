package com.metajou.authentication.config;

import com.metajou.authentication.property.AccountDbConnectionProperties;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.time.Duration;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
@EnableR2dbcRepositories
public class AccountR2Config extends AbstractR2dbcConfiguration {

    private AccountDbConnectionProperties connectionProperties;

    public AccountR2Config(AccountDbConnectionProperties properties) {
        this.connectionProperties = properties;
    }

    @Override
    @NonNull
    @Bean("authR2ConnectionFactory")
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, connectionProperties.getDriver())
                .option(HOST, connectionProperties.getHost())
                .option(USER, connectionProperties.getUser())
                .option(PORT, connectionProperties.getPort())
                .option(PASSWORD, connectionProperties.getPassword())
                .option(DATABASE, connectionProperties.getDatabase())
                .option(CONNECT_TIMEOUT, Duration.ofSeconds(3))
                .option(Option.valueOf("socketTimeout"), Duration.ofSeconds(4))
                .option(Option.valueOf("tcpKeepAlive"), true)
                .option(Option.valueOf("tcpNoDelay"), true)
                .build();
        return ConnectionFactories.get(options);
    }

}
