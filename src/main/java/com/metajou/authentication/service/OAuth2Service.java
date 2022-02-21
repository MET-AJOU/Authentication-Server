package com.metajou.authentication.service;

import com.metajou.authentication.entity.oauth2.OAuth2Account;
import com.metajou.authentication.repository.AccountRepository;
import com.metajou.authentication.repository.OAuth2AccountRepository;
import com.metajou.authentication.util.OAuth2AccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OAuth2Service implements ReactiveOAuth2UserService<OAuth2UserRequest,OAuth2User> {

    private final OAuth2AccountRepository oAuth2AccountRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public OAuth2Service(OAuth2AccountRepository oAuth2AccountRepository, AccountRepository accountRepository) {
        this.oAuth2AccountRepository = oAuth2AccountRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final ReactiveOAuth2UserService delegete = new DefaultReactiveOAuth2UserService();
        Mono<OAuth2User> oAuth2User = delegete.loadUser(userRequest);

        return oAuth2User;
    }

    private void registerOAuth2Account(OAuth2UserRequest userRequest, Mono<OAuth2User> oAuth2User) {
        final String clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();
        oAuth2User.flatMap(user -> {
            OAuth2Account oAuth2Account = OAuth2AccountUtil.getInstance().getOAuth2UserInfo(clientRegistrationId, user.getAttributes());
            return oAuth2AccountRepository.findOAuth2AccountByName(oAuth2Account.getName())
                    .switchIfEmpty(Mono.defer(
                            () -> oAuth2AccountRepository.save(oAuth2Account)
                    ));
        });
    }


}
