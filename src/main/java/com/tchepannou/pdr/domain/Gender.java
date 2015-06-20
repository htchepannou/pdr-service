package com.tchepannou.pdr.domain;

public enum Gender {
    MALE('M'), FEMALE('F'), UNKNOWN('?');

    private char code;

    Gender (char code) {
        this.code = code;
    }

    public char getCode (){
        return code;
    }

    public static Gender fromCode (final char code) {
        if (MALE.code == code) {
            return MALE;
        } else if (FEMALE.code == code) {
            return FEMALE;
        } else {
            return UNKNOWN;
        }
    }

    public static Gender fromCode (final String code) {
        return code != null && code.length() == 1 ? fromCode(code.toUpperCase().charAt(0)) : UNKNOWN;
    }
}
