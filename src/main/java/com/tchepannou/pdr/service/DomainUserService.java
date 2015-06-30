package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.DomainUser;

import java.util.List;

public interface DomainUserService {
    DomainUser findByDomainByUser (long domainId, long userId, long roleId);

    List<DomainUser> findByDomainByUser(long domainId, long userId);

    void create (DomainUser domainUser);

    void delete (long id);
}
