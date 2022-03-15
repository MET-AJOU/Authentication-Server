package com.metajou.authserver.config;

import com.metajou.authserver.property.EmailSenderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties(value = {
        EmailSenderProperties.class
})
public class EmailSenderConfig {

    private final EmailSenderProperties emailSenderProperties;
    private final Properties properties = new Properties();

    @Autowired
    public EmailSenderConfig(EmailSenderProperties emailSenderProperties) {
        this.emailSenderProperties = emailSenderProperties;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(this.emailSenderProperties.getHost());
        javaMailSender.setUsername(this.emailSenderProperties.getUserName());
        javaMailSender.setPassword(this.emailSenderProperties.getPassword());
        javaMailSender.setPort(this.emailSenderProperties.getPort());

        properties.put("mail.smtp.socketFactory.port", this.emailSenderProperties.getPort());
        properties.put("mail.smtp.auth", this.emailSenderProperties.getSmtpAuth());
        properties.put("mail.smtp.starttls.enable", this.emailSenderProperties.getTlsEnable());
        properties.put("mail.smtp.starttls.required", true);
        properties.put("mail.smtp.socketFactory.fallback", false);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setDefaultEncoding("UTF-8");

        return javaMailSender;
    }
}
