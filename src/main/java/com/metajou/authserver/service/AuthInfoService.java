package com.metajou.authserver.service;

import com.metajou.authserver.entity.auth.AuthInfo;
import com.metajou.authserver.entity.oauth2.OAuth2UserInfo;
import com.metajou.authserver.repository.AuthInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthInfoService {

    private final AuthInfoRepository authInfoRepository;

    @Autowired
    public AuthInfoService(AuthInfoRepository authInfoRepository) {
        this.authInfoRepository = authInfoRepository;
    }

    public Mono<AuthInfo> registerAuthInfo(OAuth2UserInfo oAuth2UserInfo){
        AuthInfo authInfo = oAuth2UserInfo.extractAuthInfo();
        Mono<AuthInfo> authInfoMono = authInfoRepository
                .findAuthInfoByUserIdAndProvider(authInfo.getUserId(), authInfo.getProvider())
                .switchIfEmpty(Mono.defer(() -> authInfoRepository.save(authInfo)));
        return authInfoMono;
    }

}
