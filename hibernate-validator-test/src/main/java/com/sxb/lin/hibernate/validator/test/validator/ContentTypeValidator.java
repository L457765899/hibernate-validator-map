package com.sxb.lin.hibernate.validator.test.validator;

import com.sxb.lin.hibernate.validator.AbstractCustomValidator;
import com.sxb.lin.hibernate.validator.dto.ValidResult;
import com.sxb.lin.hibernate.validator.test.dto.AdminDto;
import com.sxb.lin.hibernate.validator.test.other.ContentTypeCheck;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Map;

public class ContentTypeValidator extends AbstractCustomValidator<Annotation, String> {

    @Autowired
    private ContentTypeCheck contentTypeCheck;

    @Override
    public ValidResult isValid(String value, ConstraintValidatorContext context, Object parent, String key) {

        //为空的情况交由NotNull验证，但是这里也可以验证
        if(value == null) {
            return new ValidResult(true, null);
        }

        Integer type = null;
        if(parent instanceof Map) {
            Map<?, ?> param = (Map<?, ?>) parent;
            type = (Integer) param.get("type");
        }else if(parent instanceof AdminDto) {
            AdminDto param = (AdminDto) parent;
            type = param.getType();
        }

        if(contentTypeCheck.check(type, value)){
            return new ValidResult(true, null);
        }

        return new ValidResult(false, "content与type定义的类型不符");
    }
}
