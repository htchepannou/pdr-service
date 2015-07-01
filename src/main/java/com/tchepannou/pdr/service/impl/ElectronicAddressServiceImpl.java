package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.ElectronicAddressDao;
import com.tchepannou.pdr.domain.ElectronicAddress;
import com.tchepannou.pdr.service.ElectronicAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

public class ElectronicAddressServiceImpl implements ElectronicAddressService {
    @Autowired
    private ElectronicAddressDao dao;

    @Override
    public ElectronicAddress findByAddress(String address) {
        return dao.findByAddress(address);
    }

    @Override
    public List<ElectronicAddress> findByIds(Collection<? extends Long> ids) {
        return dao.findByIds(ids);
    }

    @Override
    public void create(ElectronicAddress address) {
        long id = dao.create(address);
        address.setId(id);
    }
}
