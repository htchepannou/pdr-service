package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.DomainUserDao;
import com.tchepannou.pdr.domain.DomainUser;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.DomainUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class DomainUserServiceImpl implements DomainUserService {
    @Autowired
    private DomainUserDao domainUserDao;

    @Override
    public DomainUser findByDomainByUserByRole(long domainId, long userId, long roleId) {
        final DomainUser domainUser = domainUserDao.findByDomainByUser(domainId, userId, roleId);
        if (domainUser == null){
            ArrayList<Long> key = new ArrayList<>();
            key.add(domainId);
            key.add(userId);
            key.add(roleId);
            throw new NotFoundException(key, DomainUser.class);
        }
        return domainUser;
    }

    @Override
    public List<DomainUser> findByDomainByUser(long domainId, long userId) {
        return domainUserDao.findByDomainByUser(domainId, userId);
    }

    @Override
    @Transactional
    public DomainUser create(
            final long domainId,
            final long userId,
            final long roleId
    ) {
        try {
            DomainUser domainUser = new DomainUser(domainId, userId, roleId);
            domainUserDao.create(domainUser);
            return domainUser;
        } catch (DuplicateKeyException e) { // NOSONAR
            return findByDomainByUserByRole(domainId, userId, roleId);
        }
    }

    @Override
    @Transactional
    public void delete(
            final long domainId,
            final long userId,
            final long roleId
    ) {
        DomainUser domainUser = findByDomainByUserByRole(domainId, userId, roleId);
        domainUserDao.delete(domainUser.getId());
    }
}
