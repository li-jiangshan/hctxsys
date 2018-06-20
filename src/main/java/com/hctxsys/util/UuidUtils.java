package com.hctxsys.util;

import java.util.UUID;

public class UuidUtils {
	
	//获取uuid
	public static String getUUID(){
	    UUID uuid=UUID.randomUUID();
	    String str = uuid.toString(); 
	    String uuidStr=str.replace("-", "");
	    return uuidStr;
	}
	
}
