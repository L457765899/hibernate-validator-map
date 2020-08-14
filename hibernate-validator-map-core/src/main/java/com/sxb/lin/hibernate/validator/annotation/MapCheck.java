package com.sxb.lin.hibernate.validator.annotation;

import com.sxb.lin.hibernate.validator.AbstractCustomValidator;
import com.sxb.lin.hibernate.validator.MapCheckParamValidator;
import com.sxb.lin.hibernate.validator.MapCheckValidator;
import org.hibernate.validator.constraints.*;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 实现map的校验，以及自定义验证的逻辑，实现数据验证和业务逻辑的分离
 */
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MapCheckValidator.class, MapCheckParamValidator.class})
public @interface MapCheck {

    String message() default "参数验证不通过";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    String key();
    
    Class<?> type() default Object.class;
    
    Class<? extends AbstractCustomValidator<? extends Annotation, ?>>[] customValidators() default {};

    //validation-api annotation
    NotNull[] notNull() default {};//不能为null

    NotBlank[] notBlank() default {};//不能为null，并且除去尾部的空白符以后，不能为空符串

    NotEmpty[] notEmpty() default {};//不能为null，并且不能为空字符串，空集合，空数组

    Null[] nuLL() default {};
    
    Size[] size() default {};//验证字符串，数组，集合长度在某个区间内
    
    Max[] max() default {};//验证数字大小在某个区间内，字符串数字也可以
    
    Min[] min() default {};//验证数字大小在某个区间内，字符串数字也可以
    
    AssertFalse[] assertFalse() default {};//必须为false
    
    AssertTrue[] assertTrue() default {};//必须为true
    
    DecimalMax[] decimalMax() default {};//验证数字大小在某个区间内，字符串数字也可以
    
    DecimalMin[] decimalMin() default {};//验证数字大小在某个区间内，字符串数字也可以
    
    Pattern[] pattern() default {};
    
    Digits[] digits() default {};
    
    Past[] past() default {};
    
    Future[] future() default {};

    Email[] email() default {};
    
    //hibernate-validator annotation
    Range[] range() default {};
    
    CreditCardNumber[] creditCardNumber() default {};
    
    Length[] length() default {};
    
    URL[] url() default {};
    
    LuhnCheck[] luhnCheck() default {};
    
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        MapCheck[] value();
    }
}
