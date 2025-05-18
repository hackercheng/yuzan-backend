package com.yupi.yuzan.aop;

import com.yupi.yuzan.common.BaseResponse;
import com.yupi.yuzan.utils.TraceIdUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
public class ResultTraceIdAspect {

    @Pointcut("execution(* com.yupi..*Controller.*(..)) || " +
            "execution(* com.yupi.yuzan.exception.GlobalExceptionHandler.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object aroud(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        if (proceed instanceof BaseResponse) {
            ((BaseResponse<?>) proceed).setTraceId(TraceIdUtil.getTraceId());
        }
        return proceed;
    }
}
