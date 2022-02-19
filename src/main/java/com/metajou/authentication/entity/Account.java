package com.metajou.authentication.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Table
@ToString
@RequiredArgsConstructor
public class Account {
    @Id
    private Long id;
    @Column("username")
    private String userName;
    @Column("usertoken")
    private String userToken;
    @Column("registerdate")
    private Instant registerDate;
    @Column("ajouemail")
    private String ajouEmail;
    private Role role;

    public Account(String userName, String userToken) {
        this.userName = userName;
        this.userToken = userToken;
        this.registerDate = Instant.now();
    }
}
