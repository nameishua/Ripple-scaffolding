package top.coderak.core.base.annotation;

import java.lang.annotation.*;

/**
 * 自定义打印注解 Retention(RetentionPolicy.RUNTIME) 生命周期永远不会被丢弃
 * Target(ElementType.METHOD) 作用于方法上
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrintOutAno {

    // 描述
    AnoEnum Type() default AnoEnum.DEFAULT;

}