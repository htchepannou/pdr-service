package com.tchepannou.pds.service.impl;

import com.tchepannou.core.dao.AbstractPersistentEnumDao;
import com.tchepannou.core.service.impl.AbstractPersistentEnumServiceImpl;
import com.tchepannou.pds.dao.PartyRoleTypeDao;
import com.tchepannou.pds.domain.PartyRoleType;
import com.tchepannou.pds.service.PartyRoleTypeService;
import org.springframework.beans.factory.annotation.Autowired;

public class PartyRoleTypeServiceImpl extends AbstractPersistentEnumServiceImpl<PartyRoleType> implements PartyRoleTypeService {
    @Autowired
    private PartyRoleTypeDao roleDao;

    @Override
    protected AbstractPersistentEnumDao<PartyRoleType> getDao() {
        return roleDao;
    }
}
