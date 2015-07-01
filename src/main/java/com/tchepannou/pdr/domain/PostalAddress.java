package com.tchepannou.pdr.domain;

import com.google.common.base.Joiner;

public class PostalAddress extends ContactMechanism {
    //-- Attributes
    private String street1;
    private String street2;
    private String city;
    private String stateCode;
    private String zip;
    private String countryCode;
    private transient String hash;

    //-- Public
    public static String hash (
            final String street1,
            final String street2,
            final String city,
            final String stateCode,
            final String zip,
            final String countryCode
    ){
        return Joiner
                .on('_')
                .skipNulls()
                .join(street1, street2, city, stateCode, zip, countryCode);
    }

    //-- Getter/Setter
    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getHash() {
        if (hash == null){
            hash = hash(street1, street2, city, stateCode, zip, countryCode);
        }
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
