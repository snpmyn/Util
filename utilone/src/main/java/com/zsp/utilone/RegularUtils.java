package com.zsp.utilone;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2018/4/11.
 *
 * @author 郑少鹏
 * @desc 正则工具类
 */
public class RegularUtils {
    /**
     * 手机号（简单）
     */
    private static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
    /**
     * 手机号（精确）
     * 移动134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188
     * 联通130、131、132、145、155、156、175、176、185、186
     * 电信133、153、173、177、180、181、189
     * 全球星1349
     * 虚拟运营商170
     */
    private static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$";
    /**
     * 电话号码（正则）
     */
    private static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";
    /**
     * 身份证号码15位（正则）
     */
    private static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 身份证号码18位（正则）
     */
    private static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    /**
     * 邮箱（正则）
     */
    private static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * URL（正则）
     */
    private static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";
    /**
     * 汉字（正则）
     */
    private static final String REGEX_ZH = "^[\\u4e00-\\u9fa5]+$";

    /**
     * constructor
     */
    private RegularUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 手机号（简单）
     *
     * @param input 待验文本
     * @return true匹/false不匹
     */
    public static boolean isMobileSimple(CharSequence input) {
        return isMatch(REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 手机号（精确）
     *
     * @param input 待验文本
     * @return true匹/false不匹
     */
    public static boolean isMobileExact(CharSequence input) {
        return isMatch(REGEX_MOBILE_EXACT, input);
    }

    /**
     * 电话号码
     *
     * @param input 待验文本
     * @return true匹/false不匹
     */
    public static boolean isTel(CharSequence input) {
        return isMatch(REGEX_TEL, input);
    }

    /**
     * 身份证号码15位
     *
     * @param input 待验文本
     * @return true匹/false不匹
     */
    public static boolean isIdCard15(CharSequence input) {
        return isMatch(REGEX_ID_CARD15, input);
    }

    /**
     * 身份证号码18位
     *
     * @param input 待验文本
     * @return true匹/false不匹
     */
    public static boolean isIdCard18(CharSequence input) {
        return isMatch(REGEX_ID_CARD18, input);
    }

    /**
     * 邮箱
     *
     * @param input 待验文本
     * @return true匹/false不匹
     */
    public static boolean isEmail(CharSequence input) {
        return isMatch(REGEX_EMAIL, input);
    }

    /**
     * URL
     *
     * @param input 待验文本
     * @return true匹/false不匹
     */
    public static boolean isUrl(CharSequence input) {
        return isMatch(REGEX_URL, input);
    }

    /**
     * 汉字
     *
     * @param input 待验文本
     * @return true匹/false不匹
     */
    public static boolean isZh(CharSequence input) {
        return isMatch(REGEX_ZH, input);
    }

    /**
     * 匹正则否
     *
     * @param regex 正则表达式
     * @param input 所匹字符串
     * @return true匹/false不匹
     */
    private static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 正则匹配部分
     *
     * @param regex 正则表达式
     * @param input 所匹字符串
     * @return 正则匹配部分
     */
    public static List getMatches(String regex, CharSequence input) {
        if (input == null) {
            return null;
        }
        List matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 正则匹配分组
     *
     * @param input 要分组的字符串
     * @param regex 正则表达式
     * @return 正则匹配分组
     */
    public static String[] getSplits(String input, String regex) {
        if (input == null) {
            return null;
        }
        return input.split(regex);
    }

    /**
     * 替正则匹配第一部分
     *
     * @param input       所替字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替正则匹配第一部分
     */
    public static String getReplaceFirst(String input, String regex, String replacement) {
        if (input == null) {
            return null;
        }
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替所有正则匹配部分
     *
     * @param input       所替字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替所有正则匹配部分
     */
    public static String getReplaceAll(String input, String regex, String replacement) {
        if (input == null) {
            return null;
        }
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}
