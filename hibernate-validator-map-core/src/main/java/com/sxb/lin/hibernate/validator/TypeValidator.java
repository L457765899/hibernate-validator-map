package com.sxb.lin.hibernate.validator;

import com.sxb.lin.hibernate.validator.dto.ValidResult;

import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * 这个验证器是转给map使用的，不能在CustomCheck的customValidators中定义
 */
public class TypeValidator extends AbstractMapCheckCustomValidator<Object> {

    @Override
    protected ValidResult isValid(Object value, ConstraintValidatorContext context, Map<String,Object> parent, String key) {
        if(value == null){
            return new ValidResult(true, null);
        }
        Class<?> type = annotation.type();
        if(type == Object.class){
            return new ValidResult(true, null);
        }
        boolean isValid = type.isAssignableFrom(value.getClass());
        if(!isValid) {
            return new ValidResult(false, key + "数据类型验证不通过");
        }
        return new ValidResult(true, null);
    }
}
