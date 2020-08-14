package com.sxb.lin.hibernate.validator.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

public class ValidatorUtil {
    
    /**
     * hibernate-validator 验证一个对象
     * @param obj 验证的对象
     * @return 验证不通过的提示信息
     */
    public static String validateModel(Object obj) {
        Validator validator = (Validator) Validation.buildDefaultValidatorFactory().getValidator();  
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);//验证某个对象,，其实也可以只验证其中的某一个属性的  
        Set<ConstraintViolation<?>> cvs = new HashSet<ConstraintViolation<?>>();
        cvs.addAll(constraintViolations);
        return getMessage(cvs);
    }
    
    /**
     * hibernate-validator 验证一个对象
     * @param obj
     * @return 验证通过返回true，验证不通过返回false
     */
    public static boolean validate(Object obj){
        Validator validator = (Validator) Validation.buildDefaultValidatorFactory().getValidator();  
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
        if(constraintViolations.size() > 0){
            return false;
        }else{
            return true;
        }
    }

    public static String getMessage(Set<ConstraintViolation<?>> constraintViolations) {
        StringBuilder builder = new StringBuilder();
        if(constraintViolations == null){
            return builder.toString();
        }
        
        int i = 0, len = constraintViolations.size();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            if (i == len - 1) {
                builder.append(constraintViolation.getMessage());
            } else {
                builder.append(constraintViolation.getMessage()).append(",");
            }
            i++;
        }
        return builder.toString();
    }
}
