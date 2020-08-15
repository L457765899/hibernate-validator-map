package com.sxb.lin.hibernate.validator.test.other;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ContentTypeCheck {

    public boolean check(Integer type, String content) {
        if(type != null) {
            if(type == 1) {
                return Pattern.matches("[a-zA-Z]+", content);
            }else if(type == 2) {
                return Pattern.matches("[0-9]+", content);
            }
        }
        return false;
    }

}
