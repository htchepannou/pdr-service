package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.Permission;

import java.util.List;

public interface PermissionDao extends AbstractPersistentEnumDao<Permission>{
    List<Permission> findByRole(long roleId);
}
