package com.sxb.lin.hibernate.validator.custom;

import com.sxb.lin.hibernate.validator.AbstractCustomValidator;
import com.sxb.lin.hibernate.validator.dto.ValidResult;

import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class HttpUrlValidator extends AbstractCustomValidator<Annotation, String> {
    @Override
    public ValidResult isValid(String value, ConstraintValidatorContext context, Object parent, String key) {
        if(value == null) {
            return new ValidResult(true, null);
        }
        if(value.startsWith("http")) {
            return new ValidResult(true, null);
        }
        return new ValidResult(false, key + "必须是以http开头的url");
    }
}
