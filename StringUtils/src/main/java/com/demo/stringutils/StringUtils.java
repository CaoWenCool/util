package com.demo.stringutils;

import com.xiaoleilu.hutool.util.StrUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 17:46
 * @version: V1.0
 * @detail:
 **/
public class StringUtils {
    private StringUtils() {
    }


    /**
     * 模板字符串替换
     *
     * @param string
     * @param map
     * @return String string = "<p>您好！</p>\n" +
     * "<p>您刚才在{$site_name}重置了密码，新密码为：{$new_password}。</p>\n" +
     * "<p>请尽快登录 <a href=\"{$site_url}\" target=\"_blank\">{$site_url}</a> 修改密码。</p>";
     * HashMap<String, Object> map = new HashMap<>();
     * map.put("site_name", "B2B2C JAVA版");
     * map.put("new_password", "1101101111110");
     * map.put("site_url", "http://www.shopnc.net");
     * String stringNew = sendMessageHelper.replace(string, map);
     * System.out.println(stringNew);
     */
    public static String replace(String string, Map<String, Object> map) {
        if (string == null || string.length() == 0) {
            return "";
        }
        for (String key : map.keySet()) {
            string = string.replace("{$" + key + "}", String.valueOf(map.get(key)));
        }
        return string;
    }

    /**
     * 检验是否非空字符串
     *
     * @param obj 对象
     * @return
     */
    public static boolean isNotBlank(Object obj) {
        if (obj == null) return false;
        if (obj instanceof String) return StrUtil.isNotBlank((String) obj);
        return StrUtil.isNotBlank(obj.toString());
    }

    /**
     * 检验是否为空或空字符串
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return StringUtils.isNull(str).equals("");
    }

    public static boolean isBlank(Object o) {
        return StringUtils.isNull(o).equals("");
    }


    public static String isNull(Object o) {
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
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * public int indexOf(int ch, int fromIndex)
     * 返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索
     *
     * @param srcText
     * @param findText
     * @return
     */
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        int index = 0;
        while ((index = srcText.indexOf(findText, index)) != -1) {
            index = index + findText.length();
            count++;
        }
        return count;
    }

    /**
     * 将unicode编码转为 中文
     *
     * @param utfString
     * @return
     */
    public static String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }
        return sb.toString();
    }

    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
}
