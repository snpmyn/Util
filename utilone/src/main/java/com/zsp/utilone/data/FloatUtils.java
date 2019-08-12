package com.zsp.utilone.data;

/**
 * Created on 2019/8/12.
 *
 * @author 郑少鹏
 * @desc FloatUtils
 */
public class FloatUtils {
    /**
     * 相等
     *
     * @param a a
     * @param b b
     * @return 相等
     */
    public static boolean equal(float a, float b) {
        return Math.abs(a - b) == 0 || (Math.abs(a - b) < 0.00000001);
    }
}
