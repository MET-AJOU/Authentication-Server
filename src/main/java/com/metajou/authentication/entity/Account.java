package com.metajou.authentication.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@ToString
@RequiredArgsConstructor
public class Account {
    @Id
    private String id;
    @NonNull
    private String username;
    private String userToken;
    @NonNull
    private String registerDate;
    @NonNull
    private String mainEmail;
    private String authToken;
    private String ajouEmail;
    @NonNull
    private Role role;
}
