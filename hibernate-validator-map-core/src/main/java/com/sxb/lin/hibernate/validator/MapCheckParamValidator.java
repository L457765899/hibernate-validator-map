package com.sxb.lin.hibernate.validator;

import com.sxb.lin.hibernate.validator.dto.AbstractParamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class MapCheckParamValidator extends AbstractConstraintValidator<AbstractParamDto<Map<String, Object>>> {

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    public boolean isValid(AbstractParamDto<Map<String, Object>> value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        if(value.getParam() == null){
            return false;
        }
        return this.doValid(value.getParam(), context);
    }

    @Override
    protected DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return defaultListableBeanFactory;
    }
}
