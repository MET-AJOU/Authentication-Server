package com.metajou.authserver.entity.oauth;

import com.metajou.authserver.entity.SubUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

@Getter
@Setter
@ToString
public class OAuth2UserInfo extends DefaultOidcUser {

    protected String id;
    protected String email;
    protected OAuth2Provider provider;

    public OAuth2UserInfo(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken) {
        super(authorities, idToken);
    }

    public OAuth2UserInfo(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken, String nameAttributeKey) {
        super(authorities, idToken, nameAttributeKey);
    }

    public OAuth2UserInfo(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken, OidcUserInfo userInfo) {
        super(authorities, idToken, userInfo);
    }

    public OAuth2UserInfo(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken, OidcUserInfo userInfo, String nameAttributeKey) {
        super(authorities, idToken, userInfo, nameAttributeKey);
    }

    public OAuth2UserInfo(DefaultOidcUser user, String providerName) {
        super(user.getAuthorities(), user.getIdToken(), user.getUserInfo());
        providerName = providerName.toUpperCase(Locale.ROOT);

        if(providerName.equalsIgnoreCase(OAuth2Provider.GOOGLE.toString())) {
            convertToGoogleUser();
        }
        else if(providerName.equalsIgnoreCase(OAuth2Provider.KAKAO.toString())) {
            convertToKakaoUser();
        }
    }

    public OAuth2UserInfo(DefaultOAuth2User user, String providerName) {
        super(user.getAuthorities(), null);
        providerName = providerName.toUpperCase(Locale.ROOT);
        if(providerName.equalsIgnoreCase(OAuth2Provider.GOOGLE.toString())) {
            convertToGoogleUser();
        }
        else if(providerName.equalsIgnoreCase(OAuth2Provider.KAKAO.toString())) {
            convertToKakaoUser();
        }
    }

    private void convertToGoogleUser() {
        this.id = String.valueOf(String.valueOf(this.getClaims().get("sub")));
        this.email = String.valueOf(this.getClaims().get("email"));
        this.provider = OAuth2Provider.GOOGLE;
    }

    private void convertToKakaoUser() {
        Map<String, Object> properties = (Map<String, Object>) this.getClaims().get("properties");
        this.id = String.valueOf(this.getClaims().get("id"));
        this.email = String.valueOf(properties.get("email"));
    }

    public SubUser extractSubUser(Long mainId) {
        return new SubUser(this.getId(), this.getProvider(), this.getEmail(), mainId);
    }

}
