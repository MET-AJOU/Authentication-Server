package com.metajou.authserver.service;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.verify.VerifingTokenInfo;
import com.metajou.authserver.entity.verify.VerifyInfo;
import com.metajou.authserver.entity.verify.req.AjouEmailVerifyRequest;
import com.metajou.authserver.entity.verify.dto.SendEmailDto;
import com.metajou.authserver.entity.verify.req.VerifyTokenRequest;
import com.metajou.authserver.entity.verify.res.EmailSendResult;
import com.metajou.authserver.entity.verify.res.VerifingTokenSendResult;
import com.metajou.authserver.exception.ExceptionCode;
import com.metajou.authserver.repository.VerifingTokenInfoRepository;
import com.metajou.authserver.repository.VerifyInfoRepository;
import com.metajou.authserver.util.EmailUtils;
import com.nimbusds.oauth2.sdk.device.UserCode;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public class VerifyService {

    private final VerifyInfoRepository verifyInfoRepository;
    private final VerifingTokenInfoRepository tokenInfoRepository;
    private final EmailUtils emailUtils;
    private static final String verifyMessageSubject = "[인증코드] MET:AJOU 이메일 인증입니다.";
    private static final String verifyMessageHead = "안녕하세요. MET:AJOU입니다.\n이메일 인증 코드를 입니다.\n\n코드: ";
    private static final String verifyMessageTail = "\n\n감사합니다.";

    public VerifyService(VerifyInfoRepository verifyInfoRepository, VerifingTokenInfoRepository tokenInfoRepository, EmailUtils emailUtils) {
        this.verifyInfoRepository = verifyInfoRepository;
        this.tokenInfoRepository = tokenInfoRepository;
        this.emailUtils = emailUtils;
    }

    public Mono<Boolean> sendEmail(CustomUser user, AjouEmailVerifyRequest req) {
        return emailUtils.send(null);
    }

    public Mono<EmailSendResult> sendVerifyTokenToAjouEmail(CustomUser user, AjouEmailVerifyRequest ajouIdReq) {
        String ajouEmail = new SendEmailDto(ajouIdReq, null, null).getTo();
        return uploadVerifyTokenInfo(user, ajouEmail)
                .flatMap(verifingTokenInfo ->
                        emailUtils.send(new SendEmailDto(
                                ajouIdReq,
                                verifyMessageSubject,
                                verifyMessageHead + verifingTokenInfo.getVerifyToken() + verifyMessageTail
                        ))).map(b -> new EmailSendResult(b));
    }

    public Mono<VerifingTokenSendResult> checkVerifyTokenIsCorrect(CustomUser user, VerifyTokenRequest reqData) {
        return getVerifyTokenInfo(user.getUserCode())
                .flatMap(verifingTokenInfo -> {
                    if(!verifingTokenInfo.getVerifyToken().equals(reqData.getVerifyToken()))
                        return Mono.just(new VerifingTokenSendResult(false));
                    return deleteVerifyTokenInfo(verifingTokenInfo.getId())
                            .then(saveVerifyInfo(verifingTokenInfo))
                            .flatMap(verifyInfo -> Mono.just(new VerifingTokenSendResult(verifyInfo != null)));
                });
    }

    protected Mono<VerifingTokenInfo> uploadVerifyTokenInfo(CustomUser user, String verifyEmail) {
        final String token = makeVerifyToken();
        return  isVerifiedUser(user.getUserCode())
                .then(getVerifyTokenInfo(user.getUserCode(), false))
                .switchIfEmpty(Mono.defer(() -> Mono.just(new VerifingTokenInfo(user.getUserCode(), verifyEmail))
                )).flatMap(verifingTokenInfo -> {
                    verifingTokenInfo.setVerifyToken(token);
                    verifingTokenInfo.setVerifyEmail(verifyEmail);
                    return tokenInfoRepository.save(verifingTokenInfo)
                            .doOnError(throwable -> Mono.error(ExceptionCode.CANT_CREATE_VERIFY_INFO.build()));
                });
    }

    protected Mono<Void> isVerifiedUser(Long userCode) {
        return verifyInfoRepository.findVerifyInfoByUserCode(userCode)
                .flatMap(verifyInfo -> {
                    if(verifyInfo == null)
                        return Mono.empty();
                    return Mono.error(ExceptionCode.ALREADY_EXIST_VERIFYINFO.build());
                });
    }

    protected Mono saveVerifyInfo(VerifingTokenInfo verifingTokenInfo) {
        return verifyInfoRepository.save(new VerifyInfo(verifingTokenInfo.getUserCode(), verifingTokenInfo.getVerifyEmail()))
                .doOnError(throwable -> Mono.error(ExceptionCode.CANT_CREATE_VERIFY_INFO.build()));
    }

    protected Mono deleteVerifyTokenInfo(Long id) {
        return tokenInfoRepository.deleteById(id)
                .doOnError(throwable ->  Mono.error(ExceptionCode.CANT_REMOVE_VERIFINGTOKENINFO.build()));
    }

    protected Mono<VerifingTokenInfo> getVerifyTokenInfo(Long userCode, Boolean throwable) {
        if(throwable)
            return getVerifyTokenInfo(userCode);
        return tokenInfoRepository.findVerifingTokenInfoByUserCode(userCode)
                .doOnError(throwable1 -> Mono.error(ExceptionCode.ERROR_GET_VERIFYING_TOKEN.build()));
    }

    protected Mono<VerifingTokenInfo> getVerifyTokenInfo(Long userCode) {
        return tokenInfoRepository.findVerifingTokenInfoByUserCode(userCode)
                .doOnError(throwable -> Mono.error(ExceptionCode.ERROR_GET_VERIFYING_TOKEN.build()))
                .switchIfEmpty(Mono.error(ExceptionCode.NON_FOUND_VERIFYTOKEN.build()));
    }

    private String makeVerifyToken() {
        Random rand = new Random();
        Integer resNum = rand.nextInt(100000, 999999);
        return resNum.toString();
    }
}
