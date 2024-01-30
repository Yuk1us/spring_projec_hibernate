package org.artyomnikitin.spring.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class Asspect {

    /**Where to use Aspects*/
    @Pointcut("execution(* org.artyomnikitin.spring.controller.*.*(..))")
    public void controllerMethods(){  }

    /**Just Prints Method name before and after calling*/
    @Around("controllerMethods()")
    public Object aroundlAllMethods(ProceedingJoinPoint point)throws Throwable{
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        System.out.println("Begin of method: "+ methodSignature.getName());
        Object obj = point.proceed();
        System.out.println("End of method :"+ methodSignature.getName());
        return obj;
    }

    /**After Occurring Exception Prints method+cause+message+additional info*/
   @AfterThrowing(pointcut="controllerMethods()",throwing = "ex")
    public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String stuff = signature.toString();
        String arguments = Arrays.toString(joinPoint.getArgs());
        System.out.println("ERROR: "+ ex.getClass()+ "\nIN METHOD: "+methodName+"\nADDITIONAL INFO: "+stuff+ arguments);
    }
}
