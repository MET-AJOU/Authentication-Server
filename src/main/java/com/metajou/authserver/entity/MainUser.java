package com.metajou.authserver.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.time.LocalDate;

@Data
@ToString
@Table("MainUser")
@Getter
@Setter
@NoArgsConstructor
public class MainUser {
    @Id
    Long id;
    @Column("UserName")
    String userName;
    @Column("UserEmail")
    String ajouEmail;
    @Column("RegisterDate")
    LocalDate registerDate;
    @Column("Picture")
    String picture;

    public MainUser(String userName) {
        this.userName = userName;
        this.registerDate = LocalDate.now();
    }

    public MainUser(String userName, String picture) {
        this.userName = userName;
        this.registerDate = LocalDate.now();
        this.picture = picture;
    }

    public MainUser(String userName, String ajouEmail, String picture) {
        this.userName = userName;
        this.ajouEmail = ajouEmail;
        this.registerDate = LocalDate.now();
        this.picture = picture;
    }
}
