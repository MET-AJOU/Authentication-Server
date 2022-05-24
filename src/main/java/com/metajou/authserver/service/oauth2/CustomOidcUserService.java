package com.metajou.authserver.service.oauth2;

import com.metajou.authserver.entity.auth.oauth2.GoogleAuthInfo;
import com.metajou.authserver.entity.auth.oauth2.OAuth2Provider;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Service
public class CustomOidcUserService extends OidcReactiveOAuth2UserService {

    @Override
    public Mono<OidcUser> loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        Mono<OidcUser> oidcUserMono = super.loadUser(userRequest);
        return oidcUserMono.map(oidcUser -> makeOAuth2UserInfo(oidcUser, userRequest));
    }

    public OidcUser makeOAuth2UserInfo(OidcUser user, OAuth2UserRequest userRequest) {
        String providerName = userRequest.getClientRegistration().getRegistrationId().toUpperCase(Locale.ROOT);
        System.err.println(providerName);
        if(providerName.equalsIgnoreCase(OAuth2Provider.GOOGLE.toString())) {
            System.err.println(1);
            return convertToGoogleUser(user);
        }
        return null;
    }

    private GoogleAuthInfo convertToGoogleUser(OidcUser user) {
        GoogleAuthInfo googleAuthInfo = new GoogleAuthInfo(user);
        googleAuthInfo.setId(String.valueOf(String.valueOf(googleAuthInfo.getClaims().get("sub"))));;
        googleAuthInfo.setEmail(String.valueOf(googleAuthInfo.getClaims().get("email")));
        googleAuthInfo.setProvider(OAuth2Provider.GOOGLE);
        return googleAuthInfo;
    }
}
