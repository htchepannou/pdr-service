package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.ElectronicAddress;

import java.util.Collection;
import java.util.List;

public interface ElectronicAddressDao {
    ElectronicAddress findByAddress (String address);

    List<ElectronicAddress> findByIds (Collection<? extends Long> ids);

    long create (ElectronicAddress address);
}
