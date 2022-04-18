package com.metajou.authserver.service;

import com.metajou.authserver.entity.auth.AuthInfo;
import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.token.AccessToken;
import com.metajou.authserver.entity.token.AuthInfoRes;
import com.metajou.authserver.entity.verify.VerifyInfo;
import com.metajou.authserver.exception.ExceptionCode;
import com.metajou.authserver.repository.AuthInfoRepository;
import com.metajou.authserver.repository.VerifyInfoRepository;
import com.metajou.authserver.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TokenService {

    private final JwtUtils jwtUtils;
    private final AuthInfoRepository authInfoRepository;
    private final VerifyInfoRepository verifyInfoRepository;

    public Mono<AuthInfoRes> getSessionAuthInfo(CustomUser user) {
        return Mono.zip(getAuthInfo(user), getVerifyInfo(user, false))
                .flatMap(tuple -> {
                    AuthInfo authInfo = tuple.getT1();
                    VerifyInfo verifyInfo = tuple.getT2();
                    Mono<AuthInfoRes> res = Mono.just(AuthInfoRes.builder()
                            .user(authInfo.getUserEmail())
                            .provider(authInfo.getProvider().toString())
                            .role(authInfo.getAuthorities())
                            .expiredTime(jwtUtils.getExpirationDateFromToken(user.getToken().getValue()))
                            .verifiedEmail(verifyInfo.getVerifyEmail())
                            .build());
            return res;
        });
    }

    public Mono<AccessToken> refreshAccessTokenInCookie(CustomUser user, ServerHttpResponse response) {
        return getAuthInfo(user).flatMap(authInfo -> {
            String token = jwtUtils.generateToken(authInfo);
            jwtUtils.addCookieAccessTokenToResponse(response, token);
            return Mono.just(AccessToken.builder().accessToken(token).build());
        });
    }

    protected Mono<AuthInfo> getAuthInfo(CustomUser user) {
        return authInfoRepository.findById(user.getUserCode())
                .switchIfEmpty(Mono.error(ExceptionCode.NOT_FOUND_AUTHINFO.build()));
    }

    protected Mono<VerifyInfo> getVerifyInfo(CustomUser user, boolean throwable) {
        if(throwable)
            return getVerifyInfo(user);
        return verifyInfoRepository.findVerifyInfoByUserCode(user.getUserCode())
                .switchIfEmpty(Mono.defer(() -> Mono.just(VerifyInfo.empty)));
    }

    protected Mono<VerifyInfo> getVerifyInfo(CustomUser user) {
        return verifyInfoRepository.findVerifyInfoByUserCode(user.getUserCode())
                .switchIfEmpty(Mono.error(ExceptionCode.NO_VERIFIED_USER.build()));
    }

}
