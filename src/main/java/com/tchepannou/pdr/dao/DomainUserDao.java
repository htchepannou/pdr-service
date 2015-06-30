package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.DomainUser;

import java.util.List;

public interface DomainUserDao {
    DomainUser findByDomainByUser (long domainId, long userId, long roleId);

    List<DomainUser> findByDomainByUser(long domainId, long userId);

    long create (DomainUser domainUser);

    void delete (long id);
}
