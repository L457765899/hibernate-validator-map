package com.sxb.lin.hibernate.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class MapCheckValidator extends AbstractConstraintValidator<Map<String, Object>> {

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    public boolean isValid(Map<String, Object> value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        return this.doValid(value, context);
    }

    @Override
    protected DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return defaultListableBeanFactory;
    }

}
