package com.metajou.authentication.entity;

import org.springframework.data.r2dbc.convert.EnumWriteSupport;

public enum Role {
    Root,
    Admin,
    User,
    UnVerified
}

class RoleConverter extends EnumWriteSupport<Role> {

}