package alex.hlo.springboot.test.aspect.impl;

import alex.hlo.springboot.test.aspect.CheckId;
import alex.hlo.springboot.test.entity.common.BaseEntity;
import alex.hlo.springboot.test.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class CheckIdImpl {

    @Around("@annotation(alex.hlo.springboot.test.aspect.CheckId)")
    public Object check(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] methodArgs = joinPoint.getArgs();

        CheckId annotation = getCheckIdAnnotation(joinPoint);
        Class<? extends BaseEntity> entityClass = annotation.entityClass();

        if (Objects.nonNull(methodArgs) && methodArgs.length > 0) {
            for (Object arg : methodArgs) {
                if (isExistStrValue(arg)) {
                    throw new ApiException(entityClass.getName() + ".id cannot be empty!");
                }
            }
        } else {
            log.warn("Annotation \"@CheckId\" at {} is no need!", joinPoint.getSignature());
        }

        return joinPoint.proceed();
    }

    private static boolean isExistStrValue(Object arg) {
        return Objects.isNull(arg) || (arg instanceof String
                && (((String) arg).isEmpty() || ((String) arg).isBlank())
        );
    }

    private CheckId getCheckIdAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        return method.getAnnotation(CheckId.class);
    }
}
