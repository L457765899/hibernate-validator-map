package com.sxb.lin.hibernate.validator.test.dto;

import com.sxb.lin.hibernate.validator.dto.AbstractParamDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ParamDto<T> extends AbstractParamDto<T> {

    /**
     * 登陆用户的id
     */
    @Min(value = 1, message = "用户ID必须大于0")
    private String userId;

    /**
     * 登陆用户的token
     */
    @NotEmpty
    private String token;
}
