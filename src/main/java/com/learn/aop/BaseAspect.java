package com.learn.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @program: spring_template_1
 * @author: yjf
 * @create: 2019-03-22 22:23
 **/
@Component
@Aspect
public class BaseAspect {

    @Pointcut("execution(* com.learn.platform..*.*(..))")
    protected void declearJoinPointExpression(){}

    @Around(value = "declearJoinPointExpression()")
    public Object aroundMethod(ProceedingJoinPoint point){
        System.out.println("aspect is before -------");
        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        System.out.println("aspect is after -------");
        return proceed ;
    }

}
