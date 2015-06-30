package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.DomainUserDao;
import com.tchepannou.pdr.domain.DomainUser;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.DomainUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DomainUserServiceImpl implements DomainUserService {
    @Autowired
    private DomainUserDao domainUserDao;

    @Override
    public DomainUser findByDomainByUser(long domainId, long userId, long roleId) {
        final DomainUser domainUser = domainUserDao.findByDomainByUser(domainId, userId, roleId);
        if (domainUser == null){
            throw new NotFoundException();
        }
        return domainUser;
    }

    @Override
    public List<DomainUser> findByDomainByUser(long domainId, long userId) {
        return domainUserDao.findByDomainByUser(domainId, userId);
    }

    @Transactional
    @Override
    public void create(DomainUser domainUser) {
        try {
            final long id = domainUserDao.create(domainUser);
            domainUser.setId(id);
        } catch (DuplicateKeyException e) { // NOSONAR
            DomainUser clone = findByDomainByUser(domainUser.getDomainId(), domainUser.getUserId(), domainUser.getRoleId());
            domainUser.setId(clone.getId());
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        domainUserDao.delete(id);
    }
}
