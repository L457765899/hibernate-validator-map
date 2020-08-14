package com.sxb.lin.hibernate.validator.proxy;

import com.sxb.lin.hibernate.validator.dto.ValidResult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ConstraintValidatorSelectProxy extends ConstraintValidatorProxy{
    
    private Map<Class, ConstraintValidator> selectValidator = new HashMap<Class, ConstraintValidator>();

    @Override
    @SuppressWarnings("unchecked")
    public ValidResult isValid(Object value, ConstraintValidatorContext context, Map<String, Object> targetMap, String key) {
        if(value == null){
            return new ValidResult(true, null);
        }
        
        Class<?> clazz = value.getClass();
        Class<?> selectKey = getClass();
        if(CharSequence.class.isAssignableFrom(clazz)){
            selectKey = CharSequence.class;
        }else if(Number.class.isAssignableFrom(clazz)){
            selectKey = Number.class;
        }else if(Collection.class.isAssignableFrom(clazz)){
            selectKey = Collection.class;
        }else if(Map.class.isAssignableFrom(clazz)){
            selectKey = Map.class;
        }else if(clazz.isArray()){
            selectKey = Array.class;
        }
        
        ConstraintValidator validator = selectValidator.get(selectKey);
        if(validator != null){
            boolean valid = validator.isValid(value, context);
            return new ValidResult(valid, this.getMessage());
        }
        
        //throw new RuntimeException("no validator to support valid " + clazz);
        return new ValidResult(true, null);
    }

    public Map<Class, ConstraintValidator> getSelectValidator() {
        return selectValidator;
    }

    public void setSelectValidator(Map<Class, ConstraintValidator> selectValidator) {
        this.selectValidator = selectValidator;
    }

    /**
     * @param clazz support { CharSequence.class, Number.class, Collection.class, Map.class, Array.class }
     * @param validator
     */
    public void addValidator(Class clazz, ConstraintValidator validator) {
        selectValidator.put(clazz, validator);
    }
}
