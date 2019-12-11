package com.lizq.cache.web.commons.config;

import com.lizq.cache.commons.exception.ServiceException;
import com.lizq.cache.web.commons.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public JsonResult<Object> serviceException(ServiceException e) {
        log.error("业务异常: " + e.getMessage(), e);
        return JsonResult.failure().msg(e.getMessage());
    }

    /**
     * 上传附件大小超过阈值
     */
    @ExceptionHandler(MultipartException.class)
    public JsonResult<Object> multipartException(MultipartException e) {
        log.error("业务异常: " + e.getMessage(), e);
        return JsonResult.failure().msg("上传的附件出错");
    }

    /**
     * 数据库异常
     */
    @ExceptionHandler(DataAccessException.class)
    public JsonResult<Object> dataAccessException(DataAccessException e) {
        log.error("业务异常: " + e.getMessage(), e);
        return JsonResult.failure().msg("内部错误，服务器异常");
    }

    @ExceptionHandler(TypeMismatchException.class)
    public JsonResult<Object> typeMismatchException(TypeMismatchException e) {
        log.error("发生错误: " + e.getMessage(), e);
        String message = e.getCause().getCause().getMessage();
        if (StringUtils.isBlank(message)) {
            message = "传递的值有误";
        }
        return JsonResult.failure().msg(message);
    }

    @ExceptionHandler(BindException.class)
    public JsonResult<Object> bindException(BindException e) {
        log.error("发生错误: " + e.getMessage(), e);
        List<String> msgList = new ArrayList<>();
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        if (!CollectionUtils.isEmpty(errors)) {
            for (FieldError error : errors) {
                String msg = error.getDefaultMessage();
                if (StringUtils.isNotBlank(msg)) {
                    String property = isRelease() ? "" : (error.getField() + " : ");
                    msgList.add(property + msg.substring(msg.lastIndexOf(":") + 1).trim());
                }
            }
        }
        if (CollectionUtils.isEmpty(msgList)) {
            msgList.add("传递的值有误");
        }
        return JsonResult.failure().msg(StringUtils.join(msgList, ","));
    }

    /**
     * 请求没有相应的处理
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public JsonResult<Object> noHandlerFoundException(NoHandlerFoundException e) {
        log.error("发生错误: " + e.getMessage(), e);
        return JsonResult.failure().msg("404");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JsonResult<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("方法不支持: " + e.getMessage(), e);
        String msg = "不支持此种请求方式!";
        if (!isRelease()) {
            msg += String.format(" 当前方式(%s), 支持方式(%s)", e.getMethod(), StringUtils.join(e.getSupportedMethods(), ","));
        }
        return JsonResult.failure().msg(msg);
    }

    //添加全局异常处理流程，根据需要设置需要处理的异常，MethodArgumentNotValidException
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResult<Object> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String msg = "失败";
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult != null && bindingResult.hasErrors()) {
            msg = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }
        return JsonResult.failure().msg(msg);
    }

    /**
     * 未知的所有其他异常
     */
    @ExceptionHandler(Exception.class)
    public JsonResult<Object> exception(Exception e) {
        log.error("发生错误: " + e.getMessage(), e);
        return JsonResult.failure().msg("内部错误，服务器异常");
    }

    /**
     * 未知的所有其他异常
     */
    @ExceptionHandler(Throwable.class)
    public JsonResult<Object> throwable(Throwable e) {
        log.error("发生错误: " + e.getMessage(), e);
        return JsonResult.failure().msg("内部错误，服务器异常");
    }


    private boolean isRelease() {
        return false;
    }
}
