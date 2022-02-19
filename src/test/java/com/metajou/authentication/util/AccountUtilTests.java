package com.metajou.authentication.util;

import com.metajou.authentication.property.SecretProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

@ExtendWith(SpringExtension.class)
@TestPropertySource(value = {"classpath:secret.properties"})
@EnableConfigurationProperties(value = {SecretProperties.class})
@ContextConfiguration(classes = {AccountUtil.class})
public class AccountUtilTests {

    AccountUtil util = AccountUtil.getInstance();

    @Test
    public void checkCreatedTokenUnique() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int testCount = 30;
        byte[] rStr = new byte[50];
        for(int i = 0; i < testCount; i++) {
            new Random().nextBytes(rStr);
            String generatedStr = new String(Base64.getEncoder().encode(rStr));
            String t1 = util.generateAccountToken(generatedStr);
            String t2 = util.generateAccountToken(generatedStr);
            Assertions.assertEquals(t1,t2);
        }
    }

    @Test
    public void checkConvertingEmailToUsername() {
        Assertions.assertEquals(util.convertEmailToUsername("TesT@gmail.com"), "TesT");
        Assertions.assertEquals(util.convertEmailToUsername("Mim%IV#iVV@gmail.com"), "Mim%IV#iVV");
    }

    @Test
    public void checkGenerateUsername() {
        Assertions.assertEquals(util.generateUsername("TesT@gmail.com"), "test");
        Assertions.assertEquals(util.generateUsername("Mim%IV#iVV@gmail.com"), "mim%iv#ivv");
    }

}
