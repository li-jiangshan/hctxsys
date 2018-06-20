package com.hctxsys.util.payUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.hctxsys.config.payConfig.BankPayConfig;
import com.hctxsys.vo.api.JsonResult;

import net.sf.json.JSONObject;

public class BankPayUtils {
	/**
	 * 设置一网通请求参数
	 */
	public static String getBankReqDate(JSONObject jsonRequestData) {
		
		String sign = sign(jsonRequestData.toString(), BankPayConfig.SECRET_KEY, BankPayConfig.CHARSET);
		JSONObject resultMap = new JSONObject();
		resultMap.put("version", BankPayConfig.VERSION);
		resultMap.put("charset", BankPayConfig.CHARSET);
		resultMap.put("sign", sign);
		resultMap.put("signType", BankPayConfig.SIGN_TYPE);
		resultMap.put("reqData",jsonRequestData);
		
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * 获取一网通公钥
	 */
	public static String GetFbPubKey() {
		String url = BankPayConfig.FB_PUB_KEY_URL;
		String charset = "UTF-8";
		
		Date day=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		String xmlRequest = "{";
		xmlRequest += "\"dateTime\":\"" + df.format(day).toString() + "\",";
		xmlRequest += "\"txCode\":\"FBPK\",";
		xmlRequest += "\"branchNo\":\"" + BankPayConfig.BRANCH_NO + "\","; //商户分行号
		xmlRequest += "\"merchantNo\":\"" + BankPayConfig.MERCHANT_NO + "\""; //商户号
		xmlRequest += "}";
		String sign = sign(xmlRequest, BankPayConfig.SECRET_KEY, BankPayConfig.CHARSET);
		
		String jsonParam = "{";
		jsonParam += "\"version\":\"1.0\",";
		jsonParam += "\"charset\":\"UTF-8\",";
		jsonParam += "\"sign\":\"" + sign + "\",";
		jsonParam += "\"signType\":\"SHA-256\",";
		jsonParam += "\"reqData\":";
		jsonParam += xmlRequest;
		jsonParam += "}";
		
		OutputStreamWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL httpUrl = new URL(url);
			HttpURLConnection urlCon = (HttpURLConnection) httpUrl.openConnection();
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setReadTimeout(5 * 1000);
			out = new OutputStreamWriter(urlCon.getOutputStream(), charset);// 指定编码格式
			out.write("jsonRequestData=" + jsonParam);
			out.flush();

			in = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), charset));
			String str = null;
			while ((str = in.readLine()) != null) {
				result.append(str);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		JSONObject resJson = JSONObject.fromObject(result.toString());
		JSONObject rspData = JSONObject.fromObject(resJson.get("rspData"));
		return (String) rspData.get("fbPubKey");
	}
	
	/**
	 * 根据待签字sign，招行签字sign和招行公钥，验证是否为招行请求
	 */
	public static boolean isValidSignature(String strToSign, String strSign, String publicKey) {
		final Base64 base64 = new Base64();
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = base64.decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

			signature.initVerify(pubKey);
			signature.update(strToSign.getBytes("UTF-8"));

			boolean bverify = signature.verify(base64.decode(strSign));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 根据reqData信息生成已签字的sign，用于支付
	 */
	public static String sign(String reqDataJSON, String secretKey, String charset) {
		StringBuffer buffer = new StringBuffer();

		try {
			JSONObject json = JSONObject.fromObject(reqDataJSON);
//			JSONObject json = new JSONObject();
			List<String> keyList = sortParams(json);
			for (String key : keyList) {
				buffer.append(key).append("=").append(json.get(key)).append("&");
			}
			buffer.append(secretKey);// 商户密钥
			// 创建加密对象
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			// 传入要加密的字符串,按指定的字符集将字符串转换为字节流
			messageDigest.update(buffer.toString().getBytes(charset));
			byte byteBuffer[] = messageDigest.digest();

			// 將 byte转换为16进制string
			StringBuffer strHexString = new StringBuffer();
			for (int i = 0; i < byteBuffer.length; i++) {
				String hex = Integer.toHexString(0xff & byteBuffer[i]);
				if (hex.length() == 1) {
					strHexString.append('0');
				}
				strHexString.append(hex);
			}
			return strHexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 根据reqData信息生成待签字的sign，用于验证招行请求
	 */
	public static String getStrToSign(String reqDataJSON) {
		StringBuffer buffer = new StringBuffer();
		
		JSONObject json = JSONObject.fromObject(reqDataJSON);
//		JSONObject json = new JSONObject();
		List<String> keyList = sortParams(json);
		for (String key : keyList) {
			buffer.append(key).append("=").append(json.get(key)).append("&");
		}
		return buffer.toString().substring(0, buffer.length() - 1);
	}
	/**
	 * 对参数排序
	 */
	public static List<String> sortParams(JSONObject json) {
		List<String> list = new ArrayList<String>();
		Iterator it = json.keys();
		while (it.hasNext()) {
			list.add((String) it.next());
		}
		Collections.sort(list, new Comparator<String>() {
			public int compare(String o1, String o2) {
				String[] temp = { o1.toLowerCase(), o2.toLowerCase() };
				Arrays.sort(temp);
				if (o1.equalsIgnoreCase(temp[0])) {
					return -1;
				} else if (temp[0].equalsIgnoreCase(temp[1])) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		return list;
	}
}
