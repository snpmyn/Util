package com.zsp.utilone.json;

import org.json.JSONObject;

import static android.text.TextUtils.isEmpty;

/**
 * Created on 2019/6/21.
 *
 * @author 郑少鹏
 * @desc JsonUtils
 */
public class JsonUtils {
    /**
     * Json否
     *
     * @param s 字符串
     * @return Json否
     */
    public static boolean isJson(String s) {
        if (isEmpty(s)) {
            return false;
        } else {
            try {
                new JSONObject(s);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }
}
