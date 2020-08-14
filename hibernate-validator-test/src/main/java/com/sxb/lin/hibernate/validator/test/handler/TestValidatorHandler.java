package com.sxb.lin.hibernate.validator.test.handler;

import com.sxb.lin.hibernate.validator.annotation.MapCheck;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Service
@Validated
public class TestValidatorHandler {

    public void validMap(@MapCheck.List({
            @MapCheck(key = "id", type = Integer.class, notNull = @NotNull(message="id不能为空")),
            @MapCheck(key = "name", type = String.class, notBlank = @NotBlank(message="name不能为空")),
            @MapCheck(key = "level", type = Integer.class, min = @Min(value=100, message="等级必须大于等于100")),
            @MapCheck(key = "number", type = Integer.class)
        }) Map<String, Object> param) {
        System.out.println("验证成功:" + param);
    }
}
