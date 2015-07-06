package com.tchepannou.pdr.enums;

@Deprecated
public enum UserStatus {
    CREATED('C'), ACTIVE('A'), SUSPENDED('S');

    private char code;

    UserStatus(char code) {
        this.code = code;
    }

    public char getCode (){
        return code;
    }

    public static UserStatus fromCode (final char code) {
        Character xcode = Character.toUpperCase(code);
        if (SUSPENDED.code == xcode) {
            return SUSPENDED;
        } else if (ACTIVE.code == xcode) {
            return ACTIVE;
        } else if (CREATED.code == xcode) {
            return CREATED;
        } else{
            return null;
        }
    }

    public static UserStatus fromText (final String text) {
        try {
            if (text == null) {
                return null;
            } else {
                return text != null && text.length() == 1
                        ? fromCode(text.toUpperCase().charAt(0))
                        : Enum.valueOf(UserStatus.class, text.toUpperCase());
            }
        } catch (IllegalArgumentException e) {  // NOSONAR
            return null;
        }
    }
}
