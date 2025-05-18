package com.yupi.yuzan.interceptor;

import com.yupi.yuzan.utils.TraceIdUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(urlPatterns = "/**", filterName = "TraceFilter")
@Component
@Slf4j
public class TraceFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(TraceFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = UUID.randomUUID().toString().replace("-", "");
        TraceIdUtil.setTraceId(traceId);
        logger.info("请求start: {}",request.getRequestURI().toString());
        long st = System.currentTimeMillis();
        try {
            filterChain.doFilter(request,response);
        } finally {
            long et = System.currentTimeMillis();
            logger.info("请求end: {},耗时: {}ms",request.getRequestURI().toString(),et-st);
            TraceIdUtil.removeTraceId();
        }
    }
}
