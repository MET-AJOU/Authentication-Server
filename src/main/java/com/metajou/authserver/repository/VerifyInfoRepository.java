package com.metajou.authserver.repository;

import com.metajou.authserver.entity.verify.VerifyInfo;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VerifyInfoRepository extends R2dbcRepository<VerifyInfo, Long> {
    Mono<VerifyInfo> findVerifyInfoByUserCode(Long userCode);
}
