package com.sxb.lin.hibernate.validator.test.controller;

import com.sxb.lin.hibernate.validator.test.handler.TestValidatorHandler;
import com.sxb.lin.hibernate.validator.test.util.RetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("test/validator")
public class TestValidatorController {

    @Autowired
    private TestValidatorHandler testValidatorHandler;

    @RequestMapping("/validMap.json")
    public Map<String,Object> validMap(@RequestBody Map<String, Object> param){
        testValidatorHandler.validMap(param);
        return RetUtil.getRetValue(true);
    }
}
