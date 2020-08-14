package com.sxb.lin.hibernate.validator.custom;

import com.sxb.lin.hibernate.validator.AbstractCustomValidator;
import com.sxb.lin.hibernate.validator.annotation.CustomCheck;
import com.sxb.lin.hibernate.validator.dto.ValidResult;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomCheckValidator implements ConstraintValidator<CustomCheck, Object> {

    private static final Logger logger = LoggerFactory.getLogger(CustomCheckValidator.class);

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    private CustomCheck customCheck;

    private Set<AbstractCustomValidator<? extends Annotation, ?>> customValidatorSet = new HashSet<AbstractCustomValidator<? extends Annotation, ?>>();

    @SuppressWarnings("unchecked")
    public void initialize(CustomCheck constraintAnnotation) {
        customCheck = constraintAnnotation;
        Class<? extends AbstractCustomValidator<? extends Annotation, ?>>[] customValidators = customCheck.customValidators();
        for(Class<? extends AbstractCustomValidator<? extends Annotation, ?>> customValidator : customValidators) {
            AbstractCustomValidator customValidatorBean = defaultListableBeanFactory.createBean(customValidator);
            customValidatorBean.initialize(customCheck);
            customValidatorSet.add(customValidatorBean);
        }
    }

    @SuppressWarnings("unchecked")
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValid = true;
        StringBuilder builder = new StringBuilder();
        Class<? extends AbstractCustomValidator<? extends Annotation, ?>>[] customValidators = customCheck.customValidators();
        for(AbstractCustomValidator customValidatorBean : customValidatorSet) {
            ConstraintValidatorContextImpl contextImpl = (ConstraintValidatorContextImpl) context;
            List<ConstraintViolationCreationContext> constraintViolationCreationContexts = contextImpl.getConstraintViolationCreationContexts();
            ConstraintViolationCreationContext creationContext = constraintViolationCreationContexts.get(constraintViolationCreationContexts.size() - 1);
            PathImpl path = creationContext.getPath();
            NodeImpl leafNode = path.getLeafNode();
            String key = leafNode.getName();
            NodeImpl parent = leafNode.getParent();
            //获取拥有被注解类的类
            Object parentValue = parent.getValue();
            ValidResult valid = customValidatorBean.isValid(value, context, parentValue, key);
            if(!valid.isValid()) {
                isValid = false;
                if(builder.length() > 0) builder.append(",");
                builder.append(valid.getMessage());
            }
        }

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(builder.toString()).addConstraintViolation();
        }
        return isValid;
    }
}
