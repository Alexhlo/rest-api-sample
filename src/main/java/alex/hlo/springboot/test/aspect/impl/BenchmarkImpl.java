package alex.hlo.springboot.test.aspect.impl;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class BenchmarkImpl {

    @Around("@annotation(alex.hlo.springboot.test.aspect.Benchmark)")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = (System.currentTimeMillis() - start) / 1000;

        log.info("{} executed in {} seconds", joinPoint.getSignature(), executionTime);

        return proceed;
    }
}
