package com.demo.md5utils;

import static com.demo.md5utils.Md5utils.generateSalt;

import static com.demo.md5utils.Md5utils.getMd5;
import static com.demo.md5utils.Md5utils.verify;

public class Md5utilsTests {

	public static void main(String[] args) {
		// 原文
		String plaintext = "123456";
		//  plaintext = "123456";
		System.out.println("原始：" + plaintext);
		System.out.println("普通MD5后：" + getMd5(plaintext));

		// 获取加盐后的MD5值
		String s = generateSalt();
		String ciphertext = getMd5(plaintext, s);
		System.out.println("加盐后MD5：" + ciphertext);
		System.out.println("密码盐：" + s);
		System.out.println("是否是同一字符串:" + verify(plaintext, ciphertext));
	}

}
