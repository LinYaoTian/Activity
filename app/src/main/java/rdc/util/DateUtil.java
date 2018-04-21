package rdc.util;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by asus on 18-4-17.
 */

public class DateUtil {
    /**
     * 日期的解析方法，判断当前日期是哪一个昨天今天还是明天
     * @param createTime
     * @return
     */
    public static String parseDate(String createTime) {
        try {
            String ret = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long create = sdf.parse(createTime).getTime();
            Calendar now = Calendar.getInstance();
            long ms = 1000 * (now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));//毫秒数
            long ms_now = now.getTimeInMillis();
            if (ms_now - create < ms) {
                ret = "今天";
            } else if (ms_now - create < (ms + 24 * 3600 * 1000)) {
                ret = "昨天";
            } else if (ms_now - create < (ms + 24 * 3600 * 1000 * 2)) {
                ret = "前天";
            } else {
                ret = "更早";
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更根据日期获取当前月份
     * @param time
     * @return
     */

    public static String getMonth(DateTime time) {
        String month = "";
        switch (time.toString().substring(5, 7)) {
            case "01":
                month= "1月";
            break;
            case "02":
                month=  "2月";
            break;
            case "03":
                month=  "3月";
            break;
            case "04":
                month=  "4月";
            break;
            case "05":
                month= "5月";
            break;
            case "06":
                month=  "6月";
            break;
            case "07":
                month=  "7月";
            break;
            case "08":
                month=  "8月";
            break;
            case "09":
                month=  "9月";
            break;
            case "10":
                month=  "10月";
            break;
            case "11":
                month=  "11月";
            break;
            case "12":
                month=  "12月";
            break;


        }
        return month;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date).substring(5, 10);
    }
}
