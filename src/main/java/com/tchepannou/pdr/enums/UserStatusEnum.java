package com.tchepannou.pdr.enums;

@Deprecated
public enum UserStatusEnum {
    CREATED('C'), ACTIVE('A'), SUSPENDED('S');

    private char code;

    UserStatusEnum(char code) {
        this.code = code;
    }

    public char getCode (){
        return code;
    }

    public static UserStatusEnum fromCode (final char code) {
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

    public static UserStatusEnum fromText (final String text) {
        try {
            if (text == null) {
                return null;
            } else {
                return text != null && text.length() == 1
                        ? fromCode(text.toUpperCase().charAt(0))
                        : Enum.valueOf(UserStatusEnum.class, text.toUpperCase());
            }
        } catch (IllegalArgumentException e) {  // NOSONAR
            return null;
        }
    }
}
