package com.sxb.lin.hibernate.validator.custom;

import com.sxb.lin.hibernate.validator.AbstractCustomValidator;
import com.sxb.lin.hibernate.validator.dto.ValidResult;

import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class NotUrlValidator extends AbstractCustomValidator<Annotation, String> {
    @Override
    public ValidResult isValid(String value, ConstraintValidatorContext context, Object parent, String key) {
        if(value == null) {
            return new ValidResult(true, null);
        }
        if(value.startsWith("http")) {
            return new ValidResult(false, key + "必须是相对路径，不能以http开头");
        }
        return new ValidResult(true, null);
    }
}
