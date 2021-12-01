package pl.chessWebApp.log.aop.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1) // without priority order, logs are showing in a random way (if they have same priority)
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    // add @Before advice
    @Before("pl.chessWebApp.log.aop.pointcutExpression.AopExpressions.forAppFlow()")
    public void before(JoinPoint joinPoint) {
        LOGGER.info("@Before: calling method: " + joinPoint.getSignature().toShortString());
        Object[] args = joinPoint.getArgs();
        for (Object tempArgs : args) {
            LOGGER.info("----->: " + tempArgs);
        }
    }

    // add @AfterReturn advice
    @AfterReturning(pointcut = "pl.chessWebApp.log.aop.pointcutExpression.AopExpressions.forAppFlow()", returning = "result")
    public void afterReturn(JoinPoint joinPoint, Object result) {
        LOGGER.info("@AfterReturning: from method: " + joinPoint.getSignature().toShortString());
        LOGGER.info("----->: " + result);
    }
}
