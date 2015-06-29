package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.Permission;

import java.util.List;

public interface PermissionDao {
    List<Permission> findByRole(long roleId);
}
