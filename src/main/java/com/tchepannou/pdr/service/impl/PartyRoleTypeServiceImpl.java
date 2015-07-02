package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPersistentEnumDao;
import com.tchepannou.pdr.dao.PartyRoleTypeDao;
import com.tchepannou.pdr.domain.PartyRoleType;
import com.tchepannou.pdr.service.PartyRoleTypeService;
import org.springframework.beans.factory.annotation.Autowired;

public class PartyRoleTypeServiceImpl extends AbstractPersistentEnumServiceImpl<PartyRoleType> implements PartyRoleTypeService {
    @Autowired
    private PartyRoleTypeDao roleDao;

    @Override
    protected AbstractPersistentEnumDao<PartyRoleType> getDao() {
        return roleDao;
    }
}
