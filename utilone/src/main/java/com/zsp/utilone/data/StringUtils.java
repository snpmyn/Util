package com.zsp.utilone.data;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

import value.UtilOneMagic;

import static android.text.TextUtils.isEmpty;

/**
 * Created on 2018/3/30.
 *
 * @author 郑少鹏
 * @desc StringUtils
 */
public class StringUtils {
    private static final char[] DIGITS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 指定部分前内容（指定内容仅一份）
     *
     * @param str     内容
     * @param appoint 指定内容
     * @return 指定部分前内容
     */
    public static String appointOnlyOneForward(String str, String appoint) {
        String result = null;
        // 指定部分长
        int appointLength = appoint.length();
        // for循环找合适范围
        for (int i = 0; i < str.length() - appointLength; i++) {
            if (str.substring(i, i + appointLength).equals(appoint)) {
                result = str.substring(0, i);
            }
        }
        return result;
    }

    /**
     * 指定部分后内容（指定内容仅一份）
     *
     * @param str     内容
     * @param appoint 指定内容
     * @return 指定部分后内容
     */
    public static String appointOnlyOneBack(String str, String appoint) {
        String result = null;
        // 指定部分长
        int appointLength = appoint.length();
        // for循环找合适范围
        for (int i = 0; i < str.length() - appointLength; i++) {
            if (str.substring(i, i + appointLength).equals(appoint)) {
                result = str.substring(i + appointLength);
            }
        }
        return result;
    }

    /**
     * 指定部分前内容
     *
     * @param str     内容
     * @param appoint 指定内容
     * @return 指定部分前内容
     */
    public static String appointForward(String str, String appoint) {
        return str != null ? str.substring(0, str.indexOf(appoint)) : null;
    }

    /**
     * 转double为字符串
     * <p>
     * 最多留num位小数。
     * num为2时，1.268为1.27；1.2仍为1.2；1仍为1（而非1.00）；100.00为100。
     *
     * @param d   double
     * @param num 小数位数
     * @return 字符串
     */
    private static String doubleToString(double d, int num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 留两位小数
        nf.setMaximumFractionDigits(num);
        // 去掉数值中千位分隔符
        nf.setGroupingUsed(false);
        String temp = nf.format(d);
        if (temp.contains(UtilOneMagic.STRING_DOT)) {
            String s1 = temp.split("\\.")[0];
            String s2 = temp.split("\\.")[1];
            for (int i = s2.length(); i > 0; --i) {
                if (!"0".equals(s2.substring(i - 1, i))) {
                    return s1 + "." + s2.substring(0, i);
                }
            }
            return s1;
        }
        return temp;
    }

    /**
     * 转Double为字符串
     * <p>
     * 最多留num位小数。
     *
     * @param d        double
     * @param num      小数位数
     * @param defValue 默认值。null时返该值。
     * @return 字符串
     */
    public static String doubleToString(Double d, int num, String defValue) {
        if (d == null) {
            return defValue;
        }
        return doubleToString(d, num);
    }

    /**
     * 关联否
     *
     * @param oneString 字符串一
     * @param twoString 字符串二
     * @return 关联否
     */
    public static boolean relate(String oneString, String twoString) {
        return oneString.contains(twoString) || twoString.contains(oneString);
    }

    /**
     * 贴开头
     *
     * @param currentString 目前字符串
     * @param attachString  贴字符串
     * @return 贴后字符串
     */
    public static String attachStart(String currentString, String attachString) {
        if (currentString == null) {
            return attachString;
        } else {
            return currentString.startsWith(attachString) ? currentString : attachString + currentString;
        }
    }

    /**
     * 贴结尾
     *
     * @param currentString 目前字符串
     * @param attachString  贴字符串
     * @return 贴后字符串
     */
    public static String attachEnd(String currentString, String attachString) {
        if (currentString == null) {
            return attachString;
        } else {
            return currentString.endsWith(attachString) ? currentString : currentString + attachString;
        }
    }

    /**
     * 环绕贴
     *
     * @param currentString 目前字符串
     * @param attachString  贴字符串
     * @return 贴后字符串
     */
    public static String attachAround(String currentString, String attachString) {
        if (currentString == null) {
            return attachString;
        } else {
            currentString = currentString.endsWith(attachString) ? currentString : currentString + attachString;
            currentString = currentString.startsWith(attachString) ? currentString : attachString + currentString;
            return currentString;
        }
    }

    /**
     * 字符串转布尔型
     *
     * @param s 字符串
     * @return 布尔型
     */
    public static boolean stringToBoolean(String s) {
        if (isEmpty(s)) {
            return false;
        } else {
            try {
                return Boolean.parseBoolean(s);
            } catch (Exception var2) {
                return false;
            }
        }
    }

    /**
     * 文件转字符串
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 字符串
     */
    public static String readFromFile(Context context, String fileName) {
        if (context != null && !isEmpty(fileName)) {
            AssetManager am = context.getAssets();
            InputStream input;
            try {
                input = am.open(fileName);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = input.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                }
                output.close();
                input.close();
                return output.toString();
            } catch (IOException var7) {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 转ABC为整型
     *
     * @param s 字符串
     * @return 整型
     */
    public static int convertAbcToInt(String s) {
        int answer = 0;
        char c = 'A';
        s = s.toUpperCase();
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            answer = Character.getNumericValue(s.charAt(i)) - Character.getNumericValue(c) + 26 * answer + 1;
            if (answer > (2147483646 - Character.getNumericValue(s.charAt(i)) + Character.getNumericValue(c)) / 26) {
                return 0;
            }
        }
        return answer;
    }

    /**
     * 转整型为ABC
     *
     * @param i 整型
     * @return ABC
     */
    public static String convertIntToAbc(int i) {
        int k = i;
        StringBuilder stringBuffer = new StringBuilder();
        if (i == 0) {
            return "";
        } else {
            while (k != 0) {
                int c = k % 26;
                if (c == 0) {
                    c = 26;
                }
                stringBuffer.insert(0, DIGITS[c - 1]);
                k = (k - c) / 26;
            }
            return stringBuffer.toString();
        }
    }

    /**
     * Padding the specified number of spaces to the input string to make it that length.
     *
     * @param input String
     * @param size  int
     * @return String
     */
    public static String paddingLeft(String input, int size) {
        if (input.length() > size) {
            throw new IllegalArgumentException("input must be shorter than or equal to the number of spaces: " + size);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = input.length(); i < size; i++) {
            sb.append(" ");
        }
        return sb.append(input).toString();
    }

    /**
     * Replace all occurances of the searchString in the originalString with the replaceString.
     * Faster than the String.replace() method.
     * Does not use regexes.
     * <p>
     * If your searchString is empty, this will spin forever.
     *
     * @param originalString 原始字符串
     * @param searchString   搜索字符串
     * @param replaceString  替换字符串
     * @return 字符串
     */
    public static String replace(String originalString, String searchString, String replaceString) {
        StringBuilder sb = new StringBuilder(originalString);
        int index = sb.indexOf(searchString);
        while (index != -1) {
            sb.replace(index, index + searchString.length(), replaceString);
            index += replaceString.length();
            index = sb.indexOf(searchString, index);
        }
        return sb.toString();
    }
}
