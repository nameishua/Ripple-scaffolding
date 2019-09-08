package top.coderak.core.base.annotation;

import top.coderak.core.base.enums.PrintOperateTypeEnum;

import java.lang.annotation.*;

/**
 * 自定义打印注解
 * <p>
 * Retention(RetentionPolicy.RUNTIME) 生命周期永远不会被丢弃
 * Target(ElementType.METHOD) 作用于方法上
 *
 * @author zyh
 * @date 2019/9/8 0008
 * @return
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrintOperateType {

    // 描述
    PrintOperateTypeEnum type() default PrintOperateTypeEnum.DEFAULT;

}