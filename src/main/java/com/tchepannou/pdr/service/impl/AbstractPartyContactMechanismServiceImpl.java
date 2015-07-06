package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pdr.domain.*;
import com.tchepannou.pdr.dto.party.AbstractPartyContactMechanismRequest;
import com.tchepannou.pdr.enums.Privacy;
import com.tchepannou.pdr.exception.BadRequestException;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.AbstractPartyContactMechanismService;
import com.tchepannou.pdr.service.ContactMechanismPurposeService;
import com.tchepannou.pdr.service.ContactMechanismTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPartyContactMechanismServiceImpl<T extends PartyContactMecanism>  implements AbstractPartyContactMechanismService<T> {
    //-- Attributes
    @Autowired
    protected ContactMechanismPurposeService contactMechanismPurposeService;

    @Autowired
    protected ContactMechanismTypeService contactMechanismTypeService;


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
        if (name != null) {
            return contactMechanismPurposeService.findByName(name);
        }
        return null;
    }

    protected Class getPersistentClass () {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public T addAddress(
            final Party party,
            final String typeName,
            final AbstractPartyContactMechanismRequest request,
            final T partyContactMechanism,
            final ContactMechanism contactMechanism
    ) {
        try {
            final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

            final ContactMechanismType type = contactMechanismTypeService.findByName(typeName);

            partyContactMechanism.setPartyId(party.getId());
            partyContactMechanism.setTypeId(type.getId());
            partyContactMechanism.setContactId(contactMechanism.getId());
            partyContactMechanism.setPrivacy(Privacy.fromText(request.getPrivacy()));
            partyContactMechanism.setNoSolicitation(request.isNoSolicitation());
            partyContactMechanism.setPurposeId(purpose == null ? 0 : purpose.getId());

            getDao().create(partyContactMechanism);

            return partyContactMechanism;
        } catch (NotFoundException e) {
            if (ContactMechanismPurpose.class.equals(e.getPersistentClass())) {
                throw new BadRequestException("purpose");
            } else if (ContactMechanismType.class.equals(e.getPersistentClass())) {
                throw new BadRequestException("type");
            } else {
                throw e;
            }
        }
    }


    protected T updateAddress(
            final Party party,
            final AbstractPartyContactMechanismRequest request,
            final T partyContactMechanism,
            final ContactMechanism contactMechanism
    ) {
        if (partyContactMechanism.getPartyId() != party.getId()) {
            ArrayList<Long> key = new ArrayList<>();
            key.add(party.getId());
            key.add(partyContactMechanism.getId());
            throw new NotFoundException(key, partyContactMechanism.getClass());
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
            ArrayList<Long> key = new ArrayList<>();
            key.add(party.getId());
            key.add(partyContactMecanism.getId());
            throw new NotFoundException(key, partyContactMecanism.getClass());
        }

        getDao().delete(partyContactMecanism.getId());
    }

}
