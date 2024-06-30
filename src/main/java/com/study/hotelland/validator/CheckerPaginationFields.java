package com.study.hotelland.validator;

import com.study.hotelland.web.dto.filter.Pagination;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;


public class CheckerPaginationFields implements ConstraintValidator<CheckPaginationFields, Pagination> {


    @Override
    public boolean isValid(Pagination pagination, ConstraintValidatorContext constraintValidatorContext) {

        if (ObjectUtils.anyNull(pagination.getPageNumber(), pagination.getPageSize())) {
            return false;
        }

        return true;
    }
}
