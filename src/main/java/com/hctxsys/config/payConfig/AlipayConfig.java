package com.hctxsys.config.payConfig;

//阿里支付配置类(公共参数)
public class AlipayConfig  {
	
	/** 公共参数  Start */
	// 1.请求网关地址 支付宝网关（固定） 正式环境
	public static String URL = "https://openapi.alipay.com/gateway.do";    
	// 2.APPID即创建应用后生成  正式环境 
	public static String APP_ID = "2018022502268543";   
	// 3.开发者应用私钥，由开发者自己生成 正式环境
	public static String APP_PRIVATE_KEY ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCVhMgrI+YrBDujS1JsXnBOj5OWeAbAaqnL04wWkeLxxHUQyGtuysxWBRE+28o50tsHkUzGculLSqpyTeW6/Kc37Idtx2ZgT7MHBuvvdrSHxbT7zYU1uQ4beiIib67Wi1kgCfSKLFzH/gtNL6ON8QZnS8Cg9AWYV3MfNFBNyXKK9KMnv8Y3KjpC6TQpjHVWss9z2qLBG0bk5iDMRmFwvZj4qtyieBtDjE5+2eSNdgCRfLdFpnXy5TClxtQW2IkOL9yzZ5yW7G56/8Fl6fCKd3c3BjGpwjXheGMlU1wuZRZmr7RNEKCwZwlSHKt4O5f5voQOS1lE8+545OC6P+50Sy/5AgMBAAECggEAGI4c/ZDAvTHNxYyaNbfww8Yqm9yMZ09dIV9Vs2M0HTaBb0cq9aASnNHqOiOIe6SYWg7PyAuZKsdRZMJMR0A1CicrOqkzs/+CzFWjq8kX7vBWP8BJ7z+8BGEzd+pVkWCDO5LG+WY9uo6LNprrRmVeAtrbANPyk0lRW7+QrKW3zQAMMgRir+rdjHa90iU0yd+ystwC0jpTolf/ziXi8iK6jcfk+ZzKa15O0yFo36M+kIDnPEqQK0XB/YBc/vNZU+2h/wXUkYUaO+iD4xbGfc9G1WCgFQjN06pDZk5by0cwNMN13gtjSQu8iOEWvDPAgeZRAskbRVhGmPpLmUwaZMS2cQKBgQD4HVW3CFujXdnrysfpTrH8yMVg/obEPf94MkNYNSUB3GyxDNBqYmrrOhgDTbErj7Z2xrlL9taszPBg32C84/V6Iamp9+u0IFNG9LZcDTjs4J1Kh56ClV1RAgpX6smMx/MA+mO47sWC58HsvwANwuS0fpItNzmwORbqFlLb/I9d7QKBgQCaRUTRmITeN/3jkjz/LGgh1f+Yni9tNJ80T/O9bcphS811QlE7I5GmNqWrqPG1vENSF5u8gVnD3t7n4CTyc9gbPub8pm6AoJWaD+eATjPkomR93kJc3+GIbowcv2jvWbhtjIno0P7kVBm5uMTh6yWRmTwPhkICqUteeO31pKM4vQKBgQCzQ8alevVy4KFOGvI7oG86+/iYLu5CJaVKO86qkaNOZ+tkNKT06GzlzdCBuoWSusATq1tABnVu3pei9mIXuACpoF7MfmMffueMccy8T5VcmKEsszYUlUSZ/gZpJStAe0sY3tBkUqgD/9z+Z7XrO3Aw9O46mxckOutScJSneyA+tQKBgCk4Tu4N6ZqSrWTsAx5PWDeKPXQi+Bsez1cj8cVwm32pymkFYj0CAP2/Zj7aslLTzc0Wf8c7bKe4WijsrBKYP2rwQLnLIoV94ts6Wqneu2Dgvycehu7X5yQUHZ3kyv4BJBUpUwQBK8oIaqNEKl8O/IfNwM2JGwQpBfllKHfVGMOlAoGAc4uBDpj6LbUiI+VbMW3VC7hS6hM0zh5VyBTdeiPqONfESLn0MO0qG8r3w3ULLDS3mAWoECS5dFLCm+L0+tvOkVKcxogisrowES3e5WFkFmQ2UUAHZHJXJ+agrfi4TicRs+FpGdmi0NsUj3EW9B5mUUKO4JnTNZvcZeSN9GfeR8E=";
	// 6.支付宝公钥，由支付宝生成 正式环境
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2aNHbZwD2CfuU1+b6ABK9CJv3qm5vl6QvR5hy4ncYL4IDNL4gSZtWJd6vg9W0wSAlPUaxI3rMMLtEeAI2zjEW+yrQs/pGd4gEmbwdhdFwb+dCtQLRHjC8dM6qtf6ss/HtMUVwukT5sasN18hVCg1sKPFdLlkWxyh9Z5Rj1jmF/B8kcb8Ku3C9Ce7CnOJBu+a+2UJ61Z/EEVHI+u4TTLqYdla7rARyyWC9LaFRfG5rr/6g+04Zyym5+fJ9NPm24TByUp6a01xiByuS0ugxZX73jxG/iRgpdMSHm0H4XVGkpN6nNAo8QbsACYqX0BGkgpPPtqOChpA8lEFX7w5hYRU+wIDAQAB";
	// 4.参数返回格式，只支持json
	public static String FORMAT = "json";
	// 5.请求和签名使用的字符编码格式，支持GBK和UTF-8
	public static String CHARSET = "utf-8";
	// 7.商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
	public static String SIGN_TYPE = "RSA2";	
	// 8.回调地址 服务器
	public static String NOTIFY_URL = "http://www.lnhuacai.com/api/ali/aLiPayNotify";
	
//	// 1.请求网关地址 支付宝网关（固定） 沙箱环境
//	public static String URL = "https://openapi.alipaydev.com/gateway.do";
//	// 2.APPID即创建应用后生成  沙箱环境
//	public static String APP_ID = "2016091500520620";   
//	// 3.开发者应用私钥，由开发者自己生成 沙箱环境
//	public static String APP_PRIVATE_KEY ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCf/nQAEjRTYO8/zlDLly/RpvTiwE1S3X5q4FZkG+lHVWFdMTny7IKecoQMwF7JGZ1E8YyTByuOhVQnPmi2Jq5G7y8hWjda/asDePHqqXGZpdd06naBfn0Od/JJvkZhy1BlIHPfozvQdHpT0ZhE6PYKx5Pm3G7EjHf0eLgXp3QC4k7QkArLc7kHe7+HSImZkET0P3kGdN3GC/SX7QARRhczEHFx9GLru3+nCB/CxB00xjv5MBwIzd/lTMF5ZTw7NMM8A+tcqdqDmQeMcZpNbwdcm2OcuUs9YMUuYhJrG6nyTv6Hq1xcArQB+Ju/0iKDzz06BPKra2/FGhgd4RXk3ouPAgMBAAECggEARWQO25kJqL/6BCpvUF2aF8vZmh7dans2QRRbY7/XRWD6gXjAMWPyotX3/6/PbFj3pNc+qOYLNh0Nipe+woG0RH+oaUa/i29JpI/oHGaxJnqPH94w16kMhCHxq5oi3HdZvwJ8PxqE7rwV8hSNfhWr4LD+rLoeMJDb1eQls9ykrDrbXNamvCvbhaVGQzR/oZTyBAq4Z7cH4tCdz9eqE4gkhtny7qgxwrFa4+pJL3OjdOCO69kYGiKQ7gVw6Wk+TRTB4b21x+XESCf3yRWHRwBQFXjLjzCuJ9ED0q8k2NBRMJwB3A+Im2/YGdkLJ2v+Gln64OTEZ70seZiX0pAvj7KKQQKBgQDRWdSb+lhbxKrIuh9rP/uUvCGJtlbo1xBWk8bSRI0nN05aaENHj7htOpbChRky20ME0Fa3vlMfqdECQWqrUMdRgLuJRatPDGNFOjs1/93dpVTDlgGsDl4DTEcho3G25WiehcthwlFhCdxxVSPbMvatA9Y+AHTVDaP7lvrfGU+54wKBgQDDpR2JBKww74Ro7VfIVl6LiTRf+uKEhCAkUQ0WbRb26NKAcbTZjNXF7d+OuY2i3qDLZwedCeWl7ZwuC25tjatYmVmbm9NrkMAG56wMkz6sAWjEs2MhBDgv33wSEXhuie9b+MiqBQu3nDvSzCyuASfq07DbB+VYlY0nvRUHUGcHZQKBgHR/3tal68G1oaxuFsniIyhTwP4XoCmWdJsZRU0BpOMr3chluPr7uY/fSMIauzkX1yW4omlB2pQGNi9hI/EN1L1APBxZzskAhcWicAzOxDJxrMfilC+hFyQSsIrBn6g95YhBwDo4nny3thrDXF7A75C8T97U9i8Rli2NiTHZrgotAoGBAKTOp7WvmPnUGp9lKChcEWLL3rHnnv9wab8OA89bcBfogsKuhmEo0T/dpRAO4PBNR/hwPyqblreTFJh5CroAuFF3RNPfjVa0Zf6rE/S36Phua3offybl7grt4Q87Zk7/pQG97hPDEl7GnmJxmkWuNZkMYJPx79ISkQjjsB4sLc49AoGAR4NTRiD1/Kfh6XX7iGgNtKTB2JSOu8tIEbKNcSSq0l6Bl8l+rhfceCwNcW9YJg51XIZaO1n+RsyqbLdgL3jyWxaR965aqEXm7mmOWFz0xf07bZkbwHFItxohG//ho71ErFnaPTFcy7wDiARNKzw0fKmKJfmjBWk19UnIRXp5gIc=";
//	// 6.支付宝公钥，由支付宝生成 沙箱环境
//	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw2BckHSzY8GRoBhgh7SRZYHsxjWAjYAaVRmOASJgUZtUEBNg3yJoWNd0zOWb4RXLPWvV2zTGOdoN08/I1Y8yRf44xjoa0WFOhYoFReYV0jJEhsbP6bqvpRAxhB9RN7xkBPQdRiCrDV2wpgNar+t1W2cKNE9P+aWRblREHlbsmEggJFnFyjrMglGOKHCNjap3fii3GG3xXm4cJ+9/6jdWWYZzYhFyQwfSCBBCHqNqMR70CONNZJJzLNUk1ETDkWeXEgY9n9oNfUhUqo9+ozLKZexR/A1B4VtJB0aqmpeF8ICxKKrBrNSp7RP+W9jO6N5sXAo4PuOOZ8BmH5jvIfkSPQIDAQAB";
//	/** 公共参数  End */
//	// 8.回调地址 本地
//	public static String NOTIFY_URL = "http://113.235.127.69:80/api/ali/aLiPayNotify";
	    
	
}