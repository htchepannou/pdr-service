package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.DomainDao;
import com.tchepannou.pdr.domain.Domain;
import com.tchepannou.pdr.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DomainServiceImpl implements DomainService {
    //-- Attributes
    @Autowired
    private DomainDao domainDao;

    //-- DomainService overrides
    @Override
    public Domain findById(long id) {
        return domainDao.findById(id);
    }

    @Override
    public List<Domain> findAll() {
        return domainDao.findAll();
    }
}
