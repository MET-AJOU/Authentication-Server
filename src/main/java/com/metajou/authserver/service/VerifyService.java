package com.metajou.authserver.service;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.verify.VerifingTokenInfo;
import com.metajou.authserver.entity.verify.dto.AjouEmailVerifyRequest;
import com.metajou.authserver.entity.verify.dto.SendEmailDto;
import com.metajou.authserver.entity.verify.dto.VerifyTokenRequest;
import com.metajou.authserver.repository.VerifingTokenInfoRepository;
import com.metajou.authserver.repository.VerifyInfoRepository;
import com.metajou.authserver.util.EmailUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public class VerifyService {

    private final VerifyInfoRepository verifyInfoRepository;
    private final VerifingTokenInfoRepository tokenInfoRepository;
    private final EmailUtils emailUtils;
    private static final String verifyMessageSubject = "[인증코드]";
    private static final String verifyMessageHead = "안녕하세요. \n";
    private static final String verifyMessageTail = "\n 감사합니다.";

    public VerifyService(VerifyInfoRepository verifyInfoRepository, VerifingTokenInfoRepository tokenInfoRepository, EmailUtils emailUtils) {
        this.verifyInfoRepository = verifyInfoRepository;
        this.tokenInfoRepository = tokenInfoRepository;
        this.emailUtils = emailUtils;
    }

    public Mono<Boolean> sendEmail(CustomUser user, AjouEmailVerifyRequest req) {
        return emailUtils.send(null);
    }

    public Mono<Boolean> sendVerifyTokenToAjouEmail(CustomUser user, AjouEmailVerifyRequest ajouIdReq) {
        String ajouEmail = new SendEmailDto(ajouIdReq, null, null).getTo();

        return uploadVerifyTokenInfo(user, ajouEmail)
                .flatMap(verifingTokenInfo ->
                        emailUtils.send(new SendEmailDto(
                                ajouIdReq,
                                verifyMessageSubject,
                                verifyMessageHead + verifingTokenInfo.getVerifyToken() + verifyMessageTail
                        )));
    }

    public Mono<Boolean> checkVerifyTokenIsCorrect(CustomUser user, VerifyTokenRequest reqData) {
        return Mono.just(true);
    }

    protected Mono<VerifingTokenInfo> uploadVerifyTokenInfo(CustomUser user, String verifyEmail) {
        final String token = makeVerifyToken();
        return tokenInfoRepository.findVerifingTokenInfoByUserCode(user.getUserCode())
                .switchIfEmpty(Mono.defer(() -> Mono.just(new VerifingTokenInfo(user.getUserCode(), verifyEmail))
                )).flatMap(verifingTokenInfo -> {
                    verifingTokenInfo.setVerifyToken(token);
                    verifingTokenInfo.setVerifyEmail(verifyEmail);
                    return tokenInfoRepository.save(verifingTokenInfo);
                });
    }

    private String makeVerifyToken() {
        Random rand = new Random();
        Integer resNum = rand.nextInt(100000, 999999);
        return resNum.toString();
    }
}
