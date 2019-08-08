package com.zsp.utilone.text;

import android.text.TextUtils;

/**
 * Created on 2019/8/8.
 *
 * @author 郑少鹏
 * @desc 文本显示
 */
public class TextDisplayUtils {
    /**
     * 无提示避空处理
     *
     * @param content 内容
     * @return 处理后内容
     */
    public static String avoidanceWithoutHint(String content) {
        return TextUtils.isEmpty(content) ? "暂无" : content;
    }

    /**
     * 有提示避空处理
     *
     * @param content 内容
     * @param hint    提示
     * @return 处理后内容
     */
    public static String avoidanceWithHint(String content, String hint) {
        return TextUtils.isEmpty(content) ? "暂无" + hint : content;
    }

    /**
     * 通Male或Female判性别
     *
     * @param sex     性别
     * @param combine 结合
     * @param hint    提示
     * @return 性别
     */
    public static String judgeSexByMaleOrFemale(String sex, boolean combine, String hint) {
        String result = null;
        if (TextUtils.isEmpty(sex)) {
            result = combine ? "暂无" + hint : "暂无";
        } else {
            switch (sex) {
                case "male":
                    result = "男";
                    break;
                case "female":
                    result = "女";
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
