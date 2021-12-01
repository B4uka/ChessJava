package pl.chessWebApp.log.aop.pointcutExpression;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopExpressions {

    @Pointcut("execution(* pl.chessWebApp.security.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution(* pl.chessWebApp.security.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("within(pl.chessWebApp.security.dao.*)") //
    private void forDaoPackage() {
    }

    @Pointcut("execution(public void pl.chessWebApp.security.service.UserService.save(pl.chessWebApp.security.user.CrmUser))")
    public void forSavingNewAccount() {
    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    public void forAppFlow() {
    }
}
