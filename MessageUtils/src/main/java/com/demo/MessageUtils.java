package com.demo;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 18:43
 * @version: V1.0
 * @detail: 信息校验工具
 **/
public class MessageUtils {
    //电话号码(带区号)
    public static final String REGEX_PHONE = "^[0][1-9]{2,3}-[0-9]{5,10}$";
    //电话号码（不带区号）
    public static final String REGEX_PHONE1 = "^[1-9]{1}[0-9]{5,8}$";
    //手机号码
    public static final String REGEX_MOBILE = "^[1][3,4,5,7,8][0-9]{9}$";
    //校验Email的格式
    public static final String REGEX_EMIAL = "[\\W\\.\\-]+@([\\W\\-]+\\.)+[\\W\\-]+";
    //校验车牌号码
    public static final String REGEX_CARNO = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";
    //校验身份证号码(定义判别用户身份证号码的正则表达式 15位或者18位，最后一位可以是字母)
    public static final String REGEX_IDCARD = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";


    /**
     * 校验身份证号码
     * @param idCard
     * @return
     */
    public static boolean isIdCardNo(String idCard){
        boolean isIdCard = idCard.matches(REGEX_IDCARD);
        //判断第18位校验值
        if(isIdCard){
            if(idCard.length()==18){
                try{
                    char[] charArray = idCard.toCharArray();
                    //前17位加权因子
                    int[] idCardWi =  {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for(int i=0;i<idCardWi.length;i++){
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        return false;
                    }
                }catch (Exception e){
                    return false;
                }
            }
        }
        return isIdCard;
    }
    /**
     * 校验车牌号码
     * @param carNo
     * @return
     */
    public static boolean isCarNo(String carNo){
        boolean isCar = true;
        Pattern pattern = Pattern.compile(REGEX_CARNO);
        Matcher matcher = pattern.matcher(carNo);
        isCar = matcher.matches();
        return isCar;
    }
    /**
     * 校验邮箱
     * @param email
     * @return
     */
    public static boolean isEmial(String email){
        boolean isEmial = true;
        if(email.matches(REGEX_EMIAL)){
            isEmial = false;
        }
        return isEmial;
    }

    /**
     * 校验手机号码
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile){
        boolean b = false;
        Pattern pattern = Pattern.compile(REGEX_MOBILE);
        Matcher matcher = pattern.matcher(mobile);
        b=matcher.matches();
        return b;
    }

    /**
     * 校验电话
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone){
        Pattern pattern1 = null,pattern2 = null;
        Matcher matcher = null;
        boolean b = false;
        pattern1 = Pattern.compile(REGEX_PHONE);//验证带区号的
        pattern2 = Pattern.compile(REGEX_PHONE1);//验证不带区号的
        if(phone.length()>9){
            matcher = pattern1.matcher(phone);
            b = matcher.matches();
        }else{
            matcher = pattern2.matcher(phone);
            b= matcher.matches();
        }
        return b;
    }

    public static boolean isPhoneOrMobile(String phoneStr){
        boolean isPhone =false;
        if(MessageUtils.isMobile(phoneStr)){ isPhone=true; }
        //校验电话
        if(MessageUtils.isPhone(phoneStr)){ isPhone = true; }
        return isPhone;
    }

}
