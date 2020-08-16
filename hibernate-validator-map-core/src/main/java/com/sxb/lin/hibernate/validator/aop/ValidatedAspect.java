package com.sxb.lin.hibernate.validator.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ValidatedAspect {

    public static ThreadLocal<Map<String, Object>> LOCAL_VALID = new ThreadLocal<Map<String, Object>>();

    @Pointcut("@within(org.springframework.validation.annotation.Validated)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if(args.length == 0) {
            return;
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String[] parameterNames = methodSignature.getParameterNames();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        int length = parameterAnnotations.length;

        Map<String, Object> map = new HashMap<String, Object>();
        for(int i = 0; i < length; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            if(annotations.length > 0) {
                boolean contains = false;
                for(Annotation annotation : annotations) {
                    if(annotation.annotationType() == Valid.class) {
                        contains = true;
                    }
                }
                if(contains) {
                    String parameterName = parameterNames[i];
                    Object parameterValue = args[i];
                    map.put(parameterName, parameterValue);
                }
            }
        }

        if(map.size() > 0) {
            LOCAL_VALID.set(map);
        }
    }

    @After("pointcut()")
    public void doAfter() {
        LOCAL_VALID.remove();
    }
}
