package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.UserStatusCode;

public interface UserStatusCodeService extends AbstractPersistentEnumService<UserStatusCode>{
    UserStatusCode findDefault ();
}
