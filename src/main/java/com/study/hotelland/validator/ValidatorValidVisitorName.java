package com.study.hotelland.validator;

import com.study.hotelland.service.VisitorService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class ValidatorValidVisitorName implements ConstraintValidator<ValidVisitorName, String> {

    @Autowired
    private VisitorService visitorService;

    @Override
    public boolean isValid(String visitorName, ConstraintValidatorContext constraintValidatorContext) {

        if (visitorName == null) {
            return false;
        }

        boolean isVisitorNameExists = visitorService.existsByName(visitorName);

        return !isVisitorNameExists;
    }
}
