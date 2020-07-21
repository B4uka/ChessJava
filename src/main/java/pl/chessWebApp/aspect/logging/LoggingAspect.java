package pl.chessWebApp.aspect.logging;


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

    // setup logger
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    // add @Before advice
    @Before("pl.chessWebApp.aspect.AopExpressions.forAppFlow()")
    public void before(JoinPoint joinPoint) {

        // display method that we are calling
        LOGGER.info("@Before: calling method: " + joinPoint.getSignature().toShortString());

        // display the arguments to the method
        Object[] args = joinPoint.getArgs();

        for (Object tempArgs : args) {
            LOGGER.info("----->: " + tempArgs);
        }
    }

    // add @AfterReturn advice
    @AfterReturning(pointcut = "pl.chessWebApp.aspect.AopExpressions.forAppFlow()", returning = "result")
    public void afterReturn(JoinPoint joinPoint, Object result) {

        // display method that we are returning
        LOGGER.info("@AfterReturning: from method: " + joinPoint.getSignature().toShortString());

        // display data returned
        LOGGER.info("----->: " + result);
    }
}
