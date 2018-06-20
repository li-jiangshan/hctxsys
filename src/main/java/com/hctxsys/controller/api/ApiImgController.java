package com.hctxsys.controller.api;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.StringUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.hctxsys.service.api.ApiImgServiceImpl;
import com.hctxsys.vo.api.ApiUserId;
import com.hctxsys.vo.api.JsonResult;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("api/img")
public class ApiImgController {
    @Autowired
    private ApiImgServiceImpl imgService;
    @PostMapping("/saveImg")
    @ResponseBody
    public JsonResult saveImg(@RequestBody ApiUserId user) {
        JsonResult jsonResult=new JsonResult();
        if(user.getUserId()==0) {
            jsonResult.setCode(0);
            jsonResult.setMessage("用户ID不能为空");
            return jsonResult;
        }
        return imgService.saveImg(user);
    }
    
    
    @RequestMapping(value = "/waterTest",method=RequestMethod.POST)  
    @ResponseBody  
    public Map<String, Object> waterTest(HttpServletRequest request,@RequestParam("files") MultipartFile[] files, HttpServletResponse response)    
               throws ServletException, IOException {  
    	
    	    HashMap<String,Object> hashMap =  new HashMap<>();  
            request.setCharacterEncoding("utf-8");    
            if(files!=null&&files.length>0){    
                //循环获取file数组中得文件    
                for(int i = 0;i<files.length;i++){    
                    MultipartFile file = files[i];    
                      
                    // 文件保存路径    
                 //String filePath = request.getSession().getServletContext().getRealPath("/");    
                 String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\uploads";
                 System.out.println(filePath);
                 Date now = new Date();  
                    String today = DateUtil.formatDate(now,"yyyyMMdd");  
                    String time = DateUtil.formatDate(now,"yyyyMMddHHmmssSSS");  
                    //String name = file.getOriginalFilename();  
                    String ext = "jpg";  
                    int random =  new Random().nextInt(1000);  
                    filePath +=  File.separator + today;  
                    File dir = new File(filePath);  
                    if (!dir.exists()) {  
                        dir.mkdirs();  
                    }  
                    filePath += File.separator + time + random + "." + ext;   
                      
                    //保存文件    
                    saveFile(file,request,filePath);    
                    hashMap.put("path", filePath);
                }  
            }    
            return hashMap;     
       }   
      
      
    /***  
        * APP手机端请求保存文件，上面方法调用  
        * @param file  
        * @return  
        */   
       private boolean saveFile(MultipartFile file,HttpServletRequest request,String filePath) {    
           // 判断文件是否为空    
           if (!file.isEmpty()) {    
               try {    
                   // 转存文件    
                   file.transferTo(new File(filePath));    
                   return true;    
               } catch (Exception e) {    
                   e.printStackTrace();    
               }    
           }    
           return false;    
       }  

    @PostMapping("/save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request)  {
        MultipartHttpServletRequest mRequest= (MultipartHttpServletRequest) request;
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        return imgService.save(file);
    }
    @PostMapping("/saveIOS")
    @ResponseBody
    public JsonResult saveIos(HttpServletRequest request)  {
        MultipartHttpServletRequest mRequest= (MultipartHttpServletRequest) request;
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        String userId = request.getParameter("userid");
        JsonResult jsonResult=new JsonResult();
        if(file==null) {
            jsonResult.setCode(0);
            jsonResult.setMessage("图片不能为空");
            return jsonResult;
        }
        if(StringUtils.isNullOrEmpty(userId)) {
            jsonResult.setCode(0);
            jsonResult.setMessage("用户ID不能为空");
            return jsonResult;
        }
        return imgService.saveIOS(file,userId);
    }
}

