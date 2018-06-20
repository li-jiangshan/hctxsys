package com.hctxsys.controller.adminmall;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hctxsys.util.Contant;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.FileUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UploadImage {

	@RequestMapping("/uploadImg")
	@ResponseBody
	public List<String> uploadImg(MultipartFile file[], HttpServletRequest request) throws Exception {
		List<String> list = new ArrayList<String>();
		
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

		// 设置上传的路径是D盘下的picture
//		String imgPath = "D:/picture/" + newsNo + "/";						
		String imgPath = Contant.IMG_PATH+"/temp/"+year+"/"+month+"-"+date+"/";
		String imgName = null;
		for (MultipartFile f : file) {
			// 图片的名字用毫秒数+图片原来的名字拼接
			// System.out.println(f.getSize());
			// System.out.println(f.getBytes());
			long time = new Date().getTime();
			imgName = time
					+ f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf("."));
			// 上传文件
			FileUtil.uploadFile(f.getBytes(), imgPath, imgName);
			String path ="/uploads/temp/"+year+"/"+month+"-"+date+"/"+imgName;
			list.add(path);
		}
		
		return list;
	}

	/**
	 * 上传文件的方法
	 * 
	 * @param file：文件的字节
	 * @param imgPath：文件的路径
	 * @param imgName：文件的名字
	 * @throws Exception
	 */
	public void uploadFileUtil(byte[] file, String imgPath, String imgName) throws Exception {
		File targetFile = new File(imgPath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(imgPath + imgName);
		out.write(file);
		out.flush();
		out.close();
	}
}
