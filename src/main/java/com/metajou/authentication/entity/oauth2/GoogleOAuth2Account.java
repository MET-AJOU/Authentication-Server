package com.metajou.authentication.entity.oauth2;

import java.util.Map;

public class GoogleOAuth2Account extends OAuth2Account{

    public GoogleOAuth2Account(Map<String, Object> attributes) {
        super(attributes);
        this.name = String.valueOf(String.valueOf(attributes.get("sub")));
        this.nickName = String.valueOf(attributes.get("name"));
        this.email = String.valueOf(attributes.get("email"));
        this.image = String.valueOf(attributes.get("picture"));
    }

    @Override
    protected OAuth2Provider getOAuth2Provider() {
        return OAuth2Provider.GOOGLE;
    }
}
