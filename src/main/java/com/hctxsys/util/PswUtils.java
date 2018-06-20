package com.hctxsys.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PswUtils {
    final static String KEY="kkVg{EyPWCy:iJ*A-ZW&B+N%WlM^xHEqZGrVG<{,}J)gk``.;u|qD~d!(?\"zj;@C";
    /**
     * 根据时间生成盐值
     * @return
     */
    public static String getSalt() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = f.format(date);//时间
        String time = DateUtils.getTime(dateTime, f);//时间戳
        String timeMD5 = DigestUtils.md5Hex(time);//加密后的时间戳
        String salt = timeMD5.substring(0, 3);//截取前三位成为盐
        return salt;
    }
    public static String getCipher(String password,String salt) {
        String sha1=DigestUtils.sha1Hex(password);//SHA1加密明文密码
        String md5 = DigestUtils.md5Hex(sha1 + KEY);//MD5加密
        String cipher = DigestUtils.md5Hex(md5+salt);//对MD5加密后的密文与盐再次进行加密，得到最终密文
        return cipher;
    }
}
