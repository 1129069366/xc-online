package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

public class CustomerException extends RuntimeException{

    //自定义异常类一般需要   异常状态码,异常描述信息

    private ResultCode resultCode;

    public CustomerException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
