package com.zsp.utilone;

/**
 * Created on 2018/6/20.
 *
 * @author 郑少鹏
 * @desc 系统
 */
public class SystemUtils {
    /**
     * 型号
     *
     * @return 型号
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }
}
