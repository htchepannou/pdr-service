package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractContactMechanismDao;
import com.tchepannou.pdr.dao.ElectronicAddressDao;
import com.tchepannou.pdr.domain.ElectronicAddress;
import com.tchepannou.pdr.service.ElectronicAddressService;
import org.springframework.beans.factory.annotation.Autowired;

public class ElectronicAddressServiceImpl extends AbstractContactMecanismServiceImpl<ElectronicAddress> implements ElectronicAddressService {
    @Autowired
    private ElectronicAddressDao dao;

    @Override
    protected AbstractContactMechanismDao<ElectronicAddress> getDao() {
        return dao;
    }
}
