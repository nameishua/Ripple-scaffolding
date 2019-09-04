package top.coderak.core.base.annotation;

import top.coderak.core.utils.ObjectUtil;
import top.coderak.core.utils.ObjectUtil;

import java.lang.reflect.Method;

public class InjectorProcessor {

    public void process(final Object object) {

        Class<? extends Object> class1 = object.getClass();

        // 找到类里所有变量Method
        Method[] methods = class1.getDeclaredMethods();

        // 遍历Method数组
        for (Method method : methods) {

            if (ObjectUtil.isNotNullOrEmpty(method)) {

                System.out.println(method);

                // 找到相应的拥有PrintOutAno注解的Method
                PrintOutAno printOutAno = method.getAnnotation(PrintOutAno.class);

                System.out.println("执行。。。");
                System.out.println(printOutAno.Type());
                System.out.println(AnoEnum.getTypeString(printOutAno.Type()));
            } else {

                System.out.println("null。。。");
            }

        }

    }

}