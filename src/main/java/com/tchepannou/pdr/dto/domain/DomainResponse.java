package com.tchepannou.pdr.dto.domain;

import com.google.common.base.Preconditions;
import com.tchepannou.pdr.domain.Domain;

import java.io.Serializable;

public class DomainResponse implements Serializable{
    //-- Attributes
    private final long id;
    private final String name;
    private final String description;


    //-- Attributes
    private DomainResponse(final Builder builder) {
        final Domain domain = builder.domain;

        this.id = domain.getId();
        this.name = domain.getName();
        this.description = domain.getDescription();
    }

    //-- Getter/Setter
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    //-- Builder
    public static class Builder {
        private Domain domain;

        public DomainResponse build () {
            Preconditions.checkState(domain != null, "domain is null");

            return new DomainResponse(this);
        }

        public Builder withDomain (final Domain domain) {
            this.domain = domain;
            return this;
        }
    }
}
