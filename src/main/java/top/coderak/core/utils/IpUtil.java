package top.coderak.core.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取客户端ip地址
 */
public class IpUtil {

    public static String getServerIpAddr(HttpServletRequest request) {

        String SERVER_IP = null;

        SERVER_IP = request.getHeader("X-Forwarded-For");

        if (StringUtils.isNotEmpty(SERVER_IP) && !"unKnown".equalsIgnoreCase(SERVER_IP)) {

            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = SERVER_IP.indexOf(",");

            if (index != -1) {

                SERVER_IP.substring(0, index);
            }
        }
        if (StringUtils.isNotEmpty(SERVER_IP) && !"unKnown".equalsIgnoreCase(SERVER_IP)) {

//			SERVER_IP = SERVER_IP;
        }

        SERVER_IP = request.getRemoteAddr();

        return SERVER_IP;
    }
}
