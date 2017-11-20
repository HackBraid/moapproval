package com.longfor.handle;

import com.longfor.bean.Result;
import com.longfor.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by issuser on 2017/9/29.
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger= LoggerFactory.getLogger(ExceptionHandle.class);


//    public Result handle(Exception e){
//        logger.info(e.getMessage());
//        MethodArgumentNotValidException c = (MethodArgumentNotValidException) e;
//        List<ObjectError> errors =c.getBindingResult().getAllErrors();
//        StringBuffer errorMsg=new StringBuffer();
//        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
//        return ResultUtil.error(100,errorMsg.toString());
//    }
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public void MethodArgumentNotValidException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        MethodArgumentNotValidException c = (MethodArgumentNotValidException) ex;
        List<ObjectError> errors =c.getBindingResult().getAllErrors();
        StringBuffer errorMsg=new StringBuffer();
        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
        pouplateExceptionResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, errorMsg.toString());
    }

    private void pouplateExceptionResponse(HttpServletResponse response, HttpStatus errorCode, String errorMessage) {
        try {
            response.sendError(errorCode.value(), errorMessage);
        } catch (IOException e) {
            logger.error("failed to populate response error", e);
        }
    }

}
