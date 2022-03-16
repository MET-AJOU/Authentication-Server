package com.metajou.authserver.entity.auth;

import com.metajou.authserver.entity.oauth2.OAuth2Provider;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.security.MessageDigest;
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
    @Column("user_code")
    private String userCode;
    @Column("authorities")
    private String authorities = "";

    public AuthInfo(String userId, OAuth2Provider provider, String userEmail) {
        this.userId = userId;
        this.provider = provider;
        this.userEmail = userEmail;
        this.userCode = generateAuthInfoUserCode(userId, provider);
        this.authorities = Role.ROLE_GUEST.toString();
    }

    public AuthInfo(String userId, OAuth2Provider provider, String userEmail, List<Role> roles) {
        this.userId = userId;
        this.provider = provider;
        this.userEmail = userEmail;
        this.userCode = generateAuthInfoUserCode(userId, provider);
        roles.stream().forEach(role -> addAuthorities(role));
    }

    public List<Role> extractAuthorities() {
        String[] roleStr = this.authorities.split(",");
        return Arrays.stream(roleStr).map(Role::valueOf)
                .filter(role -> role!=null).collect(Collectors.toSet())
                .stream().collect(Collectors.toList());
    }

    public void addAuthorities(@NonNull Role role) {
        this.authorities += ("," + role.toString());
    }

    private static String generateAuthInfoUserCode(String userId, OAuth2Provider provider) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            StringBuilder builder = new StringBuilder();
            byte[] bytes = (userId + provider.toString().toUpperCase(Locale.ROOT)).getBytes();
            md.update(bytes);
            for (byte b : md.digest()) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
