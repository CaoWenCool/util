package com.demo.randomutils;
import java.util.Random;
import java.util.UUID;

/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 17:08
 * @version: V1.0
 * @detail: 随机数
 **/
public class RandomUtils {
    /**
     * Array of numbers and letters of mixed case. Numbers appear in the list
     * twice so that there is a more equal chance that a number will be picked.
     * We can use the array to get a random number or letter by picking a random
     * array index.
     */
    private static char[]	numbersAndLetters	= ("0123456789abcdefghijklmnopqrstuvwxyz"
            + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
            .toCharArray();

    /**
     * 根据指定长度随机生成小写字母
     *
     * @param length
     *            长度
     * @return 指定长度的随机小写字母字符串
     */
    public static String randomLowerWords(int length) {
		/*
		 * 0~9的ASCII为48~57 A~Z的ASCII为65~90 a~z的ASCII为97~122
		 */
        StringBuilder sb = new StringBuilder();
        Random randData = new Random();

        int data = 0;
        for (int i = 0; i < length; i++) {
            data = randData.nextInt(26) + 97;// 保证只会产生97~122之间的整数
            sb.append((char) data);
        }
        return sb.toString();
    }

    /**
     * 根据指定长度随机生成大写字母
     *
     * @param length
     *            长度
     * @return 指定长度的随机大写字母字符串
     */
    public static String randomUpperWords(int length) {
		/*
		 * 0~9的ASCII为48~57 A~Z的ASCII为65~90 a~z的ASCII为97~122
		 */
        StringBuilder sb = new StringBuilder();
        Random randData = new Random();

        int data = 0;
        for (int i = 0; i < length; i++) {
            data = randData.nextInt(26) + 65;// 保证只会产生97~122之间的整数
            sb.append((char) data);
        }
        return sb.toString();
    }

    /**
     * 根据指定长度随机生成数字
     *
     * @param length
     *            长度
     * @return 指定长度的随机数字
     */
    public static String randomNumbers(int length) {
		/*
		 * 0~9的ASCII为48~57 A~Z的ASCII为65~90 a~z的ASCII为97~122
		 */
        StringBuilder sb = new StringBuilder();
        Random randData = new Random();

        int data = 0;
        for (int i = 0; i < length; i++) {
            data = randData.nextInt(10);// 仅仅会生成0~9
            sb.append(data);
        }
        return sb.toString();
    }

    /**
     * 生成32位UUID字符，去除字符'-'
     *
     * @return 32位随机UUID字符串
     */
    public static String randomCustomUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();

        return uuidStr.replaceAll("-", "");
    }

    /**
     * 生成36位UUID字符
     *
     * @return 36未随机UUID字符串
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }


    public static String randomString(int length) {
        if (length < 1) {
            return null;
        }
        // Create a char buffer to put random letters and numbers in.
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[new Random().nextInt(71)];
        }
        return new String(randBuffer);
    }
}
