package com.tchepannou.pdr.domain;

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
        } else {
            return PUBLIC;
        }
    }

    public static Privacy fromText (final String text) {
        try {
            if (text == null) {
                return PUBLIC;
            } else {
                return text != null && text.length() == 1
                        ? fromCode(text.toUpperCase().charAt(0))
                        : Enum.valueOf(Privacy.class, text.toUpperCase());
            }
        } catch (IllegalArgumentException e) {  // NOSONAR
            return PUBLIC;
        }
    }
}
