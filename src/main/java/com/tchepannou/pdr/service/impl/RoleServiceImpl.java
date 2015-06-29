package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.RoleDao;
import com.tchepannou.pdr.domain.Role;
import com.tchepannou.pdr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findById(long id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
