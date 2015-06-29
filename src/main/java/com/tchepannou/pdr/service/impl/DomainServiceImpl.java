package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.DomainDao;
import com.tchepannou.pdr.domain.Domain;
import com.tchepannou.pdr.exception.DuplicateNameException;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class DomainServiceImpl implements DomainService {
    //-- Attributes
    @Autowired
    private DomainDao domainDao;

    //-- DomainService overrides
    @Override
    public Domain findById(long id) {
        Domain domain = domainDao.findById(id);
        if (domain == null || domain.isDeleted()) {
            throw new NotFoundException(id);
        }
        return domain;
    }

    @Override
    public List<Domain> findAll() {
        return domainDao.findAll();
    }

    @Transactional
    @Override
    public void create(final Domain domain) {
        try {
            domain.setFromDate(new Date());
            long id = domainDao.create(domain);
            domain.setId(id);
        } catch (DuplicateKeyException e) {
            throw new DuplicateNameException(domain.getName() + " already assigned to another domain", e);
        }
    }

    @Transactional
    @Override
    public void update(final Domain domain) {
        try {
            domainDao.update(domain);
        } catch (DuplicateKeyException e) {
            throw new DuplicateNameException(domain.getName() + " already assigned to another domain", e);
        }
    }

    @Transactional
    @Override
    public void delete(final long id) {
        findById(id);       // Make sure that domain exists

        domainDao.delete(id);
    }
}
