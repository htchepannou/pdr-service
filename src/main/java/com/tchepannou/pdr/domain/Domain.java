package com.tchepannou.pdr.domain;

public class Domain extends Persistent {
    //-- Attributes
    private String name;
    private String description;


    //-- Getter/Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
