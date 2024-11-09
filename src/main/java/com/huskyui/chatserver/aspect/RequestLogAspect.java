package com.huskyui.chatserver.aspect;

import com.huskyui.chatserver.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RequestLogAspect {

    @Pointcut("@annotation(com.huskyui.chatserver.annotation.RequestLog)")
    public void webLog() {
    }


    @Around("webLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        Object result = null;
        try {
            result = joinPoint.proceed();
            return result;
        } finally {
            log.info("className {} methodName {}  args {} result {}",
                    signature.getDeclaringTypeName(),
                    signature.getName(),
                    JsonUtils.objectToJson(args),
                    JsonUtils.objectToJson(result)
            );
        }


    }


}
