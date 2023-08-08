package com.teamproject.okowan.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Aspect
@Component
public class UserPatternAop {
    @Pointcut("execution(* com.teamproject.okowan.user.UserController.signup(..))")
    public void signup() {
    }

    @Pointcut("execution(* com.teamproject.okowan.user.UserController.updateProfile(..))")
    public void updateProfile() {
    }

    @Around("signup()||updateProfile()")
    public Object executePatternCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] argument = joinPoint.getArgs();
        BindingResult bindingResult = (BindingResult) argument[1];

        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                stringBuilder.append(error.getDefaultMessage());
            }
            String patternErrorMessage = stringBuilder.toString();
            throw new IllegalArgumentException(patternErrorMessage);
        }
        return joinPoint.proceed();
    }
}
