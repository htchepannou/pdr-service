package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.UserStatus;

import java.util.List;

public interface UserStatusDao {
    UserStatus findById (long id);
    List<UserStatus> findByUser (long id);
    long create (UserStatus userStatus);
}
