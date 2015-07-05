package com.tchepannou.pdr.enums;

public enum Privacy {
    PUBLIC('P'), HIDDEN('H');

    private char code;

    Privacy (char code) {
        this.code = code;
    }

    public char getCode (){
        return code;
    }

    public static Privacy fromCode (final char code) {
        Character xcode = Character.toUpperCase(code);
        if (HIDDEN.code == xcode) {
            return HIDDEN;
        } else if (PUBLIC.code == xcode ){
            return PUBLIC;
        } else{
            return null;
        }
    }

    public static Privacy fromText (final String text) {
        try {
            if (text == null) {
                return null;
            } else {
                return text != null && text.length() == 1
                        ? fromCode(text.toUpperCase().charAt(0))
                        : Enum.valueOf(Privacy.class, text.toUpperCase());
            }
        } catch (IllegalArgumentException e) {  // NOSONAR
            return null;
        }
    }
}
