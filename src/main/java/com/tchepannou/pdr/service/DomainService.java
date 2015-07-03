package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.Domain;
import com.tchepannou.pdr.dto.domain.DomainRequest;

import java.util.List;

public interface DomainService {
    Domain findById (long id);

    List<Domain> findAll ();

    Domain create (DomainRequest request);

    Domain update (long id, DomainRequest domain);

    void delete (long id);
}
