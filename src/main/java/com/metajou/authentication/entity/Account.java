package com.metajou.authentication.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Data
@Table
@ToString
@RequiredArgsConstructor
public class Account {
    @Id
    private String id;
    @NonNull
    private String userName;
    private String userToken;
    @NonNull
    private Date registerDate;
    @NonNull
    private String ajouEmail;
    @NonNull
    private Role role;
}
