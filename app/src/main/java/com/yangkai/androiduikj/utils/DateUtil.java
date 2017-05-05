package com.yangkai.androiduikj.utils;

import android.text.TextUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 日期表现形式转换、日期或时间偏移的计算等处理工具类
 * @author gzs
 *
 */
public class DateUtil {

    public static final String YMD = "yyyyMMdd";
    public static final String YMD_SLASH = "yyyy/MM/dd";
    public static final String YMD_DASH = "yyyy-MM-dd";
    public static final String YMD_DASH_WITH_TIME = "yyyy-MM-dd H:m";
    public static final String YDM_SLASH = "yyyy/dd/MM";
    public static final String YDM_DASH = "yyyy-dd-MM";
    public static final String HM = "HHmm";
    public static final String HM_COLON = "HH:mm";
    public static final int MINUTE = 60 * 1000;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;

    private static final Map<String, DateFormat> DFS = new HashMap<>();

    private DateUtil() {
    }

    /**
     * 获取当前时间字符串，格式为："yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String getCurrDateStr() {
        return getCurrDateStr(null);
    }

    /**
     * 返回指定格式的当前时间字符串，如果format为空或者为null则使用默认格式："yyyy-MM-dd HH:mm:ss"
     *
     * @Throws 如果传入的格式不支持则抛出 IllegalArgumentException 异常
     * @param format
     * @return
     */
    public static String getCurrDateStr(String format) {
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return format(new Date(), format);
    }

    /**
     * 根据字符串获取对应的DateFormat实例
     *
     * @param pattern
     * @return
     */
    public static DateFormat getFormat(String pattern, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        DateFormat format = DFS.get(pattern);
        if (format == null) {
            format = new SimpleDateFormat(pattern, locale);
            DFS.put(pattern, format);
        }
        return format;
    }

    /**
     * 将时间日期字符串转换为Date实例
     *
     * @param source
     *        时间日期字符串
     * @param pattern
     *        格式
     * @return
     * @throws ParseException
     */
    public static Date parse(String source, String pattern) {
        if (source == null) {
            return null;
        }
        return parse(source, pattern, null);
    }

    /**
     * 将时间日期字符串转换为Date实例
     *
     * @param source
     *        时间日期字符串
     * @param pattern
     *        格式
     * @return
     * @throws ParseException
     */
    public static Date parse(String source, String pattern, Locale locale) {
        if (source == null) {
            return null;
        }
        try {
            return getFormat(pattern, locale).parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Date实例转换为时间日期字符串
     *
     * @param date
     *        时间日期
     * @param pattern
     *        格式
     * @return
     * @throws ParseException
     */
    public static String format(Date date, String pattern, Locale locale) {
        if (date == null) {
            return null;
        }
        return getFormat(pattern, locale).format(date);
    }

    /**
     * 将Date实例转换为时间日期字符串
     *
     * @param date
     *        时间日期
     * @param pattern
     *        格式
     * @return
     * @throws ParseException
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return getFormat(pattern, null).format(date);
    }

    /**
     * @param year
     *        年
     * @param month
     *        月(1-12)
     * @param day
     *        日(1-31)
     * @return 输入的年、月、日是否是有效日期
     */
    public static boolean isValid(int year, int month, int day) {
        if (month > 0 && month < 13 && day > 0 && day < 32) {
            // month of calendar is 0-based
            int mon = month - 1;
            Calendar calendar = new GregorianCalendar(year, mon, day);
            if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.DAY_OF_MONTH) == day) {
                return true;
            }
        }
        return false;
    }

    /**
     * 用于将日期转换成Calendar实例
     *
     * @param date
     * @return
     */
    public static Calendar convert(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 返回指定年数位移后的日期
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date yearOffset(Date date, int offset) {
        return offsetDate(date, Calendar.YEAR, offset);
    }

    /**
     * 返回指定月数位移后的日期
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date monthOffset(Date date, int offset) {
        return offsetDate(date, Calendar.MONTH, offset);
    }

    /**
     * 返回指定天数位移后的日期
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date dayOffset(Date date, int offset) {
        return offsetDate(date, Calendar.DATE, offset);
    }

    /**
     * 返回指定日期相应位移后的日期
     *
     * @param date
     *        参考日期
     * @param field
     *        位移单位，见 {@link Calendar}
     * @param offset
     *        位移数量，正数表示之后的时间，负数表示之前的时间
     * @return 位移后的日期
     */
    public static Date offsetDate(Date date, int field, int offset) {
        Calendar calendar = convert(date);
        calendar.add(field, offset);
        return calendar.getTime();
    }

    /**
     * 返回当月第一天的日期
     *
     * @param date
     * @return
     */
    public static Date firstDay(Date date) {
        Calendar calendar = convert(date);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 返回当月最后一天的日期
     *
     * @param date
     * @return
     */
    public static Date lastDay(Date date) {
        Calendar calendar = convert(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * 返回两个日期间的差异天数
     *
     * @param date1
     *        参照日期
     * @param date2
     *        比较日期
     * @return 参照日期与比较日期之间的天数差异，正数表示参照日期在比较日期之后，0表示两个日期同天，负数表示参照日期在比较日期之前
     */
    public static int dayDiff(Date date1, Date date2) {
        long diff = getDateZero(date1).getTime() - getDateZero(date2).getTime();
        return (int) (diff / DAY);
    }

    /**
     * 判断给定的两个Date实例是否在同一天内
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Date d = parse(format(date1, "yyyy-MM-dd"), "yyyy-MM-dd");
        return d != null && dayDiff(d, date2) == 0;
    }

    /**
     * 返回指定日期对应的0时0分0秒Date对象
     *
     * @param date
     * @return
     */
    public static Date getDateZero(Date date) {
        Calendar calendar = convert(date);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String timeFormat(long timeMillis, String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }

    public static String formatPhotoDate(long time){
        return timeFormat(time, "yyyy-MM-dd");
    }

    public static String formatPhotoDate(String path){
        File file = new File(path);
        if(file.exists()){
            long time = file.lastModified();
            return formatPhotoDate(time);
        }
        return "1970-01-01";
    }
}
