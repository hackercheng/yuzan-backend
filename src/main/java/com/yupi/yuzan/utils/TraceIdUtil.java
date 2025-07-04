package com.yupi.yuzan.utils;

import org.slf4j.MDC;

public class TraceIdUtil {
    public static final String TRACE_ID = "traceId";
    public static ThreadLocal<String> traceIdThreadLocal = new ThreadLocal<>();

    public static String getTraceId() {
       return traceIdThreadLocal.get();
    }

    public static void setTraceId(String traceId) {
        traceIdThreadLocal.set(traceId);
        MDC.put(TRACE_ID,traceId);
    }

    public static void removeTraceId() {
        traceIdThreadLocal.remove();
        MDC.remove(TRACE_ID);
    }
}
