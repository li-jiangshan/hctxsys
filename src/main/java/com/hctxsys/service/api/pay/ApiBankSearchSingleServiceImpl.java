package com.hctxsys.service.api.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.hctxsys.config.payConfig.BankPayConfig;
import com.hctxsys.util.payUtil.BankPayUtils;
import com.hctxsys.vo.api.ApiPayVo;

import net.sf.json.JSONObject;

@Service("apiBankSearchSingleService")
public class ApiBankSearchSingleServiceImpl {

	public JSONObject apiBankSearchSingle(String jsonParam, String url, String charset) {
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
		// TODO 具体展示哪些数据未定
		JSONObject resData = JSONObject.fromObject(JSONObject.fromObject(result.toString()).get("rspData"));
		return resData;
	}
	
	public String setSearchReqData(String orderNo ,String payDate) {
		JSONObject jsonRequestData = new JSONObject();
		JSONObject requestData = new JSONObject();
		
		Date date = new Date();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		requestData.put("dateTime", dateTimeFormat.format(date).toString());
		requestData.put("branchNo", BankPayConfig.BRANCH_NO);
		requestData.put("merchantNo", BankPayConfig.MERCHANT_NO);
		requestData.put("type", "B");
		requestData.put("bankSerialNo", "");
		requestData.put("date", payDate);
		requestData.put("orderNo", orderNo);
		requestData.put("operatorNo", "");
		
		jsonRequestData.put("version", "1.0");
		jsonRequestData.put("charset", "UTF-8");
		jsonRequestData.put("sign", BankPayUtils.sign(requestData.toString(), BankPayConfig.SECRET_KEY, BankPayConfig.CHARSET));
		jsonRequestData.put("signType", "SHA-256");
		
		
		jsonRequestData.put("reqData", requestData);
		
		return jsonRequestData.toString();
		
	}
}
