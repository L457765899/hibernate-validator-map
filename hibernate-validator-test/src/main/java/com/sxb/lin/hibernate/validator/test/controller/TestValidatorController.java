package com.sxb.lin.hibernate.validator.test.controller;

import com.sxb.lin.hibernate.validator.test.dto.AdminDto;
import com.sxb.lin.hibernate.validator.test.dto.ParamDto;
import com.sxb.lin.hibernate.validator.test.dto.UserDto;
import com.sxb.lin.hibernate.validator.test.handler.TestValidatorHandler;
import com.sxb.lin.hibernate.validator.test.util.RetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("test/validator")
public class TestValidatorController {

    @Autowired
    private TestValidatorHandler testValidatorHandler;

    /**
     * 测试map
     * @param param
     * @return
     */
    @RequestMapping("/validMap.json")
    public Map<String,Object> validMap(@RequestBody Map<String, Object> param){
        testValidatorHandler.validMap(param);
        return RetUtil.getRetValue(true);
    }

    /**
     * 测试对象嵌套map
     * @param paramDto
     * @return
     */
    @RequestMapping("/validParamMap.json")
    public Map<String,Object> validParamMap(@RequestBody ParamDto<Map<String, Object>> paramDto){
        testValidatorHandler.validParamMap(paramDto);
        return RetUtil.getRetValue(true);
    }

    /**
     * 测试对象
     * @param userDto
     * @return
     */
    @RequestMapping("/validObject.json")
    public Map<String,Object> validObject(@RequestBody UserDto userDto){
        userDto.setDate(new Date());
        userDto.setTime(new Date());
        testValidatorHandler.validObject(userDto);
        return RetUtil.getRetValue(true);
    }

    /**
     * 测试嵌套对象
     * @param paramDto
     * @return
     */
    @RequestMapping("/validParamObject.json")
    public Map<String,Object> validParamObject(@RequestBody ParamDto<UserDto> paramDto){
        paramDto.getParam().setDate(new Date());
        paramDto.getParam().setTime(new Date());
        testValidatorHandler.validParamObject(paramDto);
        return RetUtil.getRetValue(true);
    }

    /**
     * 验证与业务分离，测试自定义验证器map
     * @param param
     * @return
     */
    @RequestMapping("/customValidMap.json")
    public Map<String,Object> customValidMap(@RequestBody Map<String, Object> param) {
        testValidatorHandler.customValidMap(param);
        return RetUtil.getRetValue(true);
    }

    /**
     * 验证与业务分离，测试自定义验证器嵌套map
     * @param paramDto
     * @return
     */
    @RequestMapping("/customValidParamMap.json")
    public Map<String,Object> customValidMap(@RequestBody ParamDto<Map<String, Object>> paramDto) {
        testValidatorHandler.customValidParamMap(paramDto);
        return RetUtil.getRetValue(true);
    }

    /**
     * 验证与业务分离，测试自定义验证器对象
     * @param adminDto
     * @return
     */
    @RequestMapping("/customValidObject.json")
    public Map<String,Object> customValidObject(@RequestBody AdminDto adminDto) {
        testValidatorHandler.customValidObject(adminDto);
        return RetUtil.getRetValue(true);
    }

    /**
     * 验证与业务分离，测试自定义验证器嵌套对象
     * @param paramDto
     * @return
     */
    @RequestMapping("/customValidParamObject.json")
    public Map<String,Object> customValidParamObject(@RequestBody ParamDto<AdminDto> paramDto) {
        testValidatorHandler.customValidParamObject(paramDto);
        return RetUtil.getRetValue(true);
    }
}
