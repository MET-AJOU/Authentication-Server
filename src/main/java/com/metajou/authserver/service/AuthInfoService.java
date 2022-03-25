package com.metajou.authserver.service;

import com.metajou.authserver.entity.auth.AuthInfo;
import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.auth.Role;
import com.metajou.authserver.entity.auth.oauth2.OAuth2UserInfo;
import com.metajou.authserver.exception.ExceptionCode;
import com.metajou.authserver.repository.AuthInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Mono<Boolean> updateAuthInfoAuthority(CustomUser user, Role role) {
        return getAuthInfo(user)
                .doOnNext(authInfo -> authInfo.addAuthorities(role))
                .flatMap(this::saveAuthInfo)
                .map(authInfo -> authInfo != null);
    }

    protected Mono<AuthInfo> saveAuthInfo(AuthInfo authInfo) {
        return authInfoRepository.save(authInfo)
                .doOnError(throwable -> Mono.error(ExceptionCode.AUTH_SERVER_ERROR.build()));
    }

    protected Mono<AuthInfo> getAuthInfo(CustomUser user) {
        return authInfoRepository.findAuthInfoByUserCode(user.getUserCode())
                .switchIfEmpty(Mono.error(ExceptionCode.NOT_FOUND_AUTHINFO.build()));
    }

}
