package com.example.administrator.myapplication2;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 128位加密解密演示程序
 * @author Administrator
 * @version 1.0.0
 * @since 2014年10月11日17:52:07
 */
public class AESTest
{
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		String content = "123";// 待加密的内容
		String pwd = "qiu";// 密匙
		
		String en = myEncrypt(pwd,content);
		System.out.println("密文：" + en);
		
		String de = myDencrypt(pwd, en);
		System.out.println("原文：" + de);
	}

	/**
	 * 解密
	 * @param strKey 密匙
	 * @param strIn  待加密的内容
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static String myDencrypt (String strKey,String strIn) throws Exception
	{
		SecretKeySpec skeySpec = getKey(strKey);
		//算法模式。 ECB（Electronic Code Book，电子密码本）模式
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte [] encrypt = Base64.decode(strIn);

		byte[] original = cipher.doFinal(encrypt);
		String originalString = new String(original);
		
		return originalString;
	}

	/**
	 * 加密
	 * @param strKey 密匙
	 * @param strIn  待加密的内容
	 * @return 加密后的内容
	 * @throws Exception 
	 */
	public static String myEncrypt (String strKey, String strIn ) throws Exception
	{
		SecretKeySpec skeySpec = getKey(strKey);
		//算法模式。 ECB（Electronic Code Book，电子密码本）模式
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(strIn.getBytes());

		return Base64.encodeBytes(encrypted);
	}
	
	/**
	 * 获得处理过的密匙
	 * @param strKey 密匙
	 * @return 处理过的密匙
	 * @throws Exception
	 */
	private static SecretKeySpec getKey(String strKey) throws Exception 
	{
		byte[] arrBTmp = strKey.getBytes();
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) 
			arrB[i] = arrBTmp[i];
		
		SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");

		return skeySpec;
	}
}
