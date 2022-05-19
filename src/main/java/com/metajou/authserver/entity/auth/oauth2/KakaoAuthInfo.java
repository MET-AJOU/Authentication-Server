package com.metajou.authserver.entity.auth.oauth2;

import com.metajou.authserver.entity.auth.AuthInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class KakaoAuthInfo extends DefaultOAuth2User implements CustomAuthInfo{

    protected String id;
    protected String email;
    protected OAuth2Provider provider;

    public KakaoAuthInfo(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey) {
        super(authorities, attributes, nameAttributeKey);
    }
    public KakaoAuthInfo(OAuth2User user) {
        super(user.getAuthorities(), user.getAttributes(), "id");
    }

    public AuthInfo extractAuthInfo() {
        return new AuthInfo(getId(), getProvider(), getEmail());
    }
}
