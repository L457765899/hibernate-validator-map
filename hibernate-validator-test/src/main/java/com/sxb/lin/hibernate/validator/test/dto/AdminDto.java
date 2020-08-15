package com.sxb.lin.hibernate.validator.test.dto;

import com.sxb.lin.hibernate.validator.annotation.CustomCheck;
import com.sxb.lin.hibernate.validator.custom.HttpUrlValidator;
import com.sxb.lin.hibernate.validator.custom.JsonArrayValidator;
import com.sxb.lin.hibernate.validator.test.validator.ContentTypeValidator;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AdminDto {

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id必须大于0")
    private Integer id;

    @NotBlank(message = "url不能为空")
    @CustomCheck(customValidators = HttpUrlValidator.class)
    private String url;

    @CustomCheck(customValidators = JsonArrayValidator.class)
    private String json;

    @NotNull(message = "type不能为空")
    private Integer type;

    @NotBlank(message = "content不能为空")
    @CustomCheck(customValidators = ContentTypeValidator.class)
    private String content;
}
