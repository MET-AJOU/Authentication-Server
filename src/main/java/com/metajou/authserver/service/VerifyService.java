package com.metajou.authserver.service;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.verify.dto.AjouEmailVerifyRequest;
import com.metajou.authserver.repository.VerifyInfoRepository;
import com.metajou.authserver.util.EmailUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VerifyService {

    private final VerifyInfoRepository verifyInfoRepository;
    private final EmailUtils emailUtils;

    public VerifyService(VerifyInfoRepository verifyInfoRepository, EmailUtils emailUtils) {
        this.verifyInfoRepository = verifyInfoRepository;
        this.emailUtils = emailUtils;
    }

    public Mono<Boolean> sendEmail(CustomUser user, AjouEmailVerifyRequest req) {
        return emailUtils.send(null);
    }

    public Mono<Boolean> sendToAjouEmail() {
        return emailUtils.send(null);
    }
}
