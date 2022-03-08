package com.metajou.authserver.repository;

import com.metajou.authserver.entity.MainUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MainUserRepository extends R2dbcRepository<MainUser, Long> {

}
