package com.sxb.lin.hibernate.validator.test.dto;

import com.sxb.lin.hibernate.validator.dto.AbstractParamDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ParamDto<T> extends AbstractParamDto<T> {

    /**
     * 登陆用户的id
     */
    @NotEmpty(message = "userId不能为空")
    @Length(min = 4, message = "userId长度必须大于4")
    private String userId;

    /**
     * 登陆用户的token
     */
    @NotBlank(message = "token不能为空")
    private String token;
}
