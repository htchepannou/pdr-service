package com.tchepannou.pds.domain;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.apache.commons.codec.digest.DigestUtils;

public class Phone extends ContactMechanism {
    //-- Attributes
    private String countryCode;
    private String number;
    private String extension;
    private transient String hash;

    //-- Public
    public static String computeHash(String countryCode, String number, String extension) {
        String str = Joiner
                .on('-')
                .join(
                        Strings.nullToEmpty(countryCode),
                        Strings.nullToEmpty(number),
                        Strings.nullToEmpty(extension)
                );
        return DigestUtils.md5Hex(str.toLowerCase());
    }

    //-- Getter/Setter
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getHash () {
        if (hash == null) {
            hash = computeHash(countryCode, number, extension);
        }
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
