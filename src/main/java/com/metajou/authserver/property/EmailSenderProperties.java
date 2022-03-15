package com.metajou.authserver.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;

@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mail")
@PropertySource(value = "secret.properties")
@ConstructorBinding
public class EmailSenderProperties {
    private String host;
    private Integer port;
    private String userName;
    private String password;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private Boolean smtpAuth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private Boolean tlsEnable;
}