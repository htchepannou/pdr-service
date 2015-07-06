package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.UserStatusCode;

public interface UserStatusCodeDao extends AbstractPersistentEnumDao<UserStatusCode>{
    UserStatusCode findDefault ();
}
