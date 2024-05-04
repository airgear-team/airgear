package com.airgear.aspect;

import com.airgear.service.Logging;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
@AllArgsConstructor
public class LoggingAspect {

    private final Logging logging;

    @Pointcut(value = "execution(* com.airgear.controller.*Controller.*(..))")
    public void notificationPointcut() {
    }

    @Before(value = "notificationPointcut()")
    public void executeLoggingBefore() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        logging.log(request);
    }

    @After(value = "notificationPointcut()")
    public void executeLoggingAfter() {
        HttpServletResponse response =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        logging.log(response);
    }
}
