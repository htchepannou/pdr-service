package com.tchepannou.pds.enums;

public enum Gender {
    MALE('M'), FEMALE('F');

    private char code;

    Gender (char code) {
        this.code = code;
    }

    public char getCode (){
        return code;
    }

    public static Gender fromCode (final char code) {
        Character xcode = Character.toUpperCase(code);
        if (MALE.code == xcode) {
            return MALE;
        } else if (FEMALE.code == xcode) {
            return FEMALE;
        } else {
            return null;
        }
    }

    public static Gender fromText (final String text) {
        try {
            if (text == null) {
                return null;
            } else {
                return text != null && text.length() == 1
                        ? fromCode(text.toUpperCase().charAt(0))
                        : Enum.valueOf(Gender.class, text.toUpperCase());
            }
        } catch (IllegalArgumentException e) {  // NOSONAR
            return null;
        }
    }
}
