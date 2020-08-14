package com.sxb.lin.hibernate.validator.proxy;

import com.sxb.lin.hibernate.validator.AbstractCustomValidator;
import com.sxb.lin.hibernate.validator.dto.ValidResult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ConstraintValidatorProxy implements ConstraintValidator{
    
    private ConstraintValidator validator;
    
    private boolean isCustomValidator;

    private String message;

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        ValidResult valid = this.isValid(value, context, null, null);
        return valid.isValid();
    }

    public ValidResult isValid(Object value, ConstraintValidatorContext context, Map<String, Object> targetMap, String key) {
        if(isCustomValidator){
            AbstractCustomValidator customValidator = (AbstractCustomValidator) validator;
            return customValidator.isValid(value, context, targetMap, key);
        }
        boolean valid = validator.isValid(value, context);
        return new ValidResult(valid, message);
    }

    public ConstraintValidator getValidator() {
        return validator;
    }

    public void setValidator(ConstraintValidator validator) {
        this.validator = validator;
    }

    public boolean isCustomValidator() {
        return isCustomValidator;
    }

    public void setCustomValidator(boolean isCustomValidator) {
        this.isCustomValidator = isCustomValidator;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
