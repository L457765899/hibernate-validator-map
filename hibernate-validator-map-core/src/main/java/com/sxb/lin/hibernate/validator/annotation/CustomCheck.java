package com.sxb.lin.hibernate.validator.annotation;

import com.sxb.lin.hibernate.validator.AbstractCustomValidator;
import com.sxb.lin.hibernate.validator.custom.CustomCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义验证的逻辑，实现数据验证和业务逻辑的分离，一般用于对象中
 */
@Target({ TYPE, FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CustomCheckValidator.class)
public @interface CustomCheck {

    String message() default "参数验证不通过";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends AbstractCustomValidator<? extends Annotation, ?>>[] customValidators();
}
