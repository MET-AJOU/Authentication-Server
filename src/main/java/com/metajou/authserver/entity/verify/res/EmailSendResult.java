package com.metajou.authserver.entity.verify.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmailSendResult {
    private final Boolean isEmailSended;
}
