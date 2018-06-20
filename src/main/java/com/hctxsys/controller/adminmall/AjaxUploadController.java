package com.hctxsys.controller.adminmall;

import java.util.Calendar;
import java.util.Date;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hctxsys.util.Contant;
import com.hctxsys.util.FileUtil;

@Controller

public class AjaxUploadController {
	
    ///uploads/temp/2018/02-28/bf156cf2e91d0048d1c3fd92ee5a4075.jpg
	private static final String path = Contant.IMG_PATH+"/temp/";

	// 处理文件上传
	@RequestMapping(value = "/testuploadimg", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String uploadImg(@RequestParam MultipartFile file) {
		//时间
		Calendar c = Calendar.getInstance();
	    int year = c.get(Calendar.YEAR);  
	    int m = c.get(Calendar.MONTH)+1;   
	    int date = c.get(Calendar.DATE);
	    String month = "";
	    if (m<10) {
			month = "0"+m;
		}else {
			month=String.valueOf(m);
		}
	    
		JSONObject obj = new JSONObject();

		//String contentType = file.getContentType();
		
		long time = new Date().getTime();
		
		String fileName = time
				+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String filePath = path+year+"/"+month+"-"+date+"/";
		try {
			FileUtil.uploadFile(file.getBytes(), filePath, fileName);
		} catch (Exception e) {
			
		}

		try {
			obj.put("path", "/uploads/temp/"+year+"/"+month+"-"+date+"/" + fileName);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		// 返回json
		return obj.toString();
	}
}
