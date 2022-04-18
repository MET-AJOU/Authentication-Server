package com.metajou.authserver.entity.verify;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("VerifingTokenInfo")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class VerifingTokenInfo {
    @Id
    private Long id;
    @Column("user_code")
    private Long userCode;
    @Column("verify_email")
    private String verifyEmail;
    @Column("verify_token")
    private String verifyToken;

    public VerifingTokenInfo(Long userCode, String verifyEmail) {
        this.userCode = userCode;
        this.verifyEmail = verifyEmail;
    }

    public VerifingTokenInfo(Long userCode, String verifyEmail, String verifyToken) {
        this.userCode = userCode;
        this.verifyEmail = verifyEmail;
        this.verifyToken = verifyToken;
    }

}
