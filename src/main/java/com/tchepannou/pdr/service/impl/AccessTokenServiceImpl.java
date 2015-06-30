package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AccessTokenDao;
import com.tchepannou.pdr.domain.AccessToken;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.AccessTokenService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class AccessTokenServiceImpl implements AccessTokenService {
    @Autowired
    private AccessTokenDao accessTokenDao;

    @Value ("${access_token.ttl.minutes:30}")
    private int ttl;

    @Override
    public AccessToken findById(long id) {
        final AccessToken out = accessTokenDao.findById(id);
        if (out == null){
            throw new NotFoundException(id);
        }
        return out;
    }

    @Override
    @Transactional
    public void create(AccessToken token) {
        Date now = new Date ();
        token.setFromDate(now);
        token.setExpiryDate(DateUtils.addMinutes(now, ttl));

        long id = accessTokenDao.create(token);
        token.setId(id);
    }

    @Override
    @Transactional
    public void update(AccessToken token) {
        accessTokenDao.update(token);
    }
}
