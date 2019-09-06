package com.zsp.utilone.data;

/**
 * Created on 2019/9/6.
 *
 * @author 郑少鹏
 * @desc IntUtils
 */
public class IntUtils {
    /**
     * 奇数
     *
     * @param value 值
     * @return 奇数
     */
    public static boolean oddNumber(int value) {
        return value % 2 != 0;
    }

    /**
     * 偶数
     *
     * @param value 值
     * @return 偶数
     */
    public static boolean even(int value) {
        return value % 2 == 0;
    }
}
