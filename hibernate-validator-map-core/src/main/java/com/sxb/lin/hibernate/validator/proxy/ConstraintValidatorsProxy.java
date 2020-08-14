package com.sxb.lin.hibernate.validator.proxy;

import com.sxb.lin.hibernate.validator.dto.ValidResult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ConstraintValidatorsProxy extends ConstraintValidatorProxy{
    
    private List<ConstraintValidator> validators;

    @Override
    @SuppressWarnings("unchecked")
    public ValidResult isValid(Object value, ConstraintValidatorContext context, Map<String, Object> targetMap, String key) {
        for(ConstraintValidator validator : validators){
            if(!validator.isValid(value, context)){
                return new ValidResult(false, this.getMessage());
            }
        }
        return new ValidResult(true, null);
    }

    public List<ConstraintValidator> getValidators() {
        return validators;
    }

    public void setValidators(List<ConstraintValidator> validators) {
        this.validators = validators;
    }

    public void addValidator(ConstraintValidator validator) {
        validators.add(validator);
    }
}
