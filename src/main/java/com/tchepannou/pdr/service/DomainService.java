package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.Domain;

import java.util.List;

public interface DomainService {
    Domain findById (long id);

    List<Domain> findAll ();

    void create (Domain domain);

    void update (Domain domain);

    void delete (long id);
}
