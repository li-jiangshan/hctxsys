package com.hctxsys.config.payConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.hctxsys.config.weixinPay.WXPayConfig;

//微信配置类
public class MyWXPayConfig implements  WXPayConfig   {
	
	private byte[] certData;

    public MyWXPayConfig() throws Exception {
        String certPath = "D:/apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    //应用Id
    public String getAppID() {
        return "wx0333c2cf790adeda";
    }
    //微信商户号
    public String getMchID() {
        return "1503414621";
    }
    //微信的密钥
    public String getKey() {
        return "VmE99atyd8VnTcobWFqoxMwzCx68sMlc"; // 正式密钥
//        return "217e6ae624986cdda6388a5f3555636f"; //沙箱密钥
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
