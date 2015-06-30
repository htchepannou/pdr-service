package com.tchepannou.pdr.domain;

public enum PartyKind {
    PERSON('P'),
    ORGANIZATION('O'),
    HOUSEHOLD('H'),
    UNKNOWN('?');

    private char code;

    PartyKind (char code) {
        this.code = code;
    }

    public char getCode (){
        return code;
    }

    public static PartyKind fromCode (final char code) {
        Character xcode = Character.toUpperCase(code);
        if (PERSON.code == xcode) {
            return PERSON;
        } else if (ORGANIZATION.code == xcode) {
            return ORGANIZATION;
        } else if (HOUSEHOLD.code == xcode) {
            return HOUSEHOLD;
        } else {
            return UNKNOWN;
        }
    }

    public static PartyKind fromText (final String text) {
        try {
            if (text == null) {
                return UNKNOWN;
            } else {
                return text != null && text.length() == 1
                        ? fromCode(text.toUpperCase().charAt(0))
                        : Enum.valueOf(PartyKind.class, text.toUpperCase());
            }
        } catch (IllegalArgumentException e) {  // NOSONAR
            return UNKNOWN;
        }
    }
}
