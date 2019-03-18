package com.demo.BigDecimalUtils;

import java.math.BigDecimal;

/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 14:51
 * @version: V1.0
 * @detail:
 **/
public class BigDecimalUtils {
    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param b     需要四舍五入的BigDecimal数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(BigDecimal b, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 默认保留两位小数
     *
     * @param bigDecimal 待处理数
     * @return BigDecimal
     */
    public static BigDecimal format(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            bigDecimal = new BigDecimal(0);
        }
        return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 乘法，结果保留两位小数
     *
     * @param bigDecimal 被乘数
     * @param number 乘数
     * @return BigDecimal
     */
    public static BigDecimal mul(BigDecimal bigDecimal, int number) {
        if (bigDecimal == null) {
            bigDecimal = format(new BigDecimal(0));
        }
        BigDecimal bigDecimalNumber = new BigDecimal(number).setScale(0, BigDecimal.ROUND_DOWN);
        return format(bigDecimal.multiply(bigDecimalNumber));
    }

    /**
     * 乘法，结果保留两位小数
     *
     * @param bigDecimal 被乘数
     * @param number 乘数
     * @return BigDecimal
     */
    public static BigDecimal mul(BigDecimal bigDecimal, double number) {
        if (bigDecimal == null) {
            bigDecimal = format(new BigDecimal(0));
        }
        BigDecimal bigDecimalNumber = new BigDecimal(number).setScale(2, BigDecimal.ROUND_DOWN);
        return format(bigDecimal.multiply(bigDecimalNumber));
    }

    /**
     * 加法，结果保留两位小数
     *
     * @param bigDecimal1 被加数
     * @param bigDecimal2 加数
     * @return BigDecimal
     */
    public static BigDecimal add(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        if (bigDecimal1 == null) {
            bigDecimal1 = format(new BigDecimal(0));
        }
        if (bigDecimal2 == null) {
            bigDecimal2 = format(new BigDecimal(0));
        }
        return format(bigDecimal1.add(bigDecimal2));
    }

    /**
     * 减法，结果保留两位小数
     *
     * @param bigDecimal1 被减数
     * @param bigDecimal2 减数
     * @return BigDecimal
     */
    public static BigDecimal sub(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        if (bigDecimal1 == null) {
            bigDecimal1 = format(new BigDecimal(0));
        }
        if (bigDecimal2 == null) {
            bigDecimal2 = format(new BigDecimal(0));
        }
        return format(bigDecimal1.subtract(bigDecimal2));
    }

    /**
     * 除法，结果保留两位小数
     *
     * @param bigDecimal 被除数
     * @param number 除数
     * @return BigDecimal
     */
    public static BigDecimal div(BigDecimal bigDecimal, int number) {
        if (bigDecimal == null) {
            bigDecimal = format(new BigDecimal(0));
        }
        BigDecimal bigDecimalNumber = new BigDecimal(number).setScale(0, BigDecimal.ROUND_DOWN);
        return bigDecimal.divide(bigDecimalNumber, 2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 除法，结果保留4位小数
     *
     * @param bigDecimal 被除数
     * @param number 除数
     * @return BigDecimal
     */
    public static BigDecimal div4(BigDecimal bigDecimal, int number) {
        if (bigDecimal == null) {
            bigDecimal = format(new BigDecimal(0));
        }
        BigDecimal bigDecimalNumber = new BigDecimal(number).setScale(0, BigDecimal.ROUND_DOWN);
        return bigDecimal.divide(bigDecimalNumber, 4, BigDecimal.ROUND_DOWN);
    }

    /**
     * 等于
     *
     * @param leftNum  比数
     * @param rightNum 被比标量
     * @return true or false
     */
    public static boolean isEquals(BigDecimal leftNum, BigDecimal rightNum) {
        if (leftNum == null) {
            leftNum = BigDecimal.ZERO;
        }
        if (rightNum == null) {
            rightNum = BigDecimal.ZERO;
        }
        leftNum = format(leftNum);
        rightNum = format(rightNum);
        return leftNum.equals(rightNum);
    }

    /**
     * 小于
     *
     * @param leftNum  比数
     * @param rightNum 被比标量
     * @return true or false
     */
    public static boolean isLessThan(BigDecimal leftNum, BigDecimal rightNum) {
        if (leftNum == null) {
            leftNum = BigDecimal.ZERO;
        }
        if (rightNum == null) {
            rightNum = BigDecimal.ZERO;
        }
        leftNum = format(leftNum);
        rightNum = format(rightNum);
        int r = leftNum.compareTo(rightNum);
        return r < 0;
    }

    /**
     * 小于等于
     *
     * @param leftNum  比数
     * @param rightNum 被比标量
     * @return true or false
     */
    public static boolean isLessThanOrEquals(BigDecimal leftNum, BigDecimal rightNum) {
        if (leftNum == null) {
            leftNum = BigDecimal.ZERO;
        }
        if (rightNum == null) {
            rightNum = BigDecimal.ZERO;
        }
        leftNum = format(leftNum);
        rightNum = format(rightNum);
        int r = leftNum.compareTo(rightNum);
        return r < 0 || r == 0;
    }

    /**
     * 大于
     *
     * @param leftNum  比数
     * @param rightNum 被比标量
     * @return true or false
     */
    public static boolean isGreaterThan(BigDecimal leftNum, BigDecimal rightNum) {
        if (leftNum == null) {
            leftNum = BigDecimal.ZERO;
        }
        if (rightNum == null) {
            rightNum = BigDecimal.ZERO;
        }
        leftNum = format(leftNum);
        rightNum = format(rightNum);
        int r = leftNum.compareTo(rightNum);
        return r > 0;
    }

    /**
     * 大于等于
     *
     * @param leftNum  比数
     * @param rightNum 被比标量
     * @return true or false
     */
    public static boolean isGreaterThanOrEquals(BigDecimal leftNum, BigDecimal rightNum) {
        if (leftNum == null) {
            leftNum = BigDecimal.ZERO;
        }
        if (rightNum == null) {
            rightNum = BigDecimal.ZERO;
        }
        leftNum = format(leftNum);
        rightNum = format(rightNum);
        int r = leftNum.compareTo(rightNum);
        return r > 0 || r == 0;
    }
}
