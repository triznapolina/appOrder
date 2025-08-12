package org.example.apporders.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LoggingAspectAnnotationOrder {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspectAnnotationOrder.class);

    @Pointcut("execution(* org.example.apporders.services.impl.OrderServiceImpl.*(..))")
    public void orderServiceMethods() {}

    @Before("orderServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Before executing: " + joinPoint.getSignature().toShortString());
    }

    @After("orderServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("After executing: " + joinPoint.getSignature().toShortString());
    }

}


