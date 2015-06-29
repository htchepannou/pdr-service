package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findByRole(long roleId);
}
