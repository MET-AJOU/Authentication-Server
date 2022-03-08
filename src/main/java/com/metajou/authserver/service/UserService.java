package com.metajou.authserver.service;

import com.metajou.authserver.repository.MainUserRepository;
import com.metajou.authserver.repository.SubUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final MainUserRepository mainUserRepository;
    private final SubUserRepository subUserRepository;

    @Autowired
    public UserService(MainUserRepository mainUserRepository, SubUserRepository subUserRepository) {
        this.mainUserRepository = mainUserRepository;
        this.subUserRepository = subUserRepository;
    }

}
