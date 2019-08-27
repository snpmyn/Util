package com.zsp.utilone.array;

/**
 * Created on 2019/8/27.
 *
 * @author 郑少鹏
 * @desc ArrayUtils
 */
public class ArrayUtils {
    /**
     * 下标
     *
     * @param array  数组
     * @param object 实体
     * @param <T>    泛型
     * @return 下标
     */
    public static <T> int index(T[] array, T object) {
        for (int i = 0; i < array.length; i++) {
            if (object.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }
}
