package com.sxb.lin.hibernate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Past;

public class PastValidatorForLong implements ConstraintValidator<Past, Long> {

    public void initialize(Past constraintAnnotation) {
        
    }

    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        
        return value < System.currentTimeMillis();
    }

}
