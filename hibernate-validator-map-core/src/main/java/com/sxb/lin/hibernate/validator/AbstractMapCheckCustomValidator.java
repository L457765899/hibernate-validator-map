package com.sxb.lin.hibernate.validator;

import com.sxb.lin.hibernate.validator.annotation.MapCheck;
import com.sxb.lin.hibernate.validator.dto.ValidResult;

import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public abstract class AbstractMapCheckCustomValidator<T> extends AbstractCustomValidator<MapCheck, T> {

    protected MapCheck annotation;

    public void initialize(MapCheck constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    protected abstract ValidResult isValid(T value, ConstraintValidatorContext context, Map<String, Object> parent, String key);

    @Override
    public ValidResult isValid(T value, ConstraintValidatorContext context, Object parent, String key) {
        @SuppressWarnings("unchecked") Map<String, Object> mapParent = (Map<String, Object>) parent;
        return this.isValid(value, context, mapParent, key);
    }
}
