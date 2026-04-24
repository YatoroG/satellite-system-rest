package sys.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Around("@annotation (LogExecutionTine)")
    public Object LogExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long elapsedTime = System.currentTimeMillis() - start;
        System.out.printf("Метод %s выполнен за %d мс%п",
                joinPoint.getSignature().toShortString(), elapsedTime);

        return result;
    }
}
