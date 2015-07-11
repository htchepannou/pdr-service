package com.tchepannou.pds.service.impl;

import com.tchepannou.core.exception.NotFoundException;
import com.tchepannou.pds.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pds.dao.ContactMechanismPurposeDao;
import com.tchepannou.pds.dao.ContactMechanismTypeDao;
import com.tchepannou.pds.dao.PartyDao;
import com.tchepannou.pds.domain.*;
import com.tchepannou.pds.dto.party.AbstractPartyContactMechanismRequest;
import com.tchepannou.pds.enums.Privacy;
import com.tchepannou.pds.exception.BadRequestException;
import com.tchepannou.pds.service.AbstractPartyContactMechanismService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPartyContactMechanismServiceImpl<T extends PartyContactMecanism>  implements AbstractPartyContactMechanismService<T> {
    //-- Attributes
    @Autowired
    protected PartyDao partyDao;
    
    @Autowired
    protected ContactMechanismPurposeDao contactMechanismPurposeDao;

    @Autowired
    protected ContactMechanismTypeDao contactMechanismTypeDao;


    //-- Abstract
    protected abstract AbstractPartyContactMechanismDao<T> getDao();

    //-- AbstractPartyContactMechanismService overrides
    @Override
    public T findById(final long id) {
        T address = getDao().findById(id);
        if (address == null){
            throw new NotFoundException(id, getPersistentClass());
        }
        return address;
    }

    @Override
    public List<T> findByParty(final long partyId) {
        return getDao().findByParty(partyId);
    }

    //-- Protected
    protected ContactMechanismPurpose findPurpose (String name){
        ContactMechanismPurpose purpose = null;
        if (name != null) {
            purpose = contactMechanismPurposeDao.findByName(name);
            if (purpose == null) {
                throw new BadRequestException("purpose");
            }
        }
        return purpose;
    }

    protected ContactMechanismType findType  (String name){
        ContactMechanismType type = null;
        if (name != null) {
            type = contactMechanismTypeDao.findByName(name);
            if (type == null) {
                throw new BadRequestException("type");
            }
            return type;
        }
        return type;
    }

    protected Class getPersistentClass () {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public T createAddress(
            final Party party,
            final ContactMechanismType type,
            final ContactMechanismPurpose purpose,
            final AbstractPartyContactMechanismRequest request,
            final ContactMechanism contactMechanism,
            final T partyContactMechanism
    ) {
        partyContactMechanism.setPartyId(party.getId());
        partyContactMechanism.setTypeId(type.getId());
        partyContactMechanism.setContactId(contactMechanism.getId());
        partyContactMechanism.setPrivacy(Privacy.fromText(request.getPrivacy()));
        partyContactMechanism.setNoSolicitation(request.isNoSolicitation());
        partyContactMechanism.setPurposeId(purpose == null ? 0 : purpose.getId());

        getDao().create(partyContactMechanism);

        return partyContactMechanism;

    }

    @Deprecated
    public T addAddress(
            final Party party,
            final String typeName,
            final AbstractPartyContactMechanismRequest request,
            final T partyContactMechanism,
            final ContactMechanism contactMechanism
    ) {
        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

        final ContactMechanismType type = findType(typeName);

        partyContactMechanism.setPartyId(party.getId());
        partyContactMechanism.setTypeId(type.getId());
        partyContactMechanism.setContactId(contactMechanism.getId());
        partyContactMechanism.setPrivacy(Privacy.fromText(request.getPrivacy()));
        partyContactMechanism.setNoSolicitation(request.isNoSolicitation());
        partyContactMechanism.setPurposeId(purpose == null ? 0 : purpose.getId());

        getDao().create(partyContactMechanism);

        return partyContactMechanism;

    }


    protected T updateAddress(
            final Party party,
            final AbstractPartyContactMechanismRequest request,
            final ContactMechanism contactMechanism,
            final T partyContactMechanism
    ) {
        if (partyContactMechanism.getPartyId() != party.getId()) {
            List<Long> key = new ArrayList<>();
            key.add(party.getId());
            key.add(partyContactMechanism.getId());
            throw new NotFoundException((ArrayList)key, partyContactMechanism.getClass());
        }

        try {
            final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

            partyContactMechanism.setContactId(contactMechanism.getId());
            partyContactMechanism.setPrivacy(Privacy.fromText(request.getPrivacy()));
            partyContactMechanism.setNoSolicitation(request.isNoSolicitation());
            partyContactMechanism.setPurposeId(purpose == null ? 0 : purpose.getId());

            getDao().update(partyContactMechanism);
            return partyContactMechanism;
        } catch (NotFoundException e) {
            if (ContactMechanismPurpose.class.equals(e.getPersistentClass())) {
                throw new BadRequestException("purpose");
            } else {
                throw e;
            }
        }
    }


    protected void removeAddress(
            final Party party,
            final PartyContactMecanism partyContactMecanism
    ) {
        if (partyContactMecanism.getPartyId() != party.getId()) {
            List<Long> key = new ArrayList<>();
            key.add(party.getId());
            key.add(partyContactMecanism.getId());
            throw new NotFoundException((ArrayList)key, partyContactMecanism.getClass());
        }

        getDao().delete(partyContactMecanism.getId());
    }

    protected Party findParty(final long id) {
        Party party = partyDao.findById(id);
        if (party == null){
            throw new NotFoundException(id, Party.class);
        }
        return party;
    }
}
