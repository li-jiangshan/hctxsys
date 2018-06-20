package com.hctxsys.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @ClassName:CreateQrCode
 * @Author:li
 * @CreateDate:2018年4月23日
 */
public class CreateQrCode {

	/**
	 * 创建二维码
	 * 
	 * @param uid
	 * @param url
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String createQrCode(String uid, String url) {
		int width = 259;
		int height = 259;
		String format = "png";
		// 定义二维码的参数
		HashMap map = new HashMap();
		// 设置编码
		map.put(EncodeHintType.CHARACTER_SET, "utf-8");
		// 设置纠错等级
		map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		map.put(EncodeHintType.MARGIN, 7);

		try {
			// 生成二维码
			BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);
			File file = new File("D:/huacai/uploads/usercode/code" + backMD5(uid) + ".png");
			if (!file.exists()) {
				file.mkdirs();
			}
			Path path = file.toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, path);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "code" + backMD5(uid) + ".png";
	}

	/**
	 * MD5加密
	 * 
	 * @param inStr
	 * @return
	 */
	public static String backMD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
