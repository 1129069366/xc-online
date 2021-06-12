package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionCatch {


    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();

    static{
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCast.class);

    //遇到CustomerException类异常
    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public ResponseResult customException(CustomerException customerException){
        //记录日志
        LOGGER.error(customerException.getMessage());
        ResultCode resultCode = customerException.getResultCode();
        return new ResponseResult(resultCode);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult catchException(Exception e){

        //记录日志
        LOGGER.error("catch exception:{}",e.getMessage());
        if (EXCEPTIONS==null){
            EXCEPTIONS=builder.build();
        }
        ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        if(resultCode!=null){
            return new ResponseResult(resultCode);
        }


        return new ResponseResult(CommonCode.SERVER_ERROR);
    }
}
