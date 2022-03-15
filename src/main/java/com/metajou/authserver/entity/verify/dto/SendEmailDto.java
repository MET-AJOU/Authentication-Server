package com.metajou.authserver.entity.verify.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

@ToString
@Getter
@Setter
public class SendEmailDto {
    private String to;
    private String subject;
    private String data;

    /**
     * @param to 수신자(여러명일 경우 ',' 구분)
     * @param subject 제목
     * @param data 내용
     */
    public SendEmailDto(String to, String subject, String data) {
        this.to = to;
        this.subject = subject;
        this.data = data;
    }

    public String[] getAllUserReceivedMail() {
        return Arrays.stream(to.split(","))
                .filter(user -> validateStringIsEmail(user))
                .toArray(String[]::new);
    }

    protected Boolean validateStringIsEmail(String user) {

        //TODO user가 이메일인지 식별하는 코드 짜기

        return true;
    }
}
