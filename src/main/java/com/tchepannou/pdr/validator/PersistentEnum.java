package com.tchepannou.pdr.validator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumContraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PersistentEnum {

    String message() default "Invalid value. This is not permitted.";

    Class<? extends com.tchepannou.pdr.domain.PersistentEnum> enumClass();

    boolean ignoreCase() default true;
}
