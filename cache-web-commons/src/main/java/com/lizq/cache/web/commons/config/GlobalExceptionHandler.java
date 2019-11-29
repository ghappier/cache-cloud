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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public void serviceException(ServiceException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.error("业务异常: " + e.getMessage(), e);
        returnErrorJson(request, response, e.getMessage());
    }

    /**
     * 上传附件大小超过阈值
     */
    @ExceptionHandler(MultipartException.class)
    public void multipartException(MultipartException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.error("业务异常: " + e.getMessage(), e);
        returnErrorJson(request, response, "上传的附件出错");
    }

    /**
     * 数据库异常
     */
    @ExceptionHandler(DataAccessException.class)
    public void dataAccessException(DataAccessException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.error("业务异常: " + e.getMessage(), e);
        returnErrorJson(request, response, "内部错误，服务器异常");
    }

    @ExceptionHandler(TypeMismatchException.class)
    public void typeMismatchException(TypeMismatchException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.error("发生错误: " + e.getMessage(), e);
        String message = e.getCause().getCause().getMessage();
        if (StringUtils.isBlank(message)) {
            message = "传递的值有误";
        }
        returnErrorJson(request, response, message);
    }

    @ExceptionHandler(BindException.class)
    public void bindException(BindException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        returnErrorJson(request, response, StringUtils.join(msgList, ","));
    }

    /**
     * 请求没有相应的处理
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public void noHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.error("发生错误: " + e.getMessage(), e);
        returnErrorJson(request, response, "404");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
                                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.error("方法不支持: " + e.getMessage(), e);
        String msg = "不支持此种请求方式!";
        if (!isRelease()) {
            msg += String.format(" 当前方式(%s), 支持方式(%s)", e.getMethod(), StringUtils.join(e.getSupportedMethods(), ","));
        }
        returnErrorJson(request, response, msg);
    }

    //添加全局异常处理流程，根据需要设置需要处理的异常，MethodArgumentNotValidException
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public void methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg = "失败";
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult != null && bindingResult.hasErrors()) {
            msg = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }
        returnErrorJson(request, response, msg);
    }

    /**
     * 未知的所有其他异常
     */
    @ExceptionHandler(Exception.class)
    public void exception(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.error("发生错误: " + e.getMessage(), e);
        returnErrorJson(request, response, "内部错误，服务器异常");
    }

    /**
     * 未知的所有其他异常
     */
    @ExceptionHandler(Throwable.class)
    public void throwable(Throwable e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.error("发生错误: " + e.getMessage(), e);
        returnErrorJson(request, response, "内部错误，服务器异常");
    }

    private void returnErrorJson(HttpServletRequest request, HttpServletResponse response, String errMsg) throws Exception {
        JsonResult result = JsonResult.failure();
        result.setMsg(errMsg);
        result.toJson(request, response);
    }

    private boolean isRelease() {
        return false;
    }
}
