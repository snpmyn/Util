package com.zsp.utilone.validate;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import value.UtilOneMagic;

/**
 * Created on 2017/9/15.
 *
 * @author 郑少鹏
 * @desc CheckOutUtils
 */
public class CheckOutUtils {
    /**
     * Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); 简匹
     * Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"); 复匹
     */
    private static final Pattern P0 = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private static final Pattern P1 = Pattern.compile("^([\u4E00-\u9FA5]|[\uF900-\uFA2D]|[\u258C]|[\u2022]|[\u2E80-\uFE4F])+$");
    private static final Pattern P2 = Pattern.compile("^[A-Za-z0-9][\\w._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})");
    private static final Pattern P3 = Pattern.compile("[0-9]*");
    private static final Pattern P5 = Pattern.compile("[\u4e00-\u9fa5]");
    private static final Pattern P6 = Pattern.compile("^[A-Za-z]+$");
    private static final Pattern P7 = Pattern.compile("^[\u4E00-\u9FFF]+$");
    private static final Pattern P8 = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z.*]{6,20}$");
    private static final Pattern P9 = Pattern.compile("[A-Za-z0-9]{6,12}");
    private static final Pattern P10 = Pattern.compile(".{6,20}");
    private static final Pattern P11 = Pattern.compile(".{1,250}");
    private static final Pattern P12 = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\\\D+$).{4,16}$");
    private static final Pattern P13 = Pattern.compile(".{2,25}");
    private static final Pattern P14 = Pattern.compile("[a-zA-Z][a-zA-Z0-9]+");
    private static final Pattern P15 = Pattern.compile(".{13,19}");
    private static final Pattern P16 = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-/\\s]?((((0?[13578])|(1[02]))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-/\\s]?((((0?[13578])|(1[02]))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3])):([0-5]?[0-9])((\\s)|(:([0-5]?[0-9])))))?$");

    /**
     * 用户名1-50即2-25汉字
     *
     * @param userName 用户名
     * @return 2-25汉字
     */
    public static boolean isUserNameYws(String userName) {
        Matcher mc = P1.matcher(userName);
        return mc.matches();
    }

    /**
     * 邮箱
     *
     * @param mail 邮箱
     * @return 邮箱否
     */
    public static boolean isValidEmail(String mail) {
        Matcher mc = P2.matcher(mail);
        return mc.matches();
    }

    /**
     * 输数字否
     */
    public static boolean isNumeric(String str) {
        return P3.matcher(str).matches();
    }

    /**
     * 联系方式
     *
     * @param str 座或手机
     * @return 联系方式
     */
    public static boolean isMobileOrPhone(String str) {
        String regex = "^((([0\\+]\\d{2,3}-)|(0\\d{2,3})-))(\\d{7,8})(-(\\d{3,}))?$|^1[0-9]{10}$";
        return match(regex, str);
    }

    /**
     * 金额有效性
     *
     * @param price 金额
     * @return 金额有效性
     */
    public static boolean isPrice(String price) {
        String regex = "^([1-9][0-9]{0,7})(\\.\\d{1,2})?$";
        return match(regex, price);
    }

    /**
     * 含中否
     */
    public static boolean isContainChinese(String str) {
        Matcher m = P5.matcher(str);
        return m.find();
    }

    /**
     * 纯英否
     */
    public static boolean isLetter(String str) {
        Matcher m = P6.matcher(str);
        return m.matches();
    }

    /**
     * 纯中否
     */
    public static boolean isChinese(String str) {
        Matcher m = P7.matcher(str);
        return m.matches();
    }

    /**
     * 密码类型否
     */
    public static boolean isPassword(String str) {
        Matcher m = P8.matcher(str);
        return m.matches();
    }

    /**
     * 警员号否
     */
    public static boolean isPoliceNumberAndLength(String str) {
        Matcher m = P9.matcher(str);
        return m.matches();
    }

    /**
     * 邮件email格式正确否
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
        Matcher m = P0.matcher(email);
        return m.matches();
    }

    /**
     * 字符长6到20位
     *
     * @param str 字符
     * @return 6到20位
     */
    public static boolean isLength(String str) {
        return P10.matcher(str).matches();
    }

    /**
     * 住址字符长1到250位
     *
     * @param str 住址
     * @return 1到250位
     */
    public static boolean isAddressLength(String str) {
        return P11.matcher(str).matches();
    }

    /**
     * 用户名长4到16位
     *
     * @param str 用户名
     * @return 4到16位
     */
    public static boolean isUserNameSsl(String str) {
        return P12.matcher(str).matches();
    }

    /**
     * 用户真名长
     *
     * @param str 用户真名
     * @return 用户真名长
     */
    public static boolean isNameLength(String str) {
        return P13.matcher(str).matches();
    }

    /**
     * 输字符首字母英否
     *
     * @param str 输字符
     * @return 输字符首字母英否
     */
    public static boolean isEnglish(String str) {
        return P14.matcher(str).matches();
    }

    /**
     * 银行卡13到19位
     *
     * @param str 银行卡号
     * @return 13到19位
     */
    public static boolean isBank(String str) {
        return P15.matcher(str).matches();
    }

    /**
     * 中占两字符（英占一）
     */
    public static int stringLength(@NotNull String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 过滤特殊字符
     */
    public static @NotNull String stringFilter(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    /**
     * 密码长（6-20位除空格回车tab外字符）
     */
    public static boolean isPassLenLes(String str) {
        String regex = "^\\INT_S{6,20}$";
        return match(regex, str);
    }

    /**
     * 密码长（6到16位数字字母组合）
     */
    public static boolean isPassLenLsl(String str) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        return match(regex, str);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   所匹字符串
     * @return str符regex正则表达式格式返true，否返false
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 字符串日期格式否
     *
     * @return 字符串日期格式否
     */
    public static boolean isDate(String strDate) {
        Matcher m = P16.matcher(strDate);
        return m.matches();
    }

    /**
     * 身份证号合规否
     * <p>
     * "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x|Y|y)$)"改"(^\\d{18}$)|(^\\d{17}(\\d|X|x|Y|y)$)"
     * 15 居民身份证编码
     * 18 公民身份证号码
     *
     * @param identityCode 身份证号
     * @return 合规否
     */
    public static boolean isIdentityCard(String identityCode) {
        if (identityCode != null) {
            String identityCardRegex = "(^\\d{18}$)|(^\\d{17}(\\d|X|x|Y|y)$)";
            return identityCode.matches(identityCardRegex);
        }
        return false;
    }

    /**
     * 据身份证号获生日
     *
     * @return 生日
     */
    public static String getUserBirthdayByCardId(@NotNull String ids) {
        String birthday = "";
        if (ids.length() == UtilOneMagic.INT_EIGHTEEN) {
            // 18位
            birthday = ids.substring(6, 14);
            String years = birthday.substring(0, 4);
            String moths = birthday.substring(4, 6);
            String days = birthday.substring(6, 8);
            birthday = years + "-" + moths + "-" + days;
        } else if (ids.length() == UtilOneMagic.INT_FIFTEEN) {
            // 15位
            birthday = ids.substring(6, 12);
            String years = birthday.substring(0, 2);
            String moths = birthday.substring(2, 4);
            String days = birthday.substring(4, 6);
            birthday = "19" + years + "-" + moths + "-" + days;
        }
        return birthday;
    }

    /**
     * 据身份证号获性别
     *
     * @return 性别
     */
    public static String getUserSexByCardId(@NotNull String ids) {
        String sexShow = "";
        if (ids.length() == UtilOneMagic.INT_EIGHTEEN) {
            // 身份证倒数第二位
            String sexString = ids.trim().substring(ids.length() - 2, ids.length() - 1);
            // 转数字
            int sexNum = Integer.parseInt(sexString);
            if (sexNum % UtilOneMagic.INT_TWO != 0) {
                sexShow = "男";
            } else {
                sexShow = "女";
            }
        } else if (ids.length() == UtilOneMagic.INT_FIFTEEN) {
            // 身份证最后一位
            String sexString = ids.trim().substring(ids.length() - 1, ids.length());
            // 转数字
            int sexNum = Integer.parseInt(sexString);
            if (sexNum % UtilOneMagic.INT_TWO != 0) {
                sexShow = "男";
            } else {
                sexShow = "女";
            }
        }
        return sexShow;
    }
}
