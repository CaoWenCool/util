package com.demo.rasutils;

/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 17:54
 * @version: V1.0
 * @detail:
 **/

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理方法
 */
public class ToolString {
    /**
     * 常用正则表达式：匹配非负整数（正整数 + 0）
     */
    public final static String regExp_integer_1 = "^\\d+$";

    /**
     * 常用正则表达式：匹配正整数
     */
    public final static String regExp_integer_2 = "^[0-9]*[1-9][0-9]*$";

    /**
     * 常用正则表达式：匹配非正整数（负整数  + 0）
     */
    public final static String regExp_integer_3 = "^((-\\d+) ?(0+))$";

    /**
     * 常用正则表达式：匹配负整数
     */
    public final static String regExp_integer_4 = "^-[0-9]*[1-9][0-9]*$";

    /**
     * 常用正则表达式：匹配整数
     */
    public final static String regExp_integer_5 = "^-?\\d+$";

    /**
     * 常用正则表达式：匹配非负浮点数（正浮点数 + 0）
     */
    public final static String regExp_float_1 = "^\\d+(\\.\\d+)?$";

    /**
     * 常用正则表达式：匹配正浮点数
     */
    public final static String regExp_float_2 = "^(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*))$";

    /**
     * 常用正则表达式：匹配非正浮点数（负浮点数 + 0）
     */
    public final static String regExp_float_3 = "^((-\\d+(\\.\\d+)?) ?(0+(\\.0+)?))$";

