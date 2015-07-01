package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.ElectronicAddress;

import java.util.Collection;
import java.util.List;

public interface ElectronicAddressService {
    ElectronicAddress findByAddress (String address);

    List<ElectronicAddress> findByIds (Collection<? extends Long> ids);

    void create (ElectronicAddress address);
}
