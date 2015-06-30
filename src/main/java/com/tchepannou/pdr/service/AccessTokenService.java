package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.AccessToken;

public interface AccessTokenService {
    AccessToken findById (long id);

    void create (AccessToken token);

    void update (AccessToken token);
}
