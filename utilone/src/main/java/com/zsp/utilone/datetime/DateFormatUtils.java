package com.zsp.utilone.datetime;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;
import value.UtilOneMagic;

/**
 * Created on 2017/10/17.
 *
 * @author 郑少鹏
 * @decs DateFormatUtils
 */
public class DateFormatUtils {
    /**
     * ss
     */
    public static final String DATE_SECOND = "ss";
    /**
     * yyyy-MM-dd hh:mm:ss|SSS
     */
    public static final String TIME_FORMAT_SSS = "yyyy-MM-dd HH:mm:ss|SSS";
    /**
     * yyyyMMdd
     */
    public static final String DATE_NOFULL_FORMAT = "yyyyMMdd";
    /**
     * yyyyMMddHHmmss
     */
    public static final String TIME_NOFULL_FORMAT = "yyyyMMddHHmmss";
    /**
     * yyyy
     */
    static final String DATE_YEAR = "yyyy";
    /**
     * MM
     */
    static final String DATE_MONTH = "MM";
    /**
     * DD
     */
    static final String DATE_DAY = "dd";
    /**
     * HH
     */
    static final String DATE_HOUR = "HH";
    /**
     * mm
     */
    static final String DATE_MINUTE = "mm";
    /**
     * yyyy-MM
     */
    static final String DATE_FORMAT_TWO = "yyyy-MM";
    /**
     * yyyy-MM-dd
     */
    static final String DATE_FORMAT_THREE = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd hh:mm
     */
    static final String DATE_FORMAT_FIVE = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd hh:mm:ss
     */
    static final String DATE_FORMAT_SIX = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式转换
     * <p>
     * yyyy-MM-dd hh:mm:ss和yyyyMMddhhmmss互转
     * yyyy-mm-dd和yyyymmss互转
     *
     * @param value 日期
     * @return String
     */
    public static String formatString(String value) {
        String sReturn = "";
        if (value == null || "".equals(value)) {
            return sReturn;
        }
        if (value.length() == UtilOneMagic.INT_FOURTEEN) {
            // 长度14格式转yyyy-mm-dd hh:mm:ss
            sReturn = value.substring(0, 4)
                    + "-" + value.substring(4, 6)
                    + "-" + value.substring(6, 8) + " "
                    + value.substring(8, 10)
                    + ":" + value.substring(10, 12) + ":"
                    + value.substring(12, 14);
            return sReturn;
        }
        if (value.length() == UtilOneMagic.INT_NINETEEN) {
            // 长度19格式转yyyymmddhhmmss
            sReturn = value.substring(0, 4)
                    + value.substring(5, 7)
                    + value.substring(8, 10)
                    + value.substring(11, 13)
                    + value.substring(14, 16)
                    + value.substring(17, 19);
            return sReturn;
        }
        if (value.length() == UtilOneMagic.INT_TEN) {
            // 长度10格式转yyyymmhh
            sReturn = value.substring(0, 4) + value.substring(5, 7) + value.substring(8, 10);
        }
        if (value.length() == UtilOneMagic.INT_EIGHT) {
            // 长度8格式转yyyy-mm-dd
            sReturn = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
        }
        return sReturn;
    }

    static String formatDate(String date, String format) {
        if (date == null || "".equals(date)) {
            return "";
        }
        Date dt;
        SimpleDateFormat inFmt;
        SimpleDateFormat outFmt;
        ParsePosition pos = new ParsePosition(0);
        date = date.replace("-", "").replace(":", "");
        if ("".equals(date.trim())) {
            return "";
        }
        try {
            if (Long.parseLong(date) == 0L) {
                return "";
            }
        } catch (Exception e) {
            return date;
        }
        try {
            switch (date.trim().length()) {
                case 14:
                    inFmt = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
                    break;
                case 12:
                    inFmt = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);
                    break;
                case 10:
                    inFmt = new SimpleDateFormat("yyyyMMddHH", Locale.CHINA);
                    break;
                case 8:
                    inFmt = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
                    break;
                case 6:
                    inFmt = new SimpleDateFormat("yyyyMM", Locale.CHINA);
                    break;
                case 7:
                case 9:
                case 11:
                case 13:
                default:
                    return date;
            }
            if ((dt = inFmt.parse(date, pos)) == null) {
                return date;
            }
            if ((format == null) || ("".equals(format.trim()))) {
                outFmt = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
            } else {
                outFmt = new SimpleDateFormat(format, Locale.CHINA);
            }
            return outFmt.format(dt);
        } catch (Exception e) {
            Timber.e(e);
        }
        return date;
    }

    /**
     * 格式化日期
     *
     * @param date   date
     * @param format format
     * @return formatDate
     */
    public static String formatDate(Date date, String format) {
        return formatDate(DateUtils.dateToString(date), format);
    }

    /**
     * 格式化时间用默格式（yyyy-MM-dd HH:mm:ss）
     *
     * @param value value
     * @return formatDate
     */
    static @NotNull String formatDate(String value) {
        return getFormat(DATE_FORMAT_SIX).format(DateUtils.stringToDate(value, DATE_FORMAT_SIX));
    }

    /**
     * 格式化日期
     *
     * @param value value
     * @return formatDate
     */
    static @NotNull String formatDate(Date value) {
        return formatDate(DateUtils.dateToString(value));
    }

    /**
     * 日期显格式（空默yyyy-mm-dd HH:mm）
     *
     * @param format format
     * @return SimpleDateFormat
     */
    @Contract("_ -> new")
    static @NotNull SimpleDateFormat getFormat(String format) {
        if (format == null || "".equals(format)) {
            format = DATE_FORMAT_FIVE;
        }
        return new SimpleDateFormat(format, Locale.CHINA);
    }
}
