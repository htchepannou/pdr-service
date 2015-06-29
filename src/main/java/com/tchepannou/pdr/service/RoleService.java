package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.Role;

import java.util.List;

public interface RoleService {
    Role findById (long id);

    List<Role> findAll ();
}
