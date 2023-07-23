package alex.hlo.springboot.test.aspect;

import alex.hlo.springboot.test.entity.common.BaseEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckId {

    Class<? extends BaseEntity> entityClass();
}
