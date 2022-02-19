package com.metajou.authentication.util;

import com.metajou.authentication.entity.Account;
import com.metajou.authentication.property.SecretProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;

public class AccountUtil {

    @Autowired
    private SecretProperties secretProperties;
    private String createKey;

    private AccountUtil() {}

    public Account generateCreateAccountDto(String email) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return new Account(generateUsername(email), generateAccountToken(email));
    }

    public String generateAccountToken(String email) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest tmpDigest = MessageDigest.getInstance("SHA-512");
        tmpDigest.update((email + "$$" + createKey).getBytes());
        return new String(Base64.getEncoder().encode(tmpDigest.digest()), "UTF-8");
    }

    public String generateUsername(String email) {
        return convertEmailToUsername(email).toLowerCase(Locale.ROOT);
    }

    public String convertEmailToUsername(String email) {
        return email.split("@")[0];
    }

    @PostConstruct
    private void init(){
        createKey = secretProperties.getCREATE_TOKEN_KEY();
    }

    public static AccountUtil getInstance() {
        return ClassHolder.INSTANCE;
    }

    private static class ClassHolder {
        public static final AccountUtil INSTANCE = new AccountUtil();
    }
}
