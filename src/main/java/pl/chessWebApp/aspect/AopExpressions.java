package pl.chessWebApp.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopExpressions {

    // setup pointcut declarations  https://www.baeldung.com/spring-aop-pointcut-tutorial
    @Pointcut("execution(* pl.chessWebApp.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution(* pl.chessWebApp.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("within(pl.chessWebApp.dao.*)") //
    private void forDaoPackage() {
    }

    @Pointcut("execution(public void pl.chessWebApp.service.UserService.save(pl.chessWebApp.user.CrmUser))")
    public void forSavingNewAccount() {
    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    public void forAppFlow() {
    }
}
