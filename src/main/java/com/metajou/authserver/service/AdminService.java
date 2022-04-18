package com.metajou.authserver.service;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.auth.Role;
import com.metajou.authserver.entity.verify.req.BeAdminRequest;
import com.metajou.authserver.repository.AuthInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

@Service
public class AdminService {

    @Value("spring.project.role.adminkey")
    private String ADMIN_KEY;

    private final AuthInfoRepository authInfoRepository;

    public AdminService(AuthInfoRepository authInfoRepository) {
        this.authInfoRepository = authInfoRepository;
    }

    public Mono<Boolean> beAdmin(CustomUser user, BeAdminRequest req) {
        Assert.notNull(req, "adminToken은 Null일 수 없습니다.");
        Assert.isTrue(!ADMIN_KEY.equals(req), "adminToken이 일치하지 않습니다.");

        return Mono.just(user.getUserCode())
                .flatMap(authInfoRepository::findById)
                .doOnNext(authInfo -> authInfo.addAuthorities(Role.ROLE_ADMIN))
                .flatMap(authInfoRepository::save)
                .map(authInfo -> authInfo != null);
    }
}
