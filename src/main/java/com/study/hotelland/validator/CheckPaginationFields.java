package com.study.hotelland.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = CheckerPaginationFields.class)
public @interface CheckPaginationFields {

    String message() default "Some of the pagination fields are not specified, check the fields pageNumber or pageSize";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
