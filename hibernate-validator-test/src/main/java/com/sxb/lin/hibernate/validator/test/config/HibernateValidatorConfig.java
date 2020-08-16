package com.sxb.lin.hibernate.validator.test.config;

import com.sxb.lin.hibernate.validator.aop.ValidatedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateValidatorConfig {

    @Bean
    public ValidatedAspect validatedAspect() {
        return new ValidatedAspect();
    }
}
