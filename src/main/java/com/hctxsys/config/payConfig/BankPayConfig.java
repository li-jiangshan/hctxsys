package com.hctxsys.config.payConfig;

//阿里支付配置类
public class BankPayConfig  {
	
	// 商户密钥
	public static String SECRET_KEY = "Huacaitianxia018";
	
	// 获取公钥
	public static String FB_PUB_KEY_URL = "http://121.15.180.72/CmbBank_B2B/UI/NetPay/DoBusiness.ashx";
	
	// 一网通单笔查询路径
	public static String BANK_SEARCH_SINGLE_URL ="http://121.15.180.66:801/NetPayment_dl/BaseHttp.dll?QuerySingleOrder";
	
	// 退款url
	public static String DO_REFUND_URL = "http://121.15.180.66:801/NetPayment_dl/BaseHttp.dll?DoRefund";
	
	// 查询退款url
	public static String SEARCH_REFUND_URL = "http://121.15.180.66:801/netpayment_dl/BaseHttp.dll?QuerySettledRefund";
	
	// 编码:UTF-8
	public static String CHARSET = "UTF-8";
	
//	// 支付方式Map
//	public static HashMap<Integer,String> PAYNAME;
//	static {
//		PAYNAME = new HashMap<Integer,String>();
//		
//		PAYNAME.put(1, "余额支付");
//		PAYNAME.put(2, "微信支付");
//		PAYNAME.put(3, "支付宝支付");
//		PAYNAME.put(4, "一网通支付");
//	}
	
	// 商户分行号
	public static String BRANCH_NO = "0411";
	
	// 商户号
	public static String MERCHANT_NO = "000017";
	
	// 成功支付结果通知地址
	public static String PAY_NOTICE_URL = "http://113.235.113.113:80/api/bank/paySuccess";
	
	// 返回商家地址
	public static String RETURN_URL = "http://113.235.113.113:80/api/pay/successReturn";
	
	// 过期时间跨度
	public static String EXPIRE_TIME_SPAN = "30";
	
	// 允许支付的卡类型
	public static String CARD_TYPE = "";
	
	// 成功签约结果通知地址
	public static String SIGN_NOTICE_URL = "http://113.235.113.113:80/api/bank/signSuccess";
	
	// 一网通版本
	public static String VERSION = "1.0";
	
	// sign方式
	public static String SIGN_TYPE = "SHA-256";
	
	// 操作员号
	public static String OPERATOR_NO = "9999";
	
	// 操作员密码
	public static String PWD = "000017";
	
	
	
}
