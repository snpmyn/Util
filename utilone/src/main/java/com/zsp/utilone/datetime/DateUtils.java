package com.zsp.utilone.datetime;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.zsp.utilone.BigDecimalToString;
import com.zsp.utilone.R;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import value.UtilOneMagic;

/**
 * Created on 2017/10/13.
 *
 * @author 郑少鹏
 * @desc DateUtils
 */
public class DateUtils {
    private static final String[] WEEKS = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     * 据指定格式获当前时
     *
     * @param format 格式
     * @return String
     */
    private static String getCurrentTime(String format) {
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 当前时（yyyy-MM）
     *
     * @return String
     */
    public static String getCurrentTimeYearMonth() {
        return getCurrentTime(DateFormatUtils.DATE_FORMAT_TWO);
    }

    /**
     * 当前时（yyyy-MM-dd HH:mm:ss）
     *
     * @return String
     */
    private static String getCurrentTime() {
        return getCurrentTime(DateFormatUtils.DATE_FORMAT_SIX);
    }

    /**
     * 指定格式当前时（为空格式为yyyy-mm-dd HH:mm:ss）
     *
     * @param format 格式
     * @return Date
     */
    private static Date getCurrentDate(String format) {
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        String stringDate = getCurrentTime(format);
        Date date = null;
        try {
            date = sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期转化（时分）
     *
     * @param date date
     * @return string
     */
    public static String dateConversionToHourMinute(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
        return format.format(date);
    }

    /**
     * 日期转化（分秒）
     *
     * @param date date
     * @return string
     */
    public static String dateConversionToMinuteSecond(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.US);
        return format.format(date);
    }

    /**
     * 日期转化（年月日）
     *
     * @param date date
     * @return string
     */
    public static String dateConversionToYearMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.US);
        return format.format(date);
    }

