package com.sxb.lin.hibernate.validator.test.handler;

import com.google.gson.Gson;
import com.sxb.lin.hibernate.validator.annotation.MapCheck;
import com.sxb.lin.hibernate.validator.custom.HttpUrlValidator;
import com.sxb.lin.hibernate.validator.custom.JsonArrayValidator;
import com.sxb.lin.hibernate.validator.test.dto.AdminDto;
import com.sxb.lin.hibernate.validator.test.dto.ParamDto;
import com.sxb.lin.hibernate.validator.test.dto.UserDto;
import com.sxb.lin.hibernate.validator.test.validator.ContentTypeValidator;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Service
@Validated //必须在类上指定@Validated注解，如果不指定方法上即使有@MapCheck,@Valid验证也不会生效
public class TestValidatorHandler {

    public void validMap(@MapCheck.List({
            @MapCheck(key = "id", type = Integer.class, notNull = @NotNull(message="id不能为空")),
            @MapCheck(key = "name", type = String.class, notBlank = @NotBlank(message="name不能为空")),
            @MapCheck(key = "level", notNull = @NotNull(message="level不能为空"),
                    min = @Min(value=100, message="等级必须大于等于100")),
            @MapCheck(key = "number", type = Integer.class)
        }) Map<String, Object> param) {
        System.out.println("validMap验证成功:" + new Gson().toJson(param));
    }

    public void validParamMap(@MapCheck.List({
            @MapCheck(key = "id", type = Integer.class, notNull = @NotNull(message="id不能为空")),
            @MapCheck(key = "name", type = String.class, notBlank = @NotBlank(message="name不能为空")),
            @MapCheck(key = "level", notNull = @NotNull(message="level不能为空"),
                    min = @Min(value=100, message="等级必须大于等于100")),
            @MapCheck(key = "number", type = Integer.class)
        }) @Valid ParamDto<Map<String, Object>> paramDto) {
        //不加@Valid只会验证paramDto.param对应的map对象
        //加@Valid注解会验证paramDto.param对应的map对象还会验证paramDto.userId,paramDto.token
        System.out.println("validParamMap验证成功:" + new Gson().toJson(paramDto));
    }

    public void validObject(@Valid UserDto userDto) {
        //参数对象必须加上@Valid注解，不然验证不会生效
        System.out.println("validObject验证成功:" + new Gson().toJson(userDto));
    }

    public void validParamObject(@Valid ParamDto<UserDto> paramDto) {
        //对象必须加上@Valid注解，不然验证不会生效
        System.out.println("validParamObject验证成功:" + new Gson().toJson(paramDto));
    }

    public void customValidMap(@MapCheck.List({
            @MapCheck(key = "id", type = Integer.class, notNull = @NotNull(message="id不能为空")),
            @MapCheck(key = "name", type = String.class, notBlank = @NotBlank(message="name不能为空")),
            @MapCheck(key = "url", notBlank = @NotBlank(message = "url不能为空"),
                    customValidators = HttpUrlValidator.class),
            @MapCheck(key = "json", customValidators = JsonArrayValidator.class),
            @MapCheck(key = "type", type = Integer.class, notNull = @NotNull(message = "type不能为空")),
            @MapCheck(key = "content", type = String.class, notNull = @NotNull(message = "content不能为空"),
                    customValidators = ContentTypeValidator.class)
        }) Map<String, Object> param) {
        //以下是验证代码，与业务代码分离
        //HttpUrlValidator，JsonArrayValidator，ContentTypeValidator是自定义验证器
        //自定义验证器，内部可注入spring管理的对象
        //url验证必须是以http开头
        //json自定义验证，验证是否是json数组
        //content的内容是否和type定义的类型一致，比如type=1 content=订单编号，type=1 content=订单ID
        System.out.println("customValidMap验证成功:" + new Gson().toJson(param));
    }


    public void customValidParamMap(@MapCheck.List({
            @MapCheck(key = "id", type = Integer.class, notNull = @NotNull(message="id不能为空")),
            @MapCheck(key = "name", type = String.class, notBlank = @NotBlank(message="name不能为空")),
            @MapCheck(key = "url", notBlank = @NotBlank(message = "url不能为空"),
                    customValidators = HttpUrlValidator.class),
            @MapCheck(key = "json", customValidators = JsonArrayValidator.class),
            @MapCheck(key = "type", type = Integer.class, notNull = @NotNull(message = "type不能为空")),
            @MapCheck(key = "content", type = String.class, notBlank = @NotBlank(message = "content不能为空"),
                    customValidators = ContentTypeValidator.class)
        }) ParamDto<Map<String, Object>> paramDto) {
        //与上一个代码类似
        //不加@Valid只会验证paramDto.param对应的map对象，不会验证paramDto.userId,paramDto.token
        System.out.println("customValidMap验证成功:" + new Gson().toJson(paramDto));
    }

    public void customValidObject(@Valid AdminDto adminDto) {
        //参数对象必须加上@Valid注解，不然验证不会生效
        //对象使用@CustomCheck，必须开启aop，注入ValidatedAspect
        System.out.println("customValidObject验证成功:" + new Gson().toJson(adminDto));
    }

    public void customValidParamObject(@Valid ParamDto<AdminDto> paramDto) {
        //参数对象必须加上@Valid注解，不然验证不会生效
        //对象使用@CustomCheck，必须开启aop，注入ValidatedAspect
        System.out.println("customValidParamObject验证成功:" + new Gson().toJson(paramDto));
    }
}
