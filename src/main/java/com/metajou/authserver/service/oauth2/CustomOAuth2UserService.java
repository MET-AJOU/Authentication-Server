package com.metajou.authserver.service.oauth2;

import com.metajou.authserver.entity.auth.oauth2.KakaoAuthInfo;
import com.metajou.authserver.entity.auth.oauth2.OAuth2Provider;
import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultReactiveOAuth2UserService {

    @Override
    public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Mono<OAuth2User> oAuth2UserMono = super.loadUser(userRequest);
        return oAuth2UserMono.map(oAuth2User -> makeOAuth2UserInfo(oAuth2User, userRequest));
    }

    public OAuth2User makeOAuth2UserInfo(OAuth2User user, OAuth2UserRequest userRequest) {
        String providerName = userRequest.getClientRegistration().getRegistrationId().toUpperCase(Locale.ROOT);
        if(providerName.equalsIgnoreCase(OAuth2Provider.KAKAO.toString())) {
            return convertToKakaoUser(user);
        }
        return null;
    }

    private KakaoAuthInfo convertToKakaoUser(OAuth2User user) {
        System.err.println(1);
        KakaoAuthInfo kakaoAuthInfo = new KakaoAuthInfo(user);
        System.err.println(2);
        Map<String, Object> properties = (Map<String, Object>) kakaoAuthInfo.getAttributes().get("properties");
        System.err.println(3);
        kakaoAuthInfo.setId(String.valueOf(kakaoAuthInfo.getAttributes().get("id")));
        System.err.println(4);
        kakaoAuthInfo.setEmail(String.valueOf(properties.get("email")));
        System.err.println(5);
        kakaoAuthInfo.setProvider(OAuth2Provider.KAKAO);
        System.err.println(kakaoAuthInfo);
        return kakaoAuthInfo;
    }
}