    /**
     * 日期转化（年月日）
     *
     * @param date date
     * @return string
     */
    public static String dateConversionToYearMonthDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return format.format(date);
    }

    /**
     * 日期转化（年月日 时分）
     *
     * @param date date
     * @return string
     */
    public static String dateConversionToYearMonthDateHourMinute(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        return format.format(date);
    }

    /**
     * 日期转化（年月日 时分秒）
     *
     * @param date date
     * @return string
     */
    public static String dateConversionToYearMonthDateHourMinuteSecond(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return format.format(date);
    }

    /**
     * 当前时（格式yyyy-MM-dd HH:mm:ss）
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        return getCurrentDate(DateFormatUtils.DATE_FORMAT_SIX);
    }

    /**
     * 获当前时（HH:mm）
     * calendar.get(Calendar.YEAR)
     * calendar.get(Calendar.MONTH)
     * calendar.get(Calendar.DAY_OF_MONTH)
     *
     * @return HashMap
     */
    public static String getCurrentHm() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }

    /**
     * 给指定日期加年份（空默当前时）
     *
     * @param year   年份 正加负减
     * @param date   空默当前时
     * @param format 默格式yyyy-MM-dd HH:mm:ss
     * @return String
     */
    private static String addYearToDate(int year, Date date, String format) {
        Calendar calender = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calender.add(Calendar.YEAR, year);
        return sdf.format(calender.getTime());
    }

    /**
     * 给指定日期加年份（空默当前时）
     *
     * @param year   年份 正加负减
     * @param date   空默当前时
     * @param format 默格式yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String addYearToDate(int year, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = stringToDate(date, format);
        }
        return addYearToDate(year, newDate, format);
    }

    /**
     * 给指定日期加月份 空默当前时
     *
     * @param month  增加月份 正加负减
     * @param date   指定时
     * @param format 指定格式（空默yyyy-mm-dd HH:mm:ss）
     * @return String
     */
    private static String addMothToDate(int month, Date date, String format) {
        Calendar calender = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calender.add(Calendar.MONTH, month);
        return sdf.format(calender.getTime());
    }

    /**
     * 给指定日期加月份 空默当前时
     *
     * @param month  增加月份 正加负减
     * @param date   指定时
     * @param format 指定格式（空默yyyy-mm-dd HH:mm:ss）
     * @return String
     */
    public static String addMothToDate(int month, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = stringToDate(date, format);
        }
        return addMothToDate(month, newDate, format);
    }

    /**
     * 给指定日期加天数（空默当前时）
     *
     * @param day    加天数 正加负减
     * @param date   指定日期
     * @param format 日期格式（空默yyyy-mm-dd HH:mm:ss）
     * @return String
     */
    private static String addDayToDate(int day, Date date, String format) {
        Calendar calendar = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calendar.add(Calendar.DATE, day);
        return sdf.format(calendar.getTime());
    }

    /**
     * 给指定日期加天数（空默当前时）
     *
     * @param day    加天数 正加负减
     * @param date   指定日期
     * @param format 日期格式（空默yyyy-mm-dd HH:mm:ss）
     * @return String
     */
    public static String addDayToDate(int day, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = stringToDate(date, format);
        }
        return addDayToDate(day, newDate, format);
    }

    /**
     * 给指定日期加小时（空默当前时）
     *
     * @param hour   加小时 正加负减
     * @param date   指定日期
     * @param format 日期格式（空默yyyy-mm-dd HH:mm:ss）
     * @return String
     */
    private static String addHourToDate(int hour, Date date, String format) {
        Calendar calendar = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calendar.add(Calendar.HOUR, hour);
        return sdf.format(calendar.getTime());
    }

    /**
     * 给指定日期加小时（空默当前时）
     *
     * @param hour   加小时 正加负减
     * @param date   指定日期
     * @param format 日期格式（空默yyyy-mm-dd HH:mm:ss）
     * @return String
     */
    public static String addHourToDate(int hour, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = stringToDate(date, format);
        }
        return addHourToDate(hour, newDate, format);
    }

    /**
     * 给指定日期加分钟（空默当前时）
     *
     * @param minute 加分钟 正加负减
     * @param date   指定日期
     * @param format 日期格式（空默yyyy-mm-dd HH:mm:ss）
     * @return String
     */
    private static String addMinuteToDate(int minute, Date date, String format) {
        Calendar calendar = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calendar.add(Calendar.MINUTE, minute);
        return sdf.format(calendar.getTime());
    }

    /**
     * 给指定日期加分钟（空默当前时）
     *
     * @param minute 加分钟 正加负减
     * @param date   指定日期
     * @param format 日期格式（空默yyyy-mm-dd HH:mm:ss）
     * @return String
     */
    public static String addMinuteToDate(int minute, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = stringToDate(date, format);
        }
        return addMinuteToDate(minute, newDate, format);
    }

    /**
     * 给指定日期加秒（空默当前时）
     *
     * @param second 加秒 正加负减
     * @param date   指定日期
     * @param format 日期格式（空默yyyy-mm-dd HH:mm:ss）
     * @return String
     */
    private static String addSecondToDate(int second, Date date, String format) {
        Calendar calendar = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calendar.add(Calendar.SECOND, second);
        return sdf.format(calendar.getTime());
    }

    /**
     * 给指定日期加秒（空默当前时）
     *
     * @param second 加秒 正加负减
     * @param date   指定日期
     * @param format 日期格式（空默yyyy-mm-dd HH:mm:ss）
     * @return String
     */
    public static String addSecondToDate(int second, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = stringToDate(date, format);
        }
        return addSecondToDate(second, newDate, format);
    }

    /**
     * 指定格式指定时间日历
     *
     * @param date   时间
     * @param format 格式
     * @return Calendar
     */
    private static Calendar getCalendar(Date date, String format) {
        if (date == null) {
            date = getCurrentDate(format);
        }
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return calender;
    }

    /**
     * 字符串转日期
     *
     * @param value value
     * @return Date
     */
    private static Date stringToDate(String value) {
        if (value == null || "".equals(value)) {
            return null;
        }
        SimpleDateFormat sdf = DateFormatUtils.getFormat(DateFormatUtils.DATE_FORMAT_SIX);
        Date date = null;
        try {
            value = DateFormatUtils.formatDate(value, DateFormatUtils.DATE_FORMAT_SIX);
            date = sdf.parse(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串（格式符合规范）转Date
     *
     * @param value  需转字符串
     * @param format 日期格式
     * @return Date
     */
    static Date stringToDate(String value, String format) {
        if (value == null || "".equals(value)) {
            return null;
        }
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        Date date = null;
        try {
            value = DateFormatUtils.formatDate(value, format);
            date = sdf.parse(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期格式转String
     *
     * @param value  需转日期
     * @param format 日期格式
     * @return String
     */
    private static String dateToString(Date value, String format) {
        if (value == null) {
            return null;
        }
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        return sdf.format(value);
    }

    /**
     * 日期转字符串
     *
     * @param value value
     * @return string
     */
    static String dateToString(Date value) {
        if (value == null) {
            return null;
        }
        SimpleDateFormat sdf = DateFormatUtils.getFormat(DateFormatUtils.DATE_FORMAT_SIX);
        return sdf.format(value);
    }

    /**
     * 指定日期的年份
     *
     * @param value 日期
     * @return int
     */
    public static int getCurrentYear(Date value) {
        String date = dateToString(value, DateFormatUtils.DATE_YEAR);
        return Integer.valueOf(date);
    }

    /**
     * 指定日期的年份
     *
     * @param value 日期
     * @return int
     */
    public static int getCurrentYear(String value) {
        Date date = stringToDate(value, DateFormatUtils.DATE_YEAR);
        Calendar calendar = getCalendar(date, DateFormatUtils.DATE_YEAR);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 指定日期月份
     *
     * @param value 日期
     * @return int
     */
    public static int getCurrentMonth(Date value) {
        String date = dateToString(value, DateFormatUtils.DATE_MONTH);
        return Integer.valueOf(date);
    }

    /**
     * 指定日期月份
     *
     * @param value 日期
     * @return int
     */
    public static int getCurrentMonth(String value) {
        Date date = stringToDate(value, DateFormatUtils.DATE_MONTH);
        Calendar calendar = getCalendar(date, DateFormatUtils.DATE_MONTH);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 指定日期天份
     *
     * @param value 日期
     * @return int
     */
    public static int getCurrentDay(Date value) {
        String date = dateToString(value, DateFormatUtils.DATE_DAY);
        return Integer.valueOf(date);
    }

    /**
     * 指定日期天份
     *
     * @param value 日期
     * @return int
     */
    public static int getCurrentDay(String value) {
        Date date = stringToDate(value, DateFormatUtils.DATE_DAY);
        Calendar calendar = getCalendar(date, DateFormatUtils.DATE_DAY);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 当前日期星期几
     *
     * @param value 日期
     * @return String
     */
    private static String getCurrentWeek(Date value) {
        Calendar calendar = getCalendar(value, DateFormatUtils.DATE_FORMAT_THREE);
        int weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1 < 0 ? 0 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return WEEKS[weekIndex];
    }

    /**
     * 当前日期星期几
     *
     * @param value 日期
     * @return String
     */
    public static String getCurrentWeek(String value) {
        Date date = stringToDate(value, DateFormatUtils.DATE_FORMAT_THREE);
        return getCurrentWeek(date);
    }

    /**
     * 指定日期小时
     *
     * @param value 日期
     * @return int
     */
    public static int getCurrentHour(Date value) {
        String date = dateToString(value, DateFormatUtils.DATE_HOUR);
        return Integer.valueOf(date);
    }

    /**
     * 指定日期小时
     *
     * @param value 日期
     * @return int
     */
    public static int getCurrentHour(String value) {
        Date date = stringToDate(value, DateFormatUtils.DATE_HOUR);
        Calendar calendar = getCalendar(date, DateFormatUtils.DATE_HOUR);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 指定日期分钟
     *
     * @param value 日期
     * @return int
     */
    public static int getCurrentMinute(Date value) {
        String date = dateToString(value, DateFormatUtils.DATE_MINUTE);
        return Integer.valueOf(date);
    }

    /**
     * 指定日期分钟
     *
     * @param value 日期
     * @return int
     */
    public static int getCurrentMinute(String value) {
        Date date = stringToDate(value, DateFormatUtils.DATE_MINUTE);
        Calendar calendar = getCalendar(date, DateFormatUtils.DATE_MINUTE);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 较两日期隔（天、月、年）
     * compareDate("2009-09-12", null, 0) 天
     * compareDate("2009-09-12", null, 1) 月
     * compareDate("2009-09-12", null, 2) 年
     *
     * @param startDay 需比较时间 不能为空，正确日期格式如2009-09-12
     * @param endDay   被比较时间 为空则为当前时间
     * @param type     返回值类型 0天数 1月数 2年数
     * @return int
     */
    public static int compareDate(String startDay, String endDay, int type) {
        int n = 0;
        startDay = DateFormatUtils.formatDate(startDay, "yyyy-MM-dd");
        endDay = DateFormatUtils.formatDate(endDay, "yyyy-MM-dd");
        String formatStyle = "yyyy-MM-dd";
        if (1 == type) {
            formatStyle = "yyyy-MM";
        } else if (UtilOneMagic.INT_TWO == type) {
            formatStyle = "yyyy";
        }
        endDay = endDay == null ? getCurrentTime("yyyy-MM-dd") : endDay;
        DateFormat df = new SimpleDateFormat(formatStyle, Locale.US);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(startDay));
            c2.setTime(df.parse(endDay));
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (!c1.after(c2)) {
            // 循环对比至相等，n即所要结果
            n++;
            if (type == 1) {
                // 比月（月+1）
                c1.add(Calendar.MONTH, 1);
            } else {
                // 比天数（日+1）
                c1.add(Calendar.DATE, 1);
            }
        }
        n = n - 1;
        if (type == UtilOneMagic.INT_TWO) {
            n = n / 365;
        }
        return n;
    }

    /**
     * 较两时间差（时、分、秒）
     *
     * @param oldTime 需比时间 不能为空且符正确格式2012-12-12 12:12
     * @param newTime 需被比时间 空默当前时
     * @param type    1小时2分钟3秒
     * @return int
     */
    public static int compareTime(String oldTime, String newTime, int type) {
        // newTime空默当前时
        if (newTime == null || "".equals(newTime)) {
            newTime = getCurrentTime();
        }
        SimpleDateFormat sdf = DateFormatUtils.getFormat("");
        int value = 0;
        try {
            Date oldDate = sdf.parse(oldTime);
            Date newDate = sdf.parse(newTime);
            // 转秒
            long between = 0;
            if (newDate != null && oldDate != null) {
                between = (newDate.getTime() - oldDate.getTime()) / 1000;
            }
            if (type == 1) {
                // 小时
                value = (int) (between % (24 * 36000) / 3600);
            } else if (type == UtilOneMagic.INT_TWO) {
                value = (int) (between % 3600 / 60);
            } else if (type == UtilOneMagic.INT_THREE) {
                value = (int) (between % 60 / 60);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 较两日期大小
     * date1 > date2 返1
     * date1 = date2 返0
     * date1 < date2 返-1
     *
     * @param date1  date1
     * @param date2  date2
     * @param format 待转格式
     * @return 结果
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static int compare(String date1, String date2, String format) {
        DateFormat df = DateFormatUtils.getFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1 != null && dt2 != null) {
                return Long.compare(dt1.getTime(), dt2.getTime());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获指定月份头天
     *
     * @param date date
     * @return string
     */
    private static String getMonthFirstDate(String date) {
        date = DateFormatUtils.formatDate(date);
        return DateFormatUtils.formatDate(date, "yyyy-MM") + "-01";
    }

    /**
     * 获指定月份最末天
     *
     * @param date date
     * @return string
     */
    public static String getMonthLastDate(String date) {
        Date strDate = DateUtils.stringToDate(getMonthFirstDate(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return DateFormatUtils.formatDate(calendar.getTime());
    }

    /**
     * 所在星期头天
     *
     * @param date date
     * @return Date
     */
    public static Date getWeekFirstDate(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int today = now.get(Calendar.DAY_OF_WEEK);
        // 星期一
        int firstDayOfWeek = now.get(Calendar.DATE) + 2 - today;
        now.set(Calendar.DATE, firstDayOfWeek);
        return now.getTime();
    }

    /**
     * 所在星期最末天
     *
     * @param date date
     * @return Date
     */
    public static Date geWeektLastDate(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int today = now.get(Calendar.DAY_OF_WEEK);
        // 星期一
        int firstDayOfWeek = now.get(Calendar.DATE) + 2 - today;
        // 星期日
        int lastDayOfWeek = firstDayOfWeek + 6;
        now.set(Calendar.DATE, lastDayOfWeek);
        return now.getTime();
    }

    /**
     * 较日期时间
     *
     * @param strPositive 主动日期时间
     * @param strNegative 被动日期时间
     * @param pattern     模式
     * @return 0等1大-1小-2无效
     */
    public static int dateCompare(String strPositive, String strNegative, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        Date datePositive = null;
        Date dateNegative = null;
        try {
            datePositive = simpleDateFormat.parse(strPositive);
            dateNegative = simpleDateFormat.parse(strNegative);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (datePositive != null) {
            return datePositive.compareTo(dateNegative);
        } else {
            return -2;
        }
    }

    /**
     * 当前年
     *
     * @return 当前年
     */
    public static int currentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 当前月
     *
     * @return 当前月
     */
    public static int currentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 当前日
     * 安卓系统月份从0-11计算（故真实月份加一）
     *
     * @return 当前日
     */
    public static int currentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 时间戳
     *
     * @return 时间戳
     */
    public static String timeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
    }

    /**
     * 差秒
     *
     * @param oldTime 旧时
     * @param newTime 新时
     * @return 差秒
     */
    public static long secondSeparate(String oldTime, String newTime) {
        long seconds = 0;
        // newTime空默当前时
        if (newTime == null || "".equals(newTime)) {
            newTime = getCurrentTime();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatUtils.DATE_FORMAT_FIVE, Locale.US);
        try {
            long oldConversionResult = simpleDateFormat.parse(oldTime).getTime();
            long newConversionResult = simpleDateFormat.parse(newTime).getTime();
            long dc = Math.abs(newConversionResult - oldConversionResult);
            seconds = dc / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return seconds;
    }

    /**
     * 根据毫秒返回时分秒
     *
     * @param time 时间
     * @return 时间
     */
    @SuppressLint("DefaultLocale")
    public static String getFormatHms(long time) {
        time = time / 1000;
        int s = (int) (time % 60);
        int m = (int) (time / 60);
        int h = (int) (time / 3600);
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    /**
     * 先后相差
     *
     * @param context   上下文
     * @param earlyTime 先时间
     * @param lateTime  后时间
     * @return 先后相差
     */
    public static String earlyLateDiffer(Context context, long earlyTime, long lateTime) {
        long between = lateTime - earlyTime;
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long m = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        BigDecimal second = new BigDecimal(s);
        BigDecimal millisecond = new BigDecimal(m);
        BigDecimal result = second.add(millisecond.divide(new BigDecimal(1000)).setScale(2, BigDecimal.ROUND_HALF_UP));
        return String.format(context.getApplicationContext().getString(R.string.earlyLateDiffer), BigDecimalToString.bigDecimalToString(result));
    }

    /**
     * 格式化时间
     *
     * @param num 时间毫秒
     * @return 格式化后的str
     */
    public static String formatDate(Long num) {
        String tem;
        if (num > 0) {
            Long minute = num / 60 / 1000;
            Long remainder = num % (60 * 1000);
            Long second = remainder / 1000;
            tem = addLeftZero(minute) + ":" + addLeftZero(second);
        } else {
            tem = "00:00";
        }
        return tem;
    }

    private static String addLeftZero(Long tempNum) {
        String num;
        if (tempNum < UtilOneMagic.INT_TEN) {
            num = "0" + tempNum;
        } else if (tempNum == 0) {
            num = "00";
        } else {
            num = "" + tempNum;
        }
        return num;
    }

    /**
     * 分秒
     *
     * @param time 时间
     * @return 分秒
     */
    public static String minuteSecond(int time) {
        int min = time % 3600 / 60;
        int second = time % 60;
        return String.format(Locale.CHINA, "%02d:%02d", min, second);
    }

    /**
     * 时分秒
     *
     * @param time 时间
     * @return 时分秒
     */
    private String hourMinuteSecond(int time) {
        int hour = time / 3600;
        int min = time % 3600 / 60;
        int second = time % 60;
        return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, min, second);
    }
}
