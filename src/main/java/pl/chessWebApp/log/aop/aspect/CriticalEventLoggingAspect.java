package pl.chessWebApp.log.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0) // Integer.MIN_VALUE will be most important one; up to Integer.MAX_VALUE
public class CriticalEventLoggingAspect {

    private static final Logger LOGGER_ERR_WARN = LoggerFactory.getLogger("LOGGER_ERR_WARN");

    // add @AfterReturn advice when someone successfully remove any account
    @AfterReturning("pl.chessWebApp.log.aop.pointcutExpression.AopExpressions.forSavingNewAccount()")
    public void afterDeletedAccount(JoinPoint joinPoint) {

        // display method that we are returning
        LOGGER_ERR_WARN.warn("@AfterReturning: Account was successfully created by method: " + joinPoint.getSignature().toShortString());
    }
}
