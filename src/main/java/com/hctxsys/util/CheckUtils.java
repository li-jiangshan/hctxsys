package com.hctxsys.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CheckUtils {
	
    /**  
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数  
     * 此方法中前三位格式有：  
     * 13+任意数  
     * 15+除4的任意数  
     * 18+除1和4的任意数  
     * 17+除9的任意数  
     * 147  
     */    
    public static boolean isCheckMobile(String str) throws PatternSyntaxException {    
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";    
        Pattern p = Pattern.compile(regExp);    
        Matcher m = p.matcher(str);    
        return m.matches();    
    }  
    
    //校验账号是否为6-20位数字或字母
    public static boolean isCheckAccount(String str) throws PatternSyntaxException {    
        Pattern pat = Pattern.compile("[\\da-zA-Z]{6,20}");
        Pattern patno = Pattern.compile(".*\\d.*");
        Pattern paten = Pattern.compile(".*[a-zA-Z].*");
        Matcher mat = pat.matcher(str);
        Matcher matno = patno.matcher(str);
        Matcher maten = paten.matcher(str);
        if(matno.matches()&& maten.matches() && mat.matches()){
            return true;
        }
        return false;    
    }  
    
    //校验是否为整数
    public static boolean isCheckInteger(String str) throws PatternSyntaxException {  
    	String regex="^[1-9]+[0-9]*$";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(str);
        if(mat.matches()){
            return true;
        }
        return false;    
    }
    
    //校验是否为数字
    public static boolean isCheckNum(String str) throws PatternSyntaxException {  
    	String regex="^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(str);
        if(mat.matches()){
            return true;
        }
        return false;    
    }
    //校验邮箱
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
	
}
