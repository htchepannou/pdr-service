package com.tchepannou.pds.enums;

public enum PartyKind {
    PERSON('P'),
    ORGANIZATION('O');

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
        } else {
            return null;
        }
    }

    public static PartyKind fromText (final String text) {
        try {
            if (text == null) {
                return null;
            } else {
                return text != null && text.length() == 1
                        ? fromCode(text.toUpperCase().charAt(0))
                        : Enum.valueOf(PartyKind.class, text.toUpperCase());
            }
        } catch (IllegalArgumentException e) {  // NOSONAR
            return null;
        }
    }
}
