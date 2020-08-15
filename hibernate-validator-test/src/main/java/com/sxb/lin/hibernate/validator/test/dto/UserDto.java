package com.sxb.lin.hibernate.validator.test.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
public class UserDto {

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id必须大于0")
    private Integer id;

    @NotBlank(message = "name不能为空")
    private String name;

    @Past(message = "date必须是过去时间")
    private Date date;

    @Future(message = "time必须是将来时间")
    private Date time;

}
