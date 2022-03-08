package com.metajou.authserver.entity;

import com.metajou.authserver.entity.oauth.OAuth2Provider;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Table("SubUser")
@Getter
@Setter
@NoArgsConstructor
public class SubUser {
    @Id
    Long id;
    @Column("UserId")
    String userId;
    @Column("UserEmail")
    String userEmail;
    @Column("Provider")
    OAuth2Provider provider;
    @Column("MainId")
    Long mainId;

    public SubUser(String userId, OAuth2Provider provider, String userEmail, Long mainId) {
        this.userId = userId;
        this.provider = provider;
        this.userEmail = userEmail;
        this.mainId = mainId;
    }
}
