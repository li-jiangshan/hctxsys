package com.hctxsys.util;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

public class Base64Utils {
    // 图片转化成base64字符串
    public static String GetImageStr(String filePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = filePath;// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Base64 base64=new Base64();
//        Base64.Encoder encoder = Base64.getEncoder();
        // 对字节数组Base64编码
        return new String( base64.encode(data));// 返回Base64编码过的字节数组字符串
    }
    // base64字符串转化成图片
    public static boolean GenerateImage(String imgStr,File file) { // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        try {
            // Base64解码
//            Base64.Decoder decoder = Base64.getDecoder();
            Base64 base64 = new Base64();
            byte[] b = base64.decode(imgStr.getBytes());
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(b);
            outputStream.flush();
            outputStream.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
