package com.sxb.lin.hibernate.validator;

import com.sxb.lin.hibernate.validator.dto.ValidResult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public abstract class AbstractCustomValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    public abstract ValidResult isValid(T value, ConstraintValidatorContext context, Object parent, String key);

    public boolean isValid(T value, ConstraintValidatorContext context) {
        ValidResult valid = this.isValid(value, context, null, null);
        return valid.isValid();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass() ;
    }
}
