package ru.kazimir.bortnik.jb.databaseservice.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMeasurementAspect {

    private final Logger logger = LogManager.getLogger(PerformanceMeasurementAspect.class);

    @Pointcut("execution(* ru.kazimir.bortnik.jb.databaseservice..*.*(..))")
    public void callAtEveryMethod() {
    }

    @Around("callAtEveryMethod()")
    public Object shellOverMethod(ProceedingJoinPoint thisJoinPoint) throws Throwable {

        long methodStartTime = System.currentTimeMillis();
        Object result = thisJoinPoint.proceed();
        long methodEndTime = System.currentTimeMillis();

        long executionTime = methodEndTime - methodStartTime;
        logger.info(String.format("%s was performed %d millis.", thisJoinPoint.getSignature(), executionTime));
        return result;
    }

}
