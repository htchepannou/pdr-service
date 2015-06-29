package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.UserDao;
import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public User findByParty(long partyId) {
        return userDao.findByParty(partyId);
    }

    @Override
    @Transactional
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        userDao.update(user);
    }
}
