package com.yupi.yuzan.aop;

import com.yupi.yuzan.annatation.AuthCheck;
import com.yupi.yuzan.common.ErrorCode;
import com.yupi.yuzan.exception.BusinessException;
import com.yupi.yuzan.model.domain.User;
import com.yupi.yuzan.model.enums.UserEnum;
import com.yupi.yuzan.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class AuthInterceptor {

    @Resource
    private UserService userService;

    @Around("@annotation(authCheck)")
    @SneakyThrows
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) {
        // 获取请求头中的 token
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        // 校验
        User loginUser = userService.getLoginUser(request);
        UserEnum enumByValue = UserEnum.getEnumByValue(mustRole);
        if (enumByValue == null) {
            return joinPoint.proceed();
        }

        UserEnum userEnum = UserEnum.getEnumByValue(loginUser.getUserRole());
        if (userEnum == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        if (!UserEnum.ADMIN.equals(userEnum)||!UserEnum.ADMIN.equals(enumByValue)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return joinPoint.proceed();
    }
}
