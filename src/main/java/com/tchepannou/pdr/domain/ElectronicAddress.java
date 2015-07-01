package com.tchepannou.pdr.domain;

import org.apache.commons.codec.digest.DigestUtils;

public class ElectronicAddress extends ContactMechanism {
    //-- Attributes
    private String address;
    private transient String hash;

    //-- Public
    public static String hash (String str) {
        return DigestUtils.md5Hex(str.toLowerCase());
    }

    //-- Getter/Setter
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHash () {
        if (hash == null) {
            hash = hash(address);
        }
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}