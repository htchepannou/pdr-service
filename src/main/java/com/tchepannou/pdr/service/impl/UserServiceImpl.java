package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.UserDao;
import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.dto.party.CreatePartyRequest;
import com.tchepannou.pdr.dto.user.CreateUserRequest;
import com.tchepannou.pdr.enums.PartyKind;
import com.tchepannou.pdr.enums.UserStatus;
import com.tchepannou.pdr.exception.DuplicateEmailException;
import com.tchepannou.pdr.exception.DuplicateLoginException;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.PartyElectronicAddressService;
import com.tchepannou.pdr.service.PartyService;
import com.tchepannou.pdr.service.PasswordEncryptor;
import com.tchepannou.pdr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class UserServiceImpl implements UserService {
    //-- Attributes
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Autowired
    private PartyService partyService;

    @Autowired
    private PartyElectronicAddressService partyElectronicAddressService;

    //-- UserService overrides
    @Override
    public User findById(long id) {
        User user = userDao.findById(id);
        if (user == null || user.isDeleted()){
            throw new NotFoundException(id);
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
    public boolean isEmailAlreadyAssigned (final String email) {
        User user = userDao.findByEmail(email);
        return user != null && !user.isDeleted();
    }

    @Override
    @Transactional
    public User create (final CreateUserRequest request) {
        final Party party = createParty(request);
        try {
            final User user = new User();
            user.setStatus(UserStatus.CREATED);
            user.setPartyId(party.getId());
            user.setLogin(request.getLogin());
            user.setPassword(passwordEncryptor.encrypt(request.getPassword()));
            user.setFromDate(new Date());
            userDao.create(user);

            return user;
        } catch (DuplicateKeyException e){
            throw new DuplicateLoginException(request.getLogin());
        }
    }

    @Override
    @Transactional
    public User updatePassword(final long id, final String password) {
        final User user = findById(id);
        user.setPassword(
                passwordEncryptor.encrypt(password)
        );
        userDao.update(user);
        return user;
    }

    @Override
    @Transactional
    public User updateLogin(final long id, final String login) {
        final User user = findById(id);
        try {
            user.setLogin(login);
            userDao.update(user);
            return user;
        } catch (DuplicateKeyException e){
            throw new DuplicateLoginException(login);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        findById(id);
        userDao.delete(id);
    }

    //-- Private
    private Party createParty  (final CreateUserRequest request) {
        final long partyId = request.getPartyId();
        if (partyId > 0) {
            return partyService.findById(partyId);
        }

        final String email = request.getEmail();
        if (isEmailAlreadyAssigned(email)){
            throw new DuplicateEmailException(email);
        }

        /* create the party */
        CreatePartyRequest partyRequest = new CreatePartyRequest();
        partyRequest.setKind(PartyKind.PERSON.name());
        partyRequest.setFirstName(request.getFirstName());
        partyRequest.setLastName(request.getLastName());
        Party party = partyService.create(partyRequest);

        /* ad the email */
        partyElectronicAddressService.addEmail(party, email);

        return party;
    }
}
