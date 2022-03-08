package com.metajou.authserver.service;

import com.metajou.authserver.entity.oauth.OAuth2UserInfo;
import com.metajou.authserver.repository.MainUserRepository;
import com.metajou.authserver.repository.SubUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomOidcUserService extends OidcReactiveOAuth2UserService {

    private final MainUserRepository mainUserRepository;
    private final SubUserRepository subUserRepository;

    @Autowired
    public CustomOidcUserService(MainUserRepository mainUserRepository, SubUserRepository subUserRepository) {
        this.mainUserRepository = mainUserRepository;
        this.subUserRepository = subUserRepository;
    }

    @Override
    public Mono<OidcUser> loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        Mono<OidcUser> oidcUserMono = super.loadUser(userRequest);
        return oidcUserMono.map(oidcUser -> {
            OAuth2UserInfo userInfo = new OAuth2UserInfo((DefaultOidcUser) oidcUser, userRequest.getClientRegistration().getRegistrationId());
            return userInfo;
        });
    }

}
