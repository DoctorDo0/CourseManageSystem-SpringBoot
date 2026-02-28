package org.example.demo05.common;

import org.example.demo05.utils.AuditEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
public class AutoAuditAspect {
    @Pointcut("execution(* org.example.demo05.service.implement.*.*add*(..))" +
            " || execution(* org.example.demo05.service.implement.*.*update*(..))" +
            "|| execution(* org.example.demo05.service.implement.*.*delete*(..))" +
            "|| execution(* org.example.demo05.service.implement.*.*restore*(..))")
    public void pc() {
        //pass
    }

    @Around("pc()")
    public Object autoAudit(ProceedingJoinPoint p) throws Throwable {
        String method = p.getSignature().getName();//目标方法名
        Object[] args = p.getArgs();
        System.out.println("======== auto audit test ========");
        System.out.println("======== test start ========");
        System.out.println("method name: " + method);
        System.out.println("======== test ========");
        for (Object arg : args) {
            System.out.println("arg: " + arg.toString());
            System.out.println("======== test ========");
            if (arg instanceof AuditEntity ae) {
                ae.setUpdateBy(Global.currentUser());
                ae.setUpdateDate(LocalDateTime.now());
                System.out.println("auditEntity:updateBy: " + ae.getUpdateBy());
                System.out.println("======== test over ========");
                if (method.contains("add")) {
                    ae.setRegisterBy(Global.currentUser());
                    ae.setRegisterDate(LocalDateTime.now());
                }
            }
        }
        //调用目标方法
        return p.proceed();
    }

}
