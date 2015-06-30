package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.AccessToken;

public interface AccessTokenDao {
    AccessToken findById (long id);

    long create (AccessToken token);

    void update (AccessToken token);
}
