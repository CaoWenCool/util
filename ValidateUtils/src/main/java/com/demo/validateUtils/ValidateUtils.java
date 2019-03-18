package com.demo.validateUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 17:57
 * @version: V1.0
 * @detail:校验工具类
 **/
public class ValidateUtils {
    /** 手机号码校验 */
    private static Pattern MOBILE_PATTERN = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0|(18[0-9]),1,3,5,6,7,8])|(18[0-9])|(19[0-9])|(14[5,7])|)\\d{8}$");
    /** 密码格式1 */
    private static Pattern PASSWD_1_PATTERN = Pattern.compile("[0-9]");
    /** 密码格式2 */
    private static Pattern PASSWD_2_PATTERN = Pattern.compile("(?i)[a-zA-Z]");
    /** 身份证正则表达式(15位) */
    private static Pattern ID_CARD_1_PATTERN = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
    /** 身份证正则表达式(18位) */
    private static Pattern ID_CARD_2_PATTERN = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
    /** 邮箱格式校验 */
    private static Pattern EMAIL_PATTERN = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    /** 英文字符串 */
    private static Pattern ENG_PATTERN = Pattern.compile("^[A-Za-z0-9]+");
    /** 数字or字符串 */
    private static Pattern DIGIT_ENG_PATTERN = Pattern.compile("^[0-9a-zA-Z]*$");
    /** 中文校验 */
    private static Pattern CHINESE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]{2,25}");
    /** 价格 4位*/
    private static Pattern PRICE_PATTERN = Pattern.compile("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,4})?$");
    /** 价格 小数与整数 整数6位 小数6位*/
    private static Pattern PRICE_PATTERN6 = Pattern.compile("^([-+]?\\d{1,6})(\\.\\d{1,6})?$");
    /** 价格 小数与整数 整数6位 小数4位*/
    private static Pattern PRICE_PATTERN4 = Pattern.compile("^([-+]?\\d{1,6})(\\.\\d{1,4})?$");
    /** 价格 2位 */
    private static Pattern PRICE_PATTERN2 = Pattern.compile("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$");
    /** 小数 10位 */
    private static Pattern PRICE_PATTERN3 = Pattern.compile("^(0|[1-9][0-9]{0,10})(\\.[0-9]{0,10})?$");
    /** 中文及中文标点符号 */
    private static Pattern CHINESE = Pattern.compile("[\\u4e00-\\u9fa5]");
    /** 中文标点符号 */
    private static Pattern SIGN = Pattern.compile("[\\u3002-\\uff1b-\\uff0c-\\uff1a-\\u201c-\\u201d-\\uff08-\\uff09-\\u3001-\\uff1f-\\u300a-\\u300b]");
    /**
     * 密码格式校验
     *
     * @param pwd 待校验密码
     * @return 密码结果描述
     */
    public static String isPwd(String pwd, String encrypt) {
        int minLen = 8;
        int maxLen = 16;
        String result;
        if (pwd.length() < minLen || pwd.length() > maxLen) {
            result = "密码长度必须是8-16位！";
            return result;
        }
        if (regular(pwd)) {
            result = "";
        } else {
            result = "密码必须是数字+字母组成";
        }
        return result;
    }

    /**
     * 校验是否为手机号
     */
    public static boolean isPhone(String phone) {
        Matcher matcher = MOBILE_PATTERN.matcher(isNull(phone));
        return matcher.matches();
    }

    private static String isNull(Object o) {
        if (o == null) {
            return "";
        }
        String str;
        if (o instanceof String) {
            str = (String) o;
        } else {
            str = o.toString();
        }
        return str;
    }

    /**
     * 密码格式校验
     *
     * @param pwd 密码
     * @return 是否满足条件
     */
    public static boolean isPwd(String pwd) {
        boolean b1 = PASSWD_1_PATTERN.matcher(pwd).find();
        boolean b2 = PASSWD_2_PATTERN.matcher(pwd).find();
        return b1 && b2;
    }

    /**
     * 中文及中文符号校验
     *
     * @param str 密码
     * @return 是否满足条件
     */
    public static boolean isChinese1(String str){
        Pattern p = Pattern.compile("^[\\u3002-\\uff1b-\\uff0c-\\uff1a-\\u201c-\\u201d-\\uff08-\\uff09-\\u3001-\\uff1f-\\u300a-\\u300b\\u4e00-\\u9fa5]*$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 校验身份证号码
     *
     * @param cardId 身份证号码
     * @return 是否匹配
     */
    public static boolean isCard(String cardId) {
        cardId = isNull(cardId);
        Matcher matcher1 = ID_CARD_1_PATTERN.matcher(cardId);
        Matcher matcher2 = ID_CARD_2_PATTERN.matcher(cardId);
        return matcher1.matches() || matcher2.matches();
    }

    /**
     * 校验邮箱格式
     *
     * @param email 待校验邮箱
     * @return 是否匹配
     */
    public static boolean isEmail(String email) {
        email = isNull(email);
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    /**
     * 校验价格格式
     *
     * @param price 待校验价格字符串 保留4位
     * @return 是否匹配
     */
    public static boolean isPrice(String price) {
        price = isNull(price);
        Matcher matcher = PRICE_PATTERN.matcher(price);
        return matcher.matches();
    }

    /**
     * 校验价格格式
     *
     * @param price 待校验价格字符串 保留2位
     * @return 是否匹配
     */
    public static boolean isPrice2(String price) {
        price = isNull(price);
        Matcher matcher = PRICE_PATTERN2.matcher(price);
        return matcher.matches();
    }

    /**
     * 校验小数格式
     *
     * @param price 待校验价格字符串 浮点数（6位）和整数
     * @return 是否匹配
     */
    public static boolean isPrice6(String price) {
        price = isNull(price);
        Matcher matcher = PRICE_PATTERN6.matcher(price);
        return matcher.matches();
    }

    /**
     * 校验小数格式
     *
     * @param price 待校验价格字符串 浮点数（4位）和整数
     * @return 是否匹配
     */
    public static boolean isPrice4(String price) {
        price = isNull(price);
        Matcher matcher = PRICE_PATTERN4.matcher(price);
        return matcher.matches();
    }

    /**
     * 校验小数格式
     *
     * @param price 待校验价格字符串 保留10位
     * @return 是否匹配
     */
    public static boolean isPrice3(String price) {
        price = isNull(price);
        Matcher matcher = PRICE_PATTERN3.matcher(price);
        return matcher.matches();
    }
    /**
     * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
     *
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    private static String isNull(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    /**
     * 匹配由数字和26个英文字母组成的字符串 ^[A-Za-z0-9]+$,匹配返回true
     *
     * @param str 匹配的字符串
     * @return boolean
     */
    private static boolean regular(String str) {
        if (null == str || str.trim().length() <= 0) {
            return false;
        }
        Matcher m = ENG_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 匹配大于等于0的正整数
     * @param s  待交易字符串
     * @return 是否满足调价
     */
    public static boolean isPositiveInteger(Integer s) {
        return s != null && !"".equals(s.toString().trim()) && s.toString().trim().matches("^+?(([1-9]\\d?)|(0))$");
    }


    /**
     * 是否为纯字符串
     * @param s 待匹配字符串
     * @return 是否是纯字符串
     */
    public static boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]*$");
    }

    /**
     * 是否为金额
     * @param s 待匹配字符串
     * @return 是否是金额
     */
    public static boolean isMoney(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^([0-9]+|[0-9]{1,3}(,[0-9]{3})*)(.[0-9]{1,2})?$");
    }

    /**
     * 校验规则：数字或字母
     * @param str 待校验字符串
     * @return 是否匹配
     */
    public static boolean isCharOrNumber(String str){
        Matcher m = DIGIT_ENG_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 校验是否为中文
     * @author eproo
     * @date 2015年12月7日 下午11:13:59
     * @param str 待交易字符串
     * @return 是否为中文
     */
    public static boolean isChinese(String str) {
        Matcher matcher = CHINESE_PATTERN.matcher(str.trim());
        return matcher.matches();
    }

    /**
     * 校验是否为含有中文
     * @author eproo
     * @date 2015年12月7日 下午11:13:59
     * @param str 字符串
     * @return 是否为中文
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 用户名校验
     * @param userName
     * @return
     */
    public static boolean isUserName(String userName){
        Pattern p = Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$");
        Matcher m = p.matcher(userName);
        return m.matches();
    }
}
