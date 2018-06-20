package com.hctxsys.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtils {
	
    /**
     * 获取订单号
     * @param flag CZ: 充值  UD:会员升级  NZ:购买产品
     * @return
     */
	public static String getOrderNo(String flag){
		
		Date date = new Date();
		//转换提日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String data = dateFormat.format(date);
		//四位随机数
  		String verificationCode = String.valueOf(Math.round(Math.random()*10000));
  		String order = flag + data + verificationCode;
	    return order;
	}
	
}
