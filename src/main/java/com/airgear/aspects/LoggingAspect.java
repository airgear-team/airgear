package com.airgear.aspects;

import com.airgear.service.Logging;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code LoggingAspect} for logging HTTP requests and responses in controllers.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Aspect
@Component
public class LoggingAspect {

    private final Logging logging;

    public LoggingAspect(Logging logging) {
        this.logging = logging;
    }

    /**
     * Pointcut definition for methods in controllers.
     */
    @Pointcut(value = "execution(* com.airgear.controller.*Controller.*(..))")
    public void notificationPointcut() {
    }

    /**
     * Advice to log information before the execution of methods in controllers.
     */
    @Before(value = "notificationPointcut()")
    public void executeLoggingBefore() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        logging.log(request);
    }

    /**
     * Advice to log information after the execution of methods in controllers.
     */
    @After(value = "notificationPointcut()")
    public void executeLoggingAfter() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        logging.log(response);
    }
}
