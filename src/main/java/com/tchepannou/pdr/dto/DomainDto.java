package com.tchepannou.pdr.dto;

import com.google.common.base.Preconditions;
import com.tchepannou.pdr.domain.Domain;

public class DomainDto {
    //-- Attributes
    private long id;
    private final String name;
    private final String description;


    //-- Attributes
    private DomainDto (final Builder builder) {
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

        public DomainDto build () {
            Preconditions.checkState(domain != null, "domain is null");

            return new DomainDto(this);
        }

        public Builder withDomain (final Domain domain) {
            this.domain = domain;
            return this;
        }
    }
}
