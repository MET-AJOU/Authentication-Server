package com.metajou.authserver.service;

import com.metajou.authserver.entity.oauth2.OAuth2UserInfo;
import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomOAuth2UserService extends DefaultReactiveOAuth2UserService {

    @Override
    public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Mono<OAuth2User> oAuth2UserMono = super.loadUser(userRequest);
        return oAuth2UserMono.map(oAuth2User -> {
            OAuth2UserInfo userInfo = new OAuth2UserInfo((DefaultOAuth2User) oAuth2User, userRequest.getClientRegistration().getRegistrationId());
            return userInfo;
        });
    }
}
