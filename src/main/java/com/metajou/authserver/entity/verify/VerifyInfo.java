package com.metajou.authserver.entity.verify;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@ToString
@Table("VerifyInfo")
@Getter
@Setter
@NoArgsConstructor
public class VerifyInfo {
    @Id
    private Long id;
    @Column("user_code")
    private Long userCode;
    @Column("verify_email")
    private String verifyEmail;
    @Column("verify_time")
    private LocalDateTime verifyTime;
    @Column("useable")
    private Boolean useable = false;

    public VerifyInfo(Long userCode, String verifyEmail) {
        this.userCode = userCode;
        this.verifyEmail = verifyEmail;
        this.verifyTime = LocalDateTime.now();
    }

    public static final VerifyInfo empty = new VerifyInfo(null, null);
}
