package com.metajou.authserver.repository;

import com.metajou.authserver.entity.auth.AuthInfo;
import com.metajou.authserver.entity.auth.oauth2.OAuth2Provider;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthInfoRepository extends R2dbcRepository<AuthInfo, Long> {
    Mono<AuthInfo> findAuthInfoByUserIdAndProvider(String userId, OAuth2Provider provider);
}
