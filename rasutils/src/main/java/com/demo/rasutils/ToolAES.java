package com.demo.rasutils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import org.apache.log4j.Logger;

/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 17:50
 * @version: V1.0
 * @detail: AES安全编码组件
 * 高级数据加密标准---AES：由于DES的问题所以产生了AES,像是DES的升级，密钥建立时间短，灵敏性好，内存要求低，被广泛应用
 * 说明：
 *
 * 对于java.security.InvalidKeyException: Illegal key size or default
 * parameters异常， 去掉这种限制需要下载Java Cryptography Extension (JCE) Unlimited Strength
 * Jurisdiction Policy Files， 下载包的readme.txt
 * 有安装说明。就是替换${java_home}/jre/lib/security/
 * 下面的local_policy.jar和US_export_policy.jar
 **/
public class ToolAES {
    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(ToolAES.class);

    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法 / 工作模式 / 填充方式 Java 6支持PKCS5Padding填充方式 Bouncy
     * Castle支持PKCS7Padding填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 转换密钥
     *
     * @param key
     *            二进制密钥
     * @return Key 密钥
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        // 实例化AES密钥材料
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);

        return secretKey;
    }

    /**
     * 解密
     *
     * @param data
     *            待解密数据
     * @param key
     *            密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);

		/*
		 * 实例化 使用PKCS7Padding填充方式，按如下方式实现 Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		 */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);

        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data
     *            待加密数据
     * @param key
     *            密钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);

		/*
		 * 实例化 使用PKCS7Padding填充方式，按如下方式实现 Cipher.getInstance(CIPHER_ALGORITHM,
		 * "BC");
		 */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);

        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 生成密钥 <br>
     *
     * @return byte[] 二进制密钥
     * @throws Exception
     */
    public static byte[] initKey() throws Exception {
        // 实例化
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

		/*
		 * AES 要求密钥长度为 128位、192位或 256位
		 */
        kg.init(128); //因为java默认只支持128位的，如果需要支持更长的秘钥，需要再下载另外的jar包，替换jre中security/下的同名jar
        //下载地址:http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html

        // 生成秘密密钥
        SecretKey secretKey = kg.generateKey();

        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }

    /**
     * 给待加密的字符串生成加密字符串
     * @param original 原文
     * @return 密文
     */
    public static String encrypt(String original){
        byte[] authTokenByte ;
        try{
            authTokenByte = original.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
            log.error("字符串数据转byte异常：content = " + original);
            return null;
        }
        Prop p = PropKit.use("conf.properties");
        String securityKey = p.get("config.securityKey");
        byte[] keyByte = Base64.decodeBase64(securityKey);

        byte[] securityByte = null;
        try {
            securityByte = encrypt(authTokenByte,keyByte);
        }catch (Exception e){
            e.printStackTrace();
            log.error("加密数据异常：content = " + original + "，securityKey = " + securityKey);
            return null;
        }
        String securityCookie = Base64.encodeBase64String(securityByte);

        //认证cookie Base64编码
        try{
            securityCookie = ToolString.encode(securityCookie);
        }catch (Exception e){
            log.error("数据Base64编码异常：content = " + original + "，securityKey = " + securityKey);
            return null;
        }
        return securityCookie;
    }

    /**
     * 解密加密的字符串
     * @param cryptograph 密文
     * @return 原文
     */
    public static String decrypt(String cryptograph){
        //1.Base64 解码cookie令牌
        try{
            cryptograph = ToolString.decode(cryptograph);
        }catch (Exception e){
            log.error("Base64解码异常：content = " + cryptograph);
            return null;
        }
        //2.解密cookie令牌
        byte[] securityByte = Base64.decodeBase64(cryptograph);
        Prop p = PropKit.use("conf.properties");
        String securityKey = p.get("config.securityKey");
        byte[] keyByte = Base64.decodeBase64(securityKey);

        byte[] dataByte = null;
        try{
            dataByte = decrypt(securityByte,keyByte);
        }catch (Exception e){
            log.error("解密数据异常：content = " + cryptograph + "，securityKey = " + securityKey);
            return null;
        }
        return new String(dataByte);
    }

    /**
     * 测试
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
		/*String inputStr = "asdf";
		byte[] inputData = inputStr.getBytes();
		System.err.println("原文:\t" + inputStr);

		// 初始化密钥
		byte[] key = initKey();
        System.err.println("密钥:\t" + Base64.encodeBase64String(key));

		// 加密
		inputData = encrypt(inputData, key);
		System.err.println("加密后:\t" + Base64.encodeBase64String(inputData));

		// 解密
		byte[] outputData = decrypt(inputData, key);

		String outputStr = new String(outputData);
		System.err.println("解密后:\t" + outputStr);*/

        String inputStr = "TEST";
        System.err.println("原文:\t" + inputStr);

        String cryptograph = encrypt(inputStr);
        System.err.println("密文:"+cryptograph);

        String original = decrypt(cryptograph);
        System.err.println("解密后："+original);

    }
}
