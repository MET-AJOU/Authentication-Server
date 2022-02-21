package com.metajou.authentication.entity.oauth2;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
@Data
@RequiredArgsConstructor
@Table(value = "subaccount")
public class OAuth2Account{
    @Id
    protected Long id;
    @Column("username")
    protected String name;
    @Column("nickname")
    protected String nickName;
    @Column("email")
    protected String email;
    @Column("image")
    protected String image;
    @Column("accountid")
    protected Long accountId;

    public OAuth2Account(Map<String,Object> attributes) {
    }

    public OAuth2Account(String name, String nickName, String email, String image, Long accountId) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.image = image;
        this.accountId = accountId;
    }

    protected OAuth2Provider getOAuth2Provider() {
        return null;
    }

}
