package com.zsp.utilone.datetime;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created on 2019/3/1.
 *
 * @author 郑少鹏
 * @desc TimeUtils
 */
public class TimeUtils {
    /**
     * 网络时间
     *
     * @return 网络时间
     */
    public static String getNetTime() {
        String netTime = null;
        try {
            // 获资源对象
            URL url = new URL("http://www.baidu.com");
            // 中国科学院国家授时中心
            /*url = new URL("http://www.ntsc.ac.cn");
            url = new URL("http://www.bjtime.cn");*/
            // 生成连接对象
            URLConnection uc = url.openConnection();
            // 发出连接
            uc.connect();
            // 获网站日期时间
            long ld = uc.getDate();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            netTime = formatter.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netTime;
    }

    /**
     * 系统时间
     *
     * @return 系统时间
     */
    public static String getSystemTime() {
        String systemTime = null;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            systemTime = formatter.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return systemTime;
    }
}
