package com.yupi.yuzan.aop;

import cn.hutool.json.JSONUtil;
import com.yupi.yuzan.annatation.NoLog;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("execution(* com.yupi..*Controller.*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        logger.info("方法开始执行：{}", signature.getDeclaringTypeName() + "." + signature.getName());
        Object result = null;
        try {
            // 获取所有的入参，打印到日志中
            Map<String, Object> params = new LinkedHashMap<>();
            String[] parameterNames = signature.getParameterNames();
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                if (parameterIsLog(signature, i)) {
                    String parameterName = parameterNames[i];
                    Object arg = args[i];
                    params.put(parameterName,arg);
                }
            }
            logger.info("方法参数：{}", JSONUtil.toJsonStr(params));
            result = joinPoint.proceed();
            return result;
        } finally {
            if (this.resultIsLog(signature)) {
                logger.info("方法返回值：{}", JSONUtil.toJsonStr(result));
            }
        }
    }

    /**
     * 指定位置的参数是否需要打印出来？
     *
     * @param methodSignature
     * @param paramIndex
     * @return
     */
    private boolean parameterIsLog(MethodSignature methodSignature, int paramIndex) {
        if (methodSignature.getMethod().getParameterCount() == 0) {
            return false;
        }

        // 参数上有 @NoLog注解的不会打印
        Annotation[] parameterAnnotation = methodSignature.getMethod().getParameterAnnotations()[paramIndex];
        if (parameterAnnotation != null && parameterAnnotation.length > 0) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType() == NoLog.class) {
                    return false;
                }
            }
        }

        // 参数类型是下面这些类型的，也不会打印，比如：ServletRequest、ServletResponse
        Class parameterType = methodSignature.getParameterTypes()[paramIndex];
        for (Class<?> type : noLogTypes) {
            if (type.isAssignableFrom(parameterType)) {
                return false;
            }
        }
        return true;
    }

    // 参数类型是下面这些类型的，也不会打印，比如：ServletRequest、ServletResponse，大家可以扩展
    private static List<Class<?>> noLogTypes = Arrays.asList(ServletRequest.class, ServletResponse.class);

    /**
     * 判断方法的返回值是否需要打印？方法上有 @NoLog 注解的，表示结果不打印返回值
     *
     * @param methodSignature
     * @return
     */
    private boolean resultIsLog(MethodSignature methodSignature) {
        return methodSignature.getMethod().getAnnotation(NoLog.class) == null;
    }
}
