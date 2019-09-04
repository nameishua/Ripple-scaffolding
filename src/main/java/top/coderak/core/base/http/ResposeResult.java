package top.coderak.core.base.http;

import top.coderak.core.base.constants.BaseConstants;
import top.coderak.core.utils.DataConverter;
import top.coderak.core.base.constants.BaseConstants;

import java.util.HashMap;
import java.util.Map;


/**
 * 返回给客户端的结果类型
 *
 * @author wangyouwei
 */
public class ResposeResult {

    /**
     * 获取返回结果
     *
     * @param resultCode  结果码
     * @param resultValue 结果值
     * @return 结果
     */
    public static String getResposeResult(String resultCode, Object resultValue) {

        Map<String, Object> result = new HashMap<String, Object>();

        result.put(BaseConstants.RESULT_CODE_KEY, resultCode);

        if (resultValue != null) {

            result.put(BaseConstants.RESULT_VALUE_KEY, resultValue);
        }

        return DataConverter.convertObject2Json(result);
    }

    /**
     * 获取成功结果
     *
     * @param resultValue 结果值
     * @return 结果
     */
    public static String getSucessResult(Object resultValue) {

        return getResposeResult(BaseConstants.RESULT_CODE_SUCCESS, resultValue);
    }

    /**
     * 获取失败结果
     *
     * @param errorMsg 失败信息
     * @return 结果
     */
    public static String getFailResult(String errorMsg) {

        return getResposeResult(BaseConstants.RESULT_CODE_FAIL, errorMsg);
    }
}
