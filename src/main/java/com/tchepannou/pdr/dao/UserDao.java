package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.User;

public interface UserDao {
    User findById (long id);

    User findByParty (long partyId);

    long create (User user);

    void update (User user);

    void delete (long id);
}
