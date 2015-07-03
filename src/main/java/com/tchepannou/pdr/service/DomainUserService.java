package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.DomainUser;

import java.util.List;

public interface DomainUserService {
    DomainUser findByDomainByUserByRole(long domainId, long userId, long roleId);

    List<DomainUser> findByDomainByUser(long domainId, long userId);

    DomainUser create (long domainId, long userId, long roleId);

    void delete (long domainId, long userId, long roleId);
}
