package top.coderak.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 将long字符串转换成格式时间
     *
     * @param time
     * @return Date
     */
    public static Date LongToString(String time) {

        Date date = new Date(Long.parseLong(time));

        return date;
    }

    /**
     * 字符串转换成时间
     *
     * @param time
     * @return Date
     * @throws ParseException
     */
    public static Date StringToDate(String time) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = formatter.parse(time);

        return date;
    }

    /**
     * 时间转换为时间戳(10位)
     *
     * @param time
     * @return
     */
    public static long dateToTimestamp(String time) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            Date date = simpleDateFormat.parse(time);

            long ts = date.getTime() / 1000;

            return ts;
        } catch (ParseException e) {

            return 0;
        }
    }
}
