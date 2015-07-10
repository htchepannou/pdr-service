package com.tchepannou.pds.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumContraintValidator implements ConstraintValidator<Enum, String> {
    private Enum annotation;

    @Override
    public void initialize(final Enum annotation)
    {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
        if (value == null){
            return true;
        }

        final boolean result = false;
        final Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if(enumValues != null) {
            for(Object enumValue : enumValues) {
                final String str = enumValue.toString();
                if(
                        (this.annotation.ignoreCase() && str.equalsIgnoreCase(value)) ||
                        str.equals(value)
                ) {
                    return true;
                }
            }
        }

        return result;
    }
}
