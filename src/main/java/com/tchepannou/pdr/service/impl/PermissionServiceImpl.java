package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPersistentEnumDao;
import com.tchepannou.pdr.dao.PermissionDao;
import com.tchepannou.pdr.domain.Permission;
import com.tchepannou.pdr.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PermissionServiceImpl extends AbstractPersistentEnumServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    protected AbstractPersistentEnumDao<Permission> getDao() {
        return permissionDao;
    }

    @Override
    public List<Permission> findByRole(long roleId) {
        return permissionDao.findByRole(roleId);
    }
}
