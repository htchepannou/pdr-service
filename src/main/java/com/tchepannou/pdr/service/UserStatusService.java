package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.UserStatus;

import java.util.List;

public interface UserStatusService {
    UserStatus findById (long id);
    List<UserStatus> findByUser (long id);
    void create (UserStatus userStatus);
}
