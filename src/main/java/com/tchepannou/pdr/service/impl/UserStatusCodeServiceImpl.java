package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPersistentEnumDao;
import com.tchepannou.pdr.dao.UserStatusCodeDao;
import com.tchepannou.pdr.domain.UserStatusCode;
import com.tchepannou.pdr.service.UserStatusCodeService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserStatusCodeServiceImpl extends AbstractPersistentEnumServiceImpl<UserStatusCode> implements UserStatusCodeService{
    @Autowired
    private UserStatusCodeDao dao;

    @Override
    public UserStatusCode findDefault() {
        return ((UserStatusCodeDao)getDao()).findDefault();
    }

    @Override
    protected AbstractPersistentEnumDao<UserStatusCode> getDao() {
        return dao;
    }
}
