package com.sxb.lin.hibernate.validator.test.exception;

import com.sxb.lin.hibernate.validator.test.util.RetUtil;
import com.sxb.lin.hibernate.validator.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    /**
     * 参数验证异常处理，返回给客户端，服务端就不记录了
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Map<String, Object> handleConstraintViolationException(ConstraintViolationException e) {
        String message = ValidatorUtil.getMessage(e.getConstraintViolations());
        return RetUtil.getRetValue(false, message, RetUtil.ReturnCodeValue_ServerEorror);
    }

    /**
     * 所有错误异常处理，服务端记录到日志
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> handleException(Exception e, HttpServletRequest request) {
        String msg = "{url:" + request.getRequestURI() + "}";
        logger.error(msg + "系统异常：" + e.getMessage(), e);
        return RetUtil.getRetValue(false, "系统异常", RetUtil.ReturnCodeValue_ServerEorror);
    }

}
