package com.metajou.authserver.util;

import com.metajou.authserver.entity.verify.dto.SendEmailDto;
import com.metajou.authserver.property.EmailSenderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.mail.internet.MimeMessage;

@Component
public class EmailUtils {

    private final EmailSenderProperties emailSenderProperties;
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailUtils(EmailSenderProperties emailSenderProperties, JavaMailSender javaMailSender) {
        this.emailSenderProperties = emailSenderProperties;
        this.javaMailSender = javaMailSender;
    }

    public Mono<Boolean> send(SendEmailDto dto) {
        return Mono.fromCallable(() -> sendByBlocking(dto));
    }

    private boolean sendByBlocking(SendEmailDto dto) {
        if (!StringUtils.hasText(dto.getTo())) return false; // 수신자가 없을 경우 종료

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, false, "UTF-8");

            messageHelper.setSubject(dto.getSubject());
            messageHelper.setText(dto.getData(), true);
            messageHelper.setFrom(emailSenderProperties.getUserName());
            messageHelper.setTo(dto.getAllUserReceivedMail());
            javaMailSender.send(message); // 메일발송

            return true;
        }catch(MailException es){
            es.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}