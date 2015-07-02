package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPersistentEnumDao;
import com.tchepannou.pdr.dao.RoleDao;
import com.tchepannou.pdr.domain.Role;
import com.tchepannou.pdr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceImpl extends AbstractPersistentEnumServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    protected AbstractPersistentEnumDao<Role> getDao() {
        return roleDao;
    }
}
