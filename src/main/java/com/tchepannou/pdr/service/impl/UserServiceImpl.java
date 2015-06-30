package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.UserDao;
import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.exception.DuplicateLoginException;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findById(long id) {
        User user = userDao.findById(id);
        if (user == null || user.isDeleted()){
            throw new NotFoundException(id);
        }
        return user;
    }

    @Override
    public User findByParty(long partyId) {
        User user = userDao.findByParty(partyId);
        if (user == null || user.isDeleted()){
            throw new NotFoundException();
        }
        return user;
    }

    @Override
    public User findByLogin(String login) {
        User user = userDao.findByLogin(login);
        if (user == null || user.isDeleted()){
            throw new NotFoundException();
        }
        return user;
    }

    @Override
    @Transactional
    public void create(User user) {
        try {
            user.setFromDate(new Date());
            final long id = userDao.create(user);
            user.setId(id);
        } catch (DuplicateKeyException e) {
            throw new DuplicateLoginException(user.getLogin() + " already assigned to another user", e);
        }
    }

    @Override
    @Transactional
    public void update(User user) {
        try {
            userDao.update(user);
        } catch (DuplicateKeyException e) {
            throw new DuplicateLoginException(user.getLogin() + " already assigned to another user", e);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        findById(id);
        userDao.delete(id);
    }
}
