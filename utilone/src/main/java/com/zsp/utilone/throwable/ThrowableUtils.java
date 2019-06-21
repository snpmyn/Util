package com.zsp.utilone.throwable;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created on 2019/6/21.
 *
 * @author 郑少鹏
 * @desc ThrowableUtils
 */
public class ThrowableUtils {
    /**
     * 异常引字符串
     *
     * @param throwable Throwable
     * @return 异常引字符串
     */
    public static String exceptionCauseString(Throwable throwable) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        PrintStream var2 = new PrintStream(var1);
        try {
            Throwable var3;
            for (var3 = throwable; var3.getCause() != null; var3 = var3.getCause()) {
                Log.e("exceptionCauseString", "var3 = ex; var3.getCause() != null; var3 = var3.getCause()");
            }
            var3.printStackTrace(var2);
            return toVisualString(var1.toString());
        } finally {
            try {
                var1.close();
            } catch (IOException var11) {
                var11.printStackTrace();
            }
        }
    }

    private static String toVisualString(String s) {
        boolean var1 = false;
        if (null == s) {
            return null;
        } else {
            char[] var2 = s.toCharArray();
            int var3;
            for (var3 = 0; var3 < var2.length; ++var3) {
                if (var2[var3] > 127) {
                    var2[var3] = 0;
                    var1 = true;
                    break;
                }
            }
            return var1 ? new String(var2, 0, var3) : s;
        }
    }
}
