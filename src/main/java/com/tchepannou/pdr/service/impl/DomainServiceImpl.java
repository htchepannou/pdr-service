package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.DomainDao;
import com.tchepannou.pdr.domain.Domain;
import com.tchepannou.pdr.exception.DuplicateNameException;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

public class DomainServiceImpl implements DomainService {
    //-- Attributes
    @Autowired
    private DomainDao domainDao;

    //-- DomainService overrides
    @Override
    public Domain findById(long id) {
        Domain domain = domainDao.findById(id);
        if (domain == null) {
            throw new NotFoundException(id);
        }
        return domain;
    }

    @Override
    public List<Domain> findAll() {
        return domainDao.findAll();
    }

    @Override
    public void create(final Domain domain) {
        try {
            long id = domainDao.create(domain);
            domain.setId(id);
        } catch (DuplicateKeyException e) {
            throw new DuplicateNameException("The domain named <" + domain.getName() + "> already exists", e);
        }
    }

    @Override
    public void update(final Domain domain) {
        try {
            domainDao.update(domain);
        } catch (DuplicateKeyException e) {
            throw new DuplicateNameException("The domain named <" + domain.getName() + "> already exists", e);
        }
    }

    @Override
    public void delete(final long id) {
        domainDao.delete(id);
    }
}
