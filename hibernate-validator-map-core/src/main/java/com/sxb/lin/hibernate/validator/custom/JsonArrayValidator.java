package com.sxb.lin.hibernate.validator.custom;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sxb.lin.hibernate.validator.AbstractCustomValidator;
import com.sxb.lin.hibernate.validator.dto.ValidResult;

import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class JsonArrayValidator extends AbstractCustomValidator<Annotation, String> {

    @Override
    public ValidResult isValid(String value, ConstraintValidatorContext context, Object parent, String key) {
        if(value == null) {
            return new ValidResult(true, null);
        }
        String message = key + "的值不是一个json数组";
        try {
            JsonElement parse = new JsonParser().parse(value);
            boolean jsonArray = parse.isJsonArray();
            if(!jsonArray) {
                return new ValidResult(false, message);
            }
            return new ValidResult(true, null);
        } catch (Exception e) {
            return new ValidResult(false, message);
        }
    }
}
