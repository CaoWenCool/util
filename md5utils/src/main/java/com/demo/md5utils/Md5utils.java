package com.demo.md5utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
/**
 * MD5工具类
 */
public class Md5utils {
    /**
     * 增加复杂度,控制暴力破解
     *
     * @param str
     * @return
     */
    public static String getTKMd5(String str) {
//        return getMd5("6%TK^" + str + "6.*x");
        return getMd5(str);
    }

    /**
     * 获取字符串md5
     *
     * @param str
     * @return
     */
    public static String getMd5(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
//            log.error(e.toString());
            return str;
        }
        md.update(str.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 加盐MD5
     *
     * @param password 密码铭文
     * @param salt     密码盐
     * @return 密码密文
     */
    public static String getMd5(String password, String salt) {
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 生成密码盐
     *
     * @return 密码盐
     */
    public static String generateSalt() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(password + salt).equals(new String(cs1));
    }


//    public static void main(String[] args) {
//        // 原文
//        String plaintext = "123456";
//        //  plaintext = "123456";
//        System.out.println("原始：" + plaintext);
//        System.out.println("普通MD5后：" + getMd5(plaintext));
//
//        // 获取加盐后的MD5值
//        String s = generateSalt();
//        String ciphertext = getMd5(plaintext, s);
//        System.out.println("加盐后MD5：" + ciphertext);
//        System.out.println("密码盐：" + s);
//        System.out.println("是否是同一字符串:" + verify(plaintext, ciphertext));
//    }



}
