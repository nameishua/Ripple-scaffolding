package top.coderak.core.base.controller;

import top.coderak.core.base.constants.BaseConstants;
import top.coderak.core.base.http.ResposeResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseController {

    /**
     * 生明request对象
     */
    @Autowired
    protected HttpServletRequest request;

    /**
     * 生明respose对象
     */
    @Autowired
    protected HttpServletResponse respose;

    /**
     * 获取参数
     *
     * @param paramName
     * @return
     */
    protected String getParam(String paramName) {
        String paramValue = this.request.getParameter(paramName);
        if (StringUtils.isEmpty(paramValue)) {
            paramValue = null;
        }
        return paramValue;
    }

    /**
     * 写回客户端消息
     *
     * @throws IOException
     */
    private void writeResult(Boolean isSuccess, Object resultValue) {

        try {

            this.respose.setCharacterEncoding(BaseConstants.RESPONSE_CHARACTER_ENCODING);

            this.respose.setContentType(BaseConstants.RESPONSE_CONTENT_TYPE);

            if (isSuccess) {

                String result = ResposeResult.getSucessResult(resultValue);

                this.respose.getWriter().write(result);
            } else {

                String value = (String) resultValue;

                String result = ResposeResult.getFailResult(value);

                this.respose.getWriter().write(result);
            }
        } catch (Exception e) {

//            LogHelper.getInstance().log(e.getMessage(), e);
        } finally {

            try {

                this.respose.getWriter().close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    /**
     * 成功消息
     *
     * @param resultValue 返回客户端的信息
     * @throws IOException
     */
    protected void writeSuccessResult(Object resultValue) {
        writeResult(true, resultValue);
    }

    /**
     * 成功消息
     *
     * @throws IOException
     */
    protected void writeSuccessResult() {

        writeResult(true, null);
    }

    /**
     * 失败消息
     *
     * @param errorMsg 返回客户端的异常信息
     * @throws IOException
     */
    protected void writeFailResult(String errorMsg) {

        writeResult(false, errorMsg);
    }
}
