package com.metajou.authentication.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

import java.sql.Date;

@Data
@AllArgsConstructor
public class CreateAccountDto {
    @Column("username")
    private String userName;
    @Column("usertoken")
    private String userToken;
    @Column("registerdate")
    private Date registerDate;
}
