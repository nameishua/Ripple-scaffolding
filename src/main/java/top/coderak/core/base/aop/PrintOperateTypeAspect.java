package top.coderak.core.base.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.coderak.core.base.annotation.PrintOperateType;
import top.coderak.core.base.enums.PrintOperateTypeEnum;

import java.lang.reflect.Method;

/**
 * 自定义切入点Aspect
 *
 * @author zyh
 * @date 2019/9/9 0009
 * @return
 */
@Slf4j
@Aspect
@Component
public class PrintOperateTypeAspect {

    /**
     * @param point
     */
    @Around("@annotation(top.coderak.core.base.annotation.PrintOperateType)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();

        Method method = signature.getMethod();

        PrintOperateType printOperateType = method.getAnnotation(PrintOperateType.class);

        System.out.println(printOperateType.type());

        String value = PrintOperateTypeEnum.getTypeString(printOperateType.type());

        System.out.println(value);

        log.info(value);

        //执行方法
        Object object = point.proceed();

        return object;
    }
}
