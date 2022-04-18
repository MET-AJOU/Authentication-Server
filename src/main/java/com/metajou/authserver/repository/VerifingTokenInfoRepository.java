package com.metajou.authserver.repository;

import com.metajou.authserver.entity.verify.VerifingTokenInfo;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VerifingTokenInfoRepository extends R2dbcRepository<VerifingTokenInfo, Long> {
    public Mono<VerifingTokenInfo> findVerifingTokenInfoByUserCode(Long userCode);
}
