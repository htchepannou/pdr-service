package com.tchepannou.pdr.domain;

public class Permission extends Persistent {
    //-- Attributes
    private String name;

    //-- Getter/Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
