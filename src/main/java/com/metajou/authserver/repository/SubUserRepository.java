package com.metajou.authserver.repository;

import com.metajou.authserver.entity.SubUser;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface SubUserRepository extends R2dbcRepository<SubUser, Long> {

    public Mono<SubUser> findByUserEmail(String userEmail);
}
