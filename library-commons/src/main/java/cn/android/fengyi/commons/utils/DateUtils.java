package cn.android.fengyi.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yinhang on 2018/11/11
 */
public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_MONTH_YEAR_DAY = "yyyy年MM月dd日";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_FORMAT_NO_SECOND = "yyyy-MM-dd HH:mm";

    public static final String TIME_FORMAT_NO_SECOND = "HH:mm";

    public static final SimpleDateFormat dateFormatMonthYearDay =
            new SimpleDateFormat(DATE_FORMAT_MONTH_YEAR_DAY, Locale.CHINA);

    public static final SimpleDateFormat dateFormat =
            new SimpleDateFormat(DATE_FORMAT, Locale.CHINA);

    public static final SimpleDateFormat datetimeFormat =
            new SimpleDateFormat(DATETIME_FORMAT, Locale.CHINA);

    public static final SimpleDateFormat datetimeNoSecondFormat =
            new SimpleDateFormat(DATETIME_FORMAT_NO_SECOND, Locale.CHINA);

    public static final SimpleDateFormat timeNoSecondFormat =
            new SimpleDateFormat(TIME_FORMAT_NO_SECOND, Locale.CHINA);

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String formatDatetime(Date date) {
        return datetimeFormat.format(date);
    }

    public static String formatDatetimeNoSecond(Date date) {
        return datetimeNoSecondFormat.format(date);
    }

    public static String formatDatetimeNoSecond(Date date, SimpleDateFormat format) {
        return format.format(date);
    }

    public static String formatTimeNoSecond(Date date) {
        return timeNoSecondFormat.format(date);
    }

    public static String getDate2String(Date date, DateFormat format) {
        return format.format(date);
    }

    /**
     * @param dateString 2018-11-07 13:42:03,
     * @return 1541569323000
     */
    public static long getString2Date(String dateString, DateFormat format) {

        Date date = new Date();
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * @param dateString 2018-11-07 13:42:03,
     * @return 1541569323000
     */
    public static long getString2Date(String dateString) {

        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 判断日期是否处于同一天
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDate(cal1, cal2);
    }

    /**
     * 重载
     */
    public static boolean isSameDate(Calendar cal1, Calendar cal2) {

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }
}
