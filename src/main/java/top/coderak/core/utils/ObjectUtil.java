package top.coderak.core.utils;

import java.util.Collection;
import java.util.Map;

public class ObjectUtil {
    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object obj) {

        if (obj == null) {

            return true;
        }

        if (obj instanceof CharSequence) {

            return ((CharSequence) obj).length() == 0;
        }

        if (obj instanceof Collection) {

            return ((Collection) obj).isEmpty();
        }

        if (obj instanceof Map) {

            return ((Map) obj).isEmpty();
        }

        if (obj instanceof Object[]) {

            Object[] object = (Object[]) obj;

            if (object.length == 0) {

                return true;
            }

            boolean empty = true;

            for (int i = 0; i < object.length; i++) {

                if (!isNullOrEmpty(object[i])) {

                    empty = false;

                    break;
                }

            }

            return empty;
        }

        return false;
    }

    /**
     * 判断对象或对象数组中每一个对象是否不为空
     *
     * @param obj
     * @return boolean
     * @author zyh
     * @date 2019/7/21 0021
     */
    public static boolean isNotNullOrEmpty(Object obj) {

        return isNullOrEmpty(obj) == false ? true : false;
    }
}
