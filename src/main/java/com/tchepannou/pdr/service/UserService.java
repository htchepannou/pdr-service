package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.User;

public interface UserService {
    User findById (long id);

    User findByParty (long partyId);

    User findByLogin (String login);

    void create (User user);

    void update (User user);

    void delete (long id);
}
