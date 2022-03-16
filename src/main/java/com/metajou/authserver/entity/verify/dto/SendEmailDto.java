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
     * @param ajouReq 수신자(1인 고정),(자동으로 @ajou.ac.kr을 추가)
     * @param subject 제목
     * @param data 내용
     */
    public SendEmailDto(AjouEmailVerifyRequest ajouReq, String subject, String data) {
        this.to = ajouReq.getAjouUserId() + "@ajou.ac.kr";
        this.subject = subject;
        this.data = data;
    }

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
