package com.example.hw13_aop.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(public * com.example.hw13_aop.controllers..*(..)) || " +
            "execution(public * com.example.hw13_aop.services..*(..)) || " +
            "execution(public * com.example.hw13_aop.repositories..*(..))")
    public void logBefore() {
        System.out.println("DS.");
    }
}