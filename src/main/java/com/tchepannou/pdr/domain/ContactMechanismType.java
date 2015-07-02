package com.tchepannou.pdr.domain;

public class ContactMechanismType extends Persistent {
    //-- Attributes
    public static final String NAME_EMAIL = "email";
    public static final String NAME_WEB = "web";
    public static final String NAME_PHONE = "phone";
    public static final String NAME_POSTAL_ADDRESS = "postal";

    private String name;

    //-- Getter/Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmail () {
        return NAME_EMAIL.equals(getName());
    }

    public boolean isWeb () {
        return NAME_WEB.equals(getName());
    }

    public boolean isPhone () {
        return NAME_PHONE.equals(getName());
    }

    public boolean isPostalAddress () {
        return NAME_POSTAL_ADDRESS.equals(getName());
    }
}
