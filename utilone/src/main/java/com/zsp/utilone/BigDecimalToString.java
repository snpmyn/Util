package com.zsp.utilone;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created on 2019/3/11.
 *
 * @author 郑少鹏
 * @desc BigDecimalToString
 */
class BigDecimalToString {
    private static final String EXPR_PATTERN = "0.##########E0";
    private static final String PATTERN = "0.##########";
    private static final String INTEGER_MIN_VALUE_CHANGE_TO_EXPR = "10000000";
    private static final String DECIMAL_MIN_VALUE_CHANGE_TO_EXPR = "0.0001";

    /**
     * Judging number is able to convert to expr display or not.
     *
     * @param bigDecimal BigDecimal
     * @return boolean
     */
    private static boolean bigDecimalCanConvertToString(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return false;
        }
        boolean result = false;
        BigDecimal bigDecimalAbs = bigDecimal.abs();
        if ((bigDecimalAbs.compareTo(new BigDecimal(DECIMAL_MIN_VALUE_CHANGE_TO_EXPR)) <= 0) || (bigDecimalAbs.compareTo(new BigDecimal(INTEGER_MIN_VALUE_CHANGE_TO_EXPR)) >= 0)) {
            result = true;
        }
        if (bigDecimalAbs.compareTo(new BigDecimal(0)) == 0) {
            result = false;
        }
        return result;
    }

    /**
     * BigDecimal to String.
     *
     * @param bigDecimal BigDecimal
     * @return String
     */
    static String bigDecimalToString(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        DecimalFormat df = new DecimalFormat();
        if (bigDecimalCanConvertToString(bigDecimal)) {
            df.applyPattern(EXPR_PATTERN);
        } else {
            df.applyPattern(PATTERN);
        }
        return df.format(bigDecimal);
    }
}
