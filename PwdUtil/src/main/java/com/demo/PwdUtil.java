package com.demo;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 18:47
 * @version: V1.0
 * @detail:
 **/
public class PwdUtil {
    //aes加解密
    private static String aesKey = "ddfdgd5?gh2e@r#%r33t4rtgdfg^$%^#$_/dfg&";
    private final static String AES = "AES";
    //des加解密
    private final static String DES = "DES";
    private static String desKey = "ddfdfdfyu3/fdfd?34d!^rth45rbeBDHdfdfB_34&";

    /**
     * MD5加密
     *
     * @param str
     */
    public static String md5(String str) {
        try {
            return str = DigestUtils.md5Hex(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("MD5加密失败");
            return null;
        }
    }

    /**
     * SHA-1加密
     *
     * @param str 待加密文本
     */
    public static String sha1(String str) {
        return shaHex(str, "1");
    }

    /**
     * SHA-256加密
     *
     * @param str 待加密文本
     */
    public static String sha256(String str) {
        return shaHex(str, "256");
    }

    /**
     * SHA-512加密
     *
     * @param str 待加密文本
     */
    public static String sha512(String str) {
        return shaHex(str, "512");
    }

    /**
     * SHA-* 通用加密方法
     *
     * @param str  加密内容
     * @param type 类型 1,256,512
     * @return String
     */
    private static String shaHex(String str, String type) {
        try {
            if (type == null || "1".equals(type)) {
                str = DigestUtils.sha1Hex(str.getBytes("UTF-8"));
            } else if ("256".equals(type)) {
                str = DigestUtils.sha256Hex(str.getBytes("UTF-8"));
            } else if ("512".equals(type)) {
                str = DigestUtils.sha512Hex(str.getBytes("UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("SHA-" + type + "加密失败");
        }
        return str;
    }

    /**
     * 字符串 base64编码
     *
     * @param str
     * @return
     */
    public static String strToBase64(String str) {

        try {
            return Base64.encodeBase64String(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("BASE64编码失败"+e);
        }
        return null;
    }

    /**
     * base64编码的字符串解码
     *
     * @param b64Str
     * @return
     */
    public static String base64ToStr(String b64Str) {
        try {
            return new String(Base64.decodeBase64(b64Str), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("BASE64解码失败"+e);
        }
        return null;
    }


    /**
     * 字符串 base64编码（将/ ,+,= 替换成 _a,_b,_c）
     *
     * @param str
     * @return
     */
    public static String strToBase64UrlEncode(String str) {

        try {
            return Base64.encodeBase64String(str.getBytes("UTF-8")).replaceAll("/", "_a").replaceAll("\\+", "_b").replaceAll("=", "_c");
        } catch (UnsupportedEncodingException e) {
            System.out.println("BASE64编码失败"+e);
        }
        return null;
    }

    /**
     * base64编码的字符串解码 （将 _a,_b,_c 还原成 /,+,= ）
     *
     * @param b64Str
     * @return
     */
    public static String base64ToStrUrlDecode(String b64Str) {
        try {
            return new String(Base64.decodeBase64(b64Str.replaceAll("_a", "/").replaceAll("_b", "+").replaceAll("_c", "=")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("BASE64解码失败"+e);
        }
        return null;
    }

    /**
     * Hex编码(16进制)
     *
     * @param str
     * @return String
     */
    public static String encodeHexTest(String str) {
        try {
            return Hex.encodeHexString(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("HEX编码失败"+e);
        }
        return null;
    }

    /**
     * Hex解码(16进制)
     *
     * @param str
     * @return String
     */
    public static String decodeHexTest(String str) {
        Hex hex = new Hex();
        try {
            return new String((byte[]) hex.decode(str));
        } catch (DecoderException e) {
            e.printStackTrace();
            System.out.println("HEX解码失败"+e);
        }
        return null;
    }

    private static final String SEPARATOR = "::";
    private static final int SALT_BYTE_SIZE = 8;
    private static final int HASH_BYTE_SIZE = 16;
    private static final int PBKDF2_ITERATIONS = 1024;

    /**
     * 密码自动加盐加密
     *
     * @param password 用户的密码
     * @return 加密后的密码（和盐）
     */
    public static String createHash(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        byte[] hash = pbkdf2(password.toCharArray(), salt, HASH_BYTE_SIZE);
        return toHex(salt) + SEPARATOR + toHex(hash);
    }

    /**
     * 验证密码是否正确
     *
     * @param password    用户输入的原始密码
     * @param correctHash 带盐的密码
     * @return 是否验证成功
     */
    public static boolean validateHash(String password, String correctHash) {
        String[] params = correctHash.split(SEPARATOR);
        byte[] salt = fromHex(params[0]);
        byte[] hash = fromHex(params[1]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, hash.length);
        return slowEquals(hash, testHash);
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int bytes) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, PBKDF2_ITERATIONS, bytes * 8);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("密码校验失败"+e);
        }
        return null;
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++)
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }


    /**
     * aes加密
     *
     * @param content 待加密内容
     * @return String
     */
    public static String aesEncrypt(String content) {
        return aesEncrypt(content, aesKey);
    }

    /**
     * aes加密
     *
     * @param content 待加密内容
     * @param aesKey  秘钥
     * @return String
     */
    public static String aesEncrypt(String content, String aesKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(aesKey.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES);// 创建密码器
            byte[] byteContent = content.getBytes("UTF-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return strToBase64(parseByte2HexStr(result)); // 加密
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AES加密失败"+e);
        }
        return null;
    }

    /**
     * aes解密
     *
     * @param content 待解密内容
     * @return String
     */
    public static String aesDecrypt(String content) {
        return aesDecrypt(content, aesKey);
    }

    /**
     * aes解密
     *
     * @param content 待解密内容
     * @param aesKey  秘钥
     * @return String
     */
    public static String aesDecrypt(String content, String aesKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(aesKey.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(parseHexStr2Byte(base64ToStr(content)));
            return new String(result); // 加密
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AES解密失败"+e);
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将字符串转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    /**
     * des 加密
     */
    public static String desEncrypt(String content) {
        return desEncrypt(content, desKey);
    }

    /**
     * des 加密
     */
    public static String desEncrypt(String data, String key) {
        try {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key.getBytes());

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
            byte[] bt = cipher.doFinal(data.getBytes("UTF-8"));
            return strToBase64(parseByte2HexStr(bt)); // 加密
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DES加密失败"+e);
        }
        return null;
    }

    /**
     * des 解密
     *
     * @param content
     */
    public static String desDecrypt(String content) {
        return desDecrypt(content, desKey);
    }

    /**
     * des 解密
     *
     * @param data
     * @param key  加密键byte数组
     */
    public static String desDecrypt(String data, String key) {
        try {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key.getBytes());

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

            byte[] bt = cipher.doFinal(parseHexStr2Byte(base64ToStr(data)));
            return new String(bt); // 加密
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DES解密失败"+e);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(aesEncrypt("admin.2018"));
    }
}
