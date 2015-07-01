package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.ContactMechanismDao;
import com.tchepannou.pdr.dao.PostalAddressDao;
import com.tchepannou.pdr.domain.PostalAddress;
import com.tchepannou.pdr.service.PostalAddressService;
import org.springframework.beans.factory.annotation.Autowired;

public class PostalAddressServiceImpl extends AbstractContactMecanismServiceImpl<PostalAddress> implements PostalAddressService {
    @Autowired
    private PostalAddressDao dao;

    @Override
    protected ContactMechanismDao<PostalAddress> getDao() {
        return dao;
    }
}
