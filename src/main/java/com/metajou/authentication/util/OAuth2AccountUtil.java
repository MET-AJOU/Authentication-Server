package com.metajou.authentication.util;

import com.metajou.authentication.entity.oauth2.GoogleOAuth2Account;
import com.metajou.authentication.entity.oauth2.OAuth2Account;
import com.metajou.authentication.entity.oauth2.OAuth2Provider;
import org.springframework.context.ApplicationContextException;

import java.util.Map;

public class OAuth2AccountUtil {
    public OAuth2Account getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(OAuth2Provider.GOOGLE.toString())) {
            return new GoogleOAuth2Account(attributes);
        }  else {
            throw new ApplicationContextException(registrationId);
        }
    }

    public static OAuth2AccountUtil getInstance() {
        return OAuth2AccountUtil.ClassHolder.INSTANCE;
    }

    private static class ClassHolder {
        public static final OAuth2AccountUtil INSTANCE = new OAuth2AccountUtil();
    }

}
