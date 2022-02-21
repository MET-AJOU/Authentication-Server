package com.metajou.authentication.repository;

import com.metajou.authentication.entity.oauth2.OAuth2Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface OAuth2AccountRepository extends R2dbcRepository<OAuth2Account, Long> {
    Mono<OAuth2Account> findOAuth2AccountByName(String name);
}
