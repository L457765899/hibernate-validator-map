package com.sxb.lin.hibernate.validator.dto;

import javax.validation.Valid;
import java.io.Serializable;

public abstract class AbstractParamDto<T> implements Serializable {

    /**
     * 请求参数
     */
    @Valid
    private T param;

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}
