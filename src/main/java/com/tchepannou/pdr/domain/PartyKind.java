package com.tchepannou.pdr.domain;

public enum PartyKind {
    PERSON('P'),
    ORGANIZATION('O'),
    UNKNOWN('?');

    private char code;

    PartyKind (char code) {
        this.code = code;
    }

    public char getCode (){
        return code;
    }

    public static PartyKind fromCode (final char code) {
        if (PERSON.code == code) {
            return PERSON;
        } else if (ORGANIZATION.code == code) {
            return ORGANIZATION;
        } else {
            return UNKNOWN;
        }
    }

    public static PartyKind fromCode (final String code) {
        return code != null && code.length() == 1 ? fromCode(code.toUpperCase().charAt(0)) : UNKNOWN;
    }
}
