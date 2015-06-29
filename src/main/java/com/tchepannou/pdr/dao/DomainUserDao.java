package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.DomainUser;

public interface DomainUserDao {
    DomainUser findByDomainByUser (long domainId, long userId);
    long create (DomainUser domainUser);
    void delete (long id);
}
