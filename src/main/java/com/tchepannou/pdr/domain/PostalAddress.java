package com.tchepannou.pdr.domain;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.apache.commons.codec.digest.DigestUtils;

public class PostalAddress extends ContactMechanism {
    //-- Attributes
    private String street1;
    private String street2;
    private String city;
    private String stateCode;
    private String zipCode;
    private String countryCode;
    private transient String hash;

    //-- Public
    public static String computeHash(
            final String street1,
            final String street2,
            final String city,
            final String stateCode,
            final String zipCode,
            final String countryCode
    ){
        String str = Joiner
                .on('-')
                .join(
                    Strings.nullToEmpty(street1),
                    Strings.nullToEmpty(street2),
                    Strings.nullToEmpty(city),
                    Strings.nullToEmpty(stateCode),
                    Strings.nullToEmpty(zipCode),
                    Strings.nullToEmpty(countryCode)
                );
        return DigestUtils.md5Hex(str.toLowerCase());
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getHash() {
        if (hash == null){
            hash = computeHash(street1, street2, city, stateCode, zipCode, countryCode);
        }
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
