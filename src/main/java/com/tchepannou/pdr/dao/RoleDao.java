package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.Role;

import java.util.List;

public interface RoleDao {
    Role findById (long id);

    List<Role> findAll ();
}
