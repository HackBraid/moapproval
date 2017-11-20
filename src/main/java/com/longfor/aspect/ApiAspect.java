package com.longfor.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by issuser on 2017/9/27.
 */
@Aspect
@Component
public class ApiAspect {

        private final static Logger logger= LoggerFactory.getLogger(ApiAspect.class);

        @Pointcut("execution(public * com.longfor.controller.FlowController.*(..))")
        public void log(){
        }

        @Before("log()")
        public void doBefore(){
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request= attributes.getRequest();
            logger.info("url={}",request.getRequestURL());
        }

        @After("log()")
        public void doAfter(){

        }


}
