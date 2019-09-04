package top.coderak.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 转换工具类
 *
 * @author zyh
 * @date 2019/7/21 0021
 */
public class ConvertUtil {

    /**
     * 将日期对象转换为字符表达式
     *
     * @param pattern   日期格式
     * @param dateValue 日期对象
     * @return 字符表达式
     */
    public static final String convertDateToString(String pattern, Date dateValue) {

        // 声明字符表达式
        String expression = null;

        // 如果日期对象不为空
        if (dateValue != null) {

            // 将日期对象转换为字符表达式
            expression = new SimpleDateFormat(pattern).format(dateValue);
        }

        // 返回字符表达式
        return expression;
    }

    /**
     * 将字符表达式转换为日期对象
     *
     * @param pattern    日期格式
     * @param expression 字符表达式
     * @return 日期对象
     * @throws ParseException
     */
    public static Date convertStringToDate(String pattern, String expression) throws ParseException {

        // 声明日期对象
        Date dateValue = null;

        // 如果字符表达式不为空
        if (StringUtils.isNotBlank(expression)) {

            // 将字符表达式转换为日期对象
            dateValue = new SimpleDateFormat(pattern).parse(expression);
        }

        // 返回日期对象
        return dateValue;
    }

    /**
     * 将字符表转换为Integer
     *
     * @param str 字符表数字
     * @return 默认值-1
     */
    public static Integer convertStringToInteger(String str) {

        // 返回整数对象
        return Integer.parseInt(str);
    }

    /**
     * 将字符表转换为BigDecimal
     *
     * @param str 字符表数字
     */
    public static BigDecimal convertStringToDecimal(String str) {

        BigDecimal decimal = new BigDecimal(str);

        // 返回decimal对象
        return decimal;
    }

    /**
     * 时间戳转换成日期格式
     */
    public static Date convertTimeStampToDate(String timeStamp) {

        // 转换为日期格式字符串
        return new Date(Long.valueOf(timeStamp + "000"));
    }

    /**
     * 日期转换成时间戳
     * date 字符串日期
     * format 如：yyyy-MM-dd HH:mm:ss
     *
     * @return
     * @throws ParseException
     */
    public static long convertDateToTimeStamp(Date dateValue) throws ParseException {

        // 转换为时间戳
        return dateValue.getTime();
    }

    /**
     * 日期格式字符串转换成时间戳
     * date 字符串日期
     *
     * @return
     * @throws ParseException
     */
    public static long convertDateStringToTimeStamp(String pattern, String dateString) throws ParseException {

        Date date = convertStringToDate(pattern, dateString);

        // 转换为时间戳
        return date.getTime();
    }

    /**
     * 时间戳转换成日期格式字符串
     */
    public static String convertTimeStampToDateString(String pattern, String timeStamp) {

        Date date = convertTimeStampToDate(timeStamp);

        // 转换为时间戳
        return convertDateToString(pattern, date);
    }
}
