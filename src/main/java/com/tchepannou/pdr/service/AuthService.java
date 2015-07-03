package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.AccessToken;
import com.tchepannou.pdr.dto.auth.AuthRequest;

public interface AuthService {
    AccessToken findById (long id);

    AccessToken login (AuthRequest request);

    void logout (long authId);
}
