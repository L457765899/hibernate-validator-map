package com.sxb.lin.hibernate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Future;

public class FutureValidatorForLong implements ConstraintValidator<Future, Long> {

    public void initialize(Future constraintAnnotation) {
       
    }

    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if ( value == null ) {
            return true;
        }
        return value > System.currentTimeMillis();
    }

}
