package com.metajou.authserver.entity.auth.oauth2;

import com.metajou.authserver.entity.auth.AuthInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Getter
@Setter
@ToString
public class GoogleAuthInfo extends DefaultOidcUser implements CustomAuthInfo{
    protected String id;
    protected String email;
    protected OAuth2Provider provider;

    public GoogleAuthInfo(OidcUser user) {
        super(user.getAuthorities(), user.getIdToken(), user.getUserInfo());
    }

    public AuthInfo extractAuthInfo() {
        return new AuthInfo(getId(), getProvider(), getEmail());
    }

}
