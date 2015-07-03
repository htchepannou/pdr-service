package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.dto.user.CreateUserRequest;

public interface UserService {
    User findById (long id);

    User findByLogin (String login);

    boolean isEmailAlreadyAssigned (final String email);

    User create (CreateUserRequest request);

    User updatePassword (long id, String password);

    User updateLogin (long id, String login);

    void delete (long id);
}
