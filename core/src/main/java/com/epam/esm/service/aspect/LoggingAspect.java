package com.epam.esm.service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for performing Log4j logging.
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(Loggable)")
    public void executeLogging() {
    }

    @Before(value = "executeLogging()")
    public void logMethodCallBefore(JoinPoint joinPoint) {
        if (LOGGER.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();

            sb.append("BEFORE - ").append(joinPoint).append(": [");
            Arrays.stream(joinPoint.getArgs()).sequential().forEach(arg -> sb.append(arg).append(", "));
            sb.delete(sb.length() - 2, sb.length()).append("]");

            LOGGER.debug(sb.toString(), sb);
        }
    }

    @AfterReturning(value = "executeLogging()", returning = "returnObject")
    public void logMethodCallAfterReturn(JoinPoint joinPoint, Object returnObject) {
        if (LOGGER.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();

            sb.append("RETURNING - ").append(joinPoint).append(": [").append(returnObject);
            sb.delete(sb.length() - 2, sb.length()).append("]");

            LOGGER.debug(sb.toString(), sb);
        }
    }

    @AfterThrowing(value = "executeLogging()", throwing = "ex")
    public void logAfterThrowingAllMethods(JoinPoint joinPoint, Throwable ex) throws Throwable {
        if (LOGGER.isErrorEnabled()) {
            StringBuilder sb = new StringBuilder();

            sb.append("ERROR - ").append("method name - [")
                    .append(joinPoint.getSignature()).append("] - error - ").append(ex);

            LOGGER.error(sb.toString(), sb);
        }
    }

}
