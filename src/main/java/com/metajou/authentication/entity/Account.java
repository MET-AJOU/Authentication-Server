package com.metajou.authentication.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Data
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
    private Date registerDate;
    @Column("ajouemail")
    private String ajouEmail;
    private Role role;
}
