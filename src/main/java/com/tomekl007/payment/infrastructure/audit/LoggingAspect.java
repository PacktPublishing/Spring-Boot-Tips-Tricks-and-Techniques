package com.tomekl007.payment.infrastructure.audit;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
//@Aspect
public class LoggingAspect {
    static Logger log = Logger.getLogger(LoggingAspect.class.getName());


    @Before("execution(* com.tomekl007.payment.api.PaymentService.pay(..))")
    public void logPaymentRequest(JoinPoint joinPoint) {
        log.info("before payment request with arguments: " + Arrays.toString(joinPoint.getArgs()));
    }
}
