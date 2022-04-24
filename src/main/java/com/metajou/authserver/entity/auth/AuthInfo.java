package com.metajou.authserver.entity.auth;

import com.metajou.authserver.entity.auth.oauth2.OAuth2Provider;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.*;
import java.util.stream.Collectors;

@Data
@ToString
@Table("AuthInfo")
@Getter
@Setter
@NoArgsConstructor
public class AuthInfo {
    @Id
    private Long id;
    @Column("user_id")
    private String userId;
    @Column("provider")
    private OAuth2Provider provider;
    @Column("user_email")
    private String userEmail;
    @Column("authorities")
    private String authorities = "";

    public AuthInfo(String userId, OAuth2Provider provider, String userEmail) {
        this.userId = userId;
        this.provider = provider;
        this.userEmail = userEmail;
        this.authorities = Role.ROLE_GUEST.toString();
    }

    public AuthInfo(String userId, OAuth2Provider provider, String userEmail, List<Role> roles) {
        this.userId = userId;
        this.provider = provider;
        this.userEmail = userEmail;
        roles.stream().forEach(role -> addAuthorities(role));
    }

    public List<Role> extractAuthorities() {
        String[] roleStr = this.authorities.split(",");
        return Arrays.stream(roleStr).map(Role::valueOf)
                .filter(role -> role!=null).collect(Collectors.toSet())
                .stream().collect(Collectors.toList());
    }

    public Boolean containAuthority(Role role) {
        return extractAuthorities().contains(role);
    }

    public void addAuthorities(@NonNull Role role) {
        if (extractAuthorities().contains(role))
            return;
        this.authorities += ("," + role.toString());
    }

}
