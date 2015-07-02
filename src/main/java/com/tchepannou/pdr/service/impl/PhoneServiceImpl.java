package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractContactMechanismDao;
import com.tchepannou.pdr.dao.PhoneDao;
import com.tchepannou.pdr.domain.Phone;
import com.tchepannou.pdr.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;

public class PhoneServiceImpl extends AbstractContactMecanismServiceImpl<Phone> implements PhoneService {
    @Autowired
    private PhoneDao dao;

    @Override
    protected AbstractContactMechanismDao<Phone> getDao() {
        return dao;
    }
}
