package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.UserStatusDao;
import com.tchepannou.pdr.domain.UserStatus;
import com.tchepannou.pdr.service.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class UserStatusServiceImpl implements UserStatusService {
    @Autowired
    private UserStatusDao dao;


    @Override
    public UserStatus findById(long id) {
        return dao.findById(id);
    }

    @Override
    public List<UserStatus> findByUser(long id) {
        return dao.findByUser(id);
    }

    @Override
    public void create(UserStatus userStatus) {
        userStatus.setDate(new Date());
        dao.create(userStatus);
    }
}
