package com.metajou.authserver.config;

import com.metajou.authserver.property.CustomR2dbcProperties;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Duration;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
@EnableR2dbcRepositories
@EnableAutoConfiguration(exclude = R2dbcAutoConfiguration.class) //EnableAutoConfiguration을 이용하여 Bean 2개 생성되는 것을 방지함.
@EnableConfigurationProperties(value = {
        CustomR2dbcProperties.class
})
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final CustomR2dbcProperties customR2dbcProperties;

    public R2dbcConfig(CustomR2dbcProperties customR2dbcProperties) {
        this.customR2dbcProperties = customR2dbcProperties;
    }

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, customR2dbcProperties.getDriver())
                .option(HOST, customR2dbcProperties.getUrl())
                .option(USER, customR2dbcProperties.getUsername())
                .option(PORT, customR2dbcProperties.getPort())
                .option(PASSWORD, customR2dbcProperties.getPassword())
                .option(DATABASE, customR2dbcProperties.getName())
                .option(CONNECT_TIMEOUT, Duration.ofSeconds(3))
                .option(Option.valueOf("socketTimeout"), Duration.ofSeconds(4))
                .option(Option.valueOf("tcpKeepAlive"), true)
                .option(Option.valueOf("tcpNoDelay"), true)
                .build();
        return ConnectionFactories.get(options);
    }

    @Bean
    @ExceptionHandler(Exception.class)
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new CustomConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(
                new ResourceDatabasePopulator(
                        new ClassPathResource("sql/create_account_db.sql")
                )
        );
        return initializer;
    }

    class CustomConnectionFactoryInitializer extends ConnectionFactoryInitializer {
        @Override
        public void afterPropertiesSet() {
            try {
                super.afterPropertiesSet();
            }
            catch (Exception e) {
            }
        }
    }
}
