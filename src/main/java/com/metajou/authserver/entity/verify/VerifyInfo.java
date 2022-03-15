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
    private String userCode;
    @Column("verify_email")
    private String verifyEmail;
    @Column("is_verified")
    private Boolean isVerified;
    @Column("verify_time")
    private LocalDateTime verifyTime;
}