    /**
     * 常用正则表达式：匹配负浮点数
     */
    public final static String regExp_float_4 = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*)))$";

    /**
     * 常用正则表达式：匹配浮点数
     */
    public final static String regExp_float_5 = "^(-?\\d+)(\\.\\d+)?$";

    /**
     * 常用正则表达式：匹配由26个英文字母组成的字符串
     */
    public final static String regExp_letter_1 = "^[A-Za-z]+$";

    /**
     * 常用正则表达式：匹配由26个英文字母的大写组成的字符串
     */
    public final static String regExp_letter_2 = "^[A-Z]+$";

    /**
     * 常用正则表达式：匹配由26个英文字母的小写组成的字符串
     */
    public final static String regExp_letter_3 = "^[a-z]+$";

    /**
     * 常用正则表达式：匹配由数字和26个英文字母组成的字符串
     */
    public final static String regExp_letter_4 = "^[A-Za-z0-9]+$";

    /**
     * 常用正则表达式：匹配由数字、26个英文字母或者下划线组成的字符串
     */
    public final static String regExp_letter_5 = "^\\w+$";

    /**
     * 常用正则表达式：匹配email地址
     */
    public final static String regExp_email = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

    /**
     * 常用正则表达式：匹配url
     */
    public final static String regExp_url_1 = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";

    /**
     * 常用正则表达式：匹配url
     */
    public final static String regExp_url_2 = "[a-zA-z]+://[^\\s]*";

    /**
     * 常用正则表达式：匹配中文字符
     */
    public final static String regExp_chinese_1 = "[\\u4e00-\\u9fa5]";

    /**
     * 常用正则表达式：匹配双字节字符(包括汉字在内)
     */
    public final static String regExp_chinese_2 = "[^\\x00-\\xff]";

    /**
     * 常用正则表达式：匹配空行
     */
    public final static String regExp_line = "\\n[\\s ? ]*\\r";

    /**
     * 常用正则表达式：匹配HTML标记
     */
    public final static String regExp_html_1 = "/ <(.*)>.* <\\/\\1> ? <(.*) \\/>/";

    /**
     * 常用正则表达式：匹配首尾空格
     */
    public final static String regExp_startEndEmpty = "(^\\s*) ?(\\s*$)";

    /**
     * 常用正则表达式：匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)
     */
    public final static String regExp_accountNumber = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

    /**
     * 常用正则表达式：匹配国内电话号码，匹配形式如 0511-4405222 或 021-87888822
     */
    public final static String regExp_telephone = "\\d{3}-\\d{8} ?\\d{4}-\\d{7}";

    /**
     * 常用正则表达式：腾讯QQ号, 腾讯QQ号从10000开始
     */
    public final static String regExp_qq = "[1-9][0-9]{4,}";

    /**
     * 常用正则表达式：匹配中国邮政编码
     */
    public final static String regExp_postbody = "[1-9]\\d{5}(?!\\d)";

    /**
     * 常用正则表达式：匹配身份证, 中国的身份证为15位或18位
     */
    public final static String regExp_idCard = "\\d{15} ?\\d{18}";

    /**
     * 常用正则表达式：IP
     */
    public final static String regExp_ip = "\\d+\\.\\d+\\.\\d+\\.\\d+";

    /**
     * 字符编码
     */
    public final static String ENCODING = "UTF-8";

    /**
     * 验证字符串是否匹配指定正则表达式
     *
     * @param content
     * @param regExp
     * @return
     */
    public static boolean regExpVali(String content, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    /**
     * double精度调整
     *
     * @param doubleValue 需要调整的值123.454
     * @param format      目标样式".##"
     * @return
     */
    public static String decimalFormat(double doubleValue, String format) {
        DecimalFormat myFormatter = new DecimalFormat(format);
        String formatValue = myFormatter.format(doubleValue);
        return formatValue;
    }

    /**
     * Url Base64编码
     *
     * @param data 待编码数据
     * @return String 编码数据
     * @throws Exception
     */
    public static String encode(String data) throws Exception {
        // 执行编码
        byte[] b = Base64.encodeBase64URLSafe(data.getBytes(ENCODING));

        return new String(b, ENCODING);
    }

    /**
     * Url Base64解码
     *
     * @param data 待解码数据
     * @return String 解码数据
     * @throws Exception
     */
    public static String decode(String data) throws Exception {
        // 执行解码
        byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));

        return new String(b, ENCODING);
    }

    /**
     * URL编码（utf-8）
     *
     * @param source
     * @return
     */
    public static String urlEncode(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * //中文转Unicode
     * @param gbString 中文
     * @return unicode
     */
    public static String gbEncoding(final String gbString) {   //gbString = "测试"
        char[] utfBytes = gbString.toCharArray();   //utfBytes = [测, 试]
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);   //转换为16进制整型字符串
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        System.out.println("unicodeBytes is: " + unicodeBytes);
        return unicodeBytes;
    }

    /**
     * Unicode转中文
     * @param dataStr Unicode
     * @return 中文
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExt(String contentType) {
        String fileExt = "";
        if ("image/jpeg".equals(contentType)) {
            fileExt = ".jpg";

        } else if ("audio/mpeg".equals(contentType)) {
            fileExt = ".mp3";

        } else if ("audio/amr".equals(contentType)) {
            fileExt = ".amr";

        } else if ("video/mp4".equals(contentType)) {
            fileExt = ".mp4";

        } else if ("video/mpeg4".equals(contentType)) {
            fileExt = ".mp4";
        }

        return fileExt;
    }

    /**
     * 获取bean名称
     *
     * @param bean
     * @return
     */
    public static String beanName(Object bean) {
        String fullClassName = bean.getClass().getName();
        String classNameTemp = fullClassName.substring(fullClassName.lastIndexOf(".") + 1, fullClassName.length());
        return classNameTemp.substring(0, 1) + classNameTemp.substring(1);
    }

    public final static Pattern referer_pattern = Pattern.compile("@([^@^\\s^:]{1,})([\\s\\:\\,\\;]{0,1})");//@.+?[\\s:]

    /*
     * 处理提到某人 @xxxx
     * @param msg  传入的文本内容
     * @param referers 传出被引用到的会员名单
     * @return 返回带有链接的文本内容
     */
    /*public static String userLinks(String contents, List<String> userReferers) {
	    StringBuilder html = new StringBuilder();
	    int lastIdx = 0;
	    Matcher matchr = referer_pattern.matcher(contents);
	    while (matchr.find()) {
	        String origion_str = matchr.group();
	        //System.out.println("-->"+origion_str);
	        String userName = origion_str.substring(1, origion_str.length()).trim();
	        //char ch = str.charAt(str.length()-1);
	        //if(ch == ':' || ch == ',' || ch == ';')
	        //  str = str.substring(0, str.length()-1);
	        //System.out.println(str);
	        html.append(contents.substring(lastIdx, matchr.start()));

	        User user = null;
			Object userObj = User.dao.cacheGet(userName);
			if (null != userObj) {
				user = (User) userObj;
			} else {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("column", "username");
				String sql.sql = ToolSqlXml.getSql(User.sqlId_column, param, ConstantRender.sql_renderType_beetl);
				List<User> userList = User.dao.find(sql.sql, userName);
				if (userList.size() == 1) {
					user = userList.get(0);
				}
			}

	        if(user != null){
	            html.append("<a href='http://www.xx.com/"+user.getStr("username")+"' class='referer' target='_blank'>@");
	            html.append(userName.trim());
	            html.append("</a> ");
	            if(userReferers != null && !userReferers.contains(user.getId())){
	            	userReferers.add(user.getId());
	            }
	        } else {
	            html.append(origion_str);
	        }
	        lastIdx = matchr.end();
	        //if(ch == ':' || ch == ',' || ch == ';')
	        //  html.append(ch);
	    }
	    html.append(contents.substring(lastIdx));
	    return html.toString();
	}*/

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
     *
     * @param str
     * @return
     */
    public static String isNull(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    /**
     * 将对象转为字符串
     *
     * @param o
     * @return
     */
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
        return str.trim();
    }

    /**
     * 检验是否为空或空字符串
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return isNull(str).equals("");
    }

    public static boolean isBlank(Object o) {
        return isNull(o).equals("");
    }

    /**
     * 检验是否非空字符串
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isNull(str).equals("");
    }
}
