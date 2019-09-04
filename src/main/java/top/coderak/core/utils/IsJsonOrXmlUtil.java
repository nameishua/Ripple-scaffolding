package top.coderak.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * 校验json
 *
 * @author zyh
 */
public class IsJsonOrXmlUtil {

    /**
     * 判断字符串是否可以转化为json对象
     *
     * @param string
     * @return
     */
    public static boolean isJsonObject(String string) {

        try {

            @SuppressWarnings("unused")
            JSONObject jsonStr = JSONObject.parseObject(string);

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * 判断字符串是否可以转化为JSON数组
     *
     * @param content
     * @return
     */
    public static boolean isJsonArray(String content) {

        if (ObjectUtil.isNullOrEmpty(content)) {

            return false;
        } else {
            try {

                @SuppressWarnings("unused")
                JSONArray jsonStr = JSONArray.parseArray(content);

                return true;
            } catch (Exception e) {

                return false;
            }
        }

    }

    /**
     * 判断是否是xml结构
     *
     * @param value
     * @return
     */
    public static boolean isXML(String value) {

        try {

            DocumentHelper.parseText(value);
        } catch (DocumentException e) {

            return false;
        }

        return true;
    }

}
