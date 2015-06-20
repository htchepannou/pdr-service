package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.Domain;

import java.util.List;

public interface DomainDao {
    Domain findById (long id);

    List<Domain> findAll ();
}
