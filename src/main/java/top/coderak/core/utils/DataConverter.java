package top.coderak.core.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * 数据转换器类
 *
 * @author dsw
 * @version 1.0 2015/07/05
 */
public class DataConverter {

    /**
     * 将对象转换为JSON数据
     *
     * @param object 需要转换的对象
     * @return JSON数据
     */
    public static String convertObject2Json(Object object) {

        // 声明JSON数据
        String jsonData = null;

        try {

            // 创建对象映射器
            ObjectMapper objectMapper = new ObjectMapper();
            // 将数据对象转换为JSON字符串
            jsonData = objectMapper.writeValueAsString(object);
        }
        // 发生异常
        catch (Exception e) {

            // 输出错误日志
//			LogHelper.getInstance().log(e.getMessage(), e);
        }

        // 返回JSON数据
        return jsonData;
    }

    /**
     * 将JSON数据转换为对象
     *
     * @param jsonData JSON数据
     * @param clazz    需要转换的对象类型
     * @return 转换完成的对象
     */
    public static Object convertJson2Object(String jsonData, Class<?> clazz) {

        // 声明对象
        Object object = null;

        // 如果JSON数据不为空
        if (StringUtils.isNotBlank(jsonData)) {

            try {

                // 创建象映射器
                ObjectMapper objectMapper = new ObjectMapper();
                // 读取JSON字符串
                object = objectMapper.readValue(jsonData, clazz);
            }
            // 发生异常
            catch (Exception e) {

                // 输出错误日志
//				LogHelper.getInstance().log(e.getMessage(), e);
            }
        }

        // 返回对象
        return object;
    }

    /**
     * 将JSON数据转换为对象
     *
     * @param jsonData JSON数据
     * @param clazz    需要转换的对象类型
     * @return 转换完成的对象
     */
    public static Object convertJson2List(String jsonData, Class<?> clazz) {

        // 声明对象
        Object object = null;

        // 如果JSON数据不为空
        if (StringUtils.isNotBlank(jsonData)) {

            try {
                // 创建象映射器
                ObjectMapper objectMapper = new ObjectMapper();

                // 得到转换类型
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);

                // 读取JSON字符串
                object = objectMapper.readValue(jsonData, javaType);
            }
            // 发生异常
            catch (Exception e) {
                // 输出错误日志
//				LogHelper.getInstance().log(e.getMessage(),e);
            }
        }
        // 返回对象
        return object;
    }
}
