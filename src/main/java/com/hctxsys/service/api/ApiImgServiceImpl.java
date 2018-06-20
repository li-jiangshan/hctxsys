package com.hctxsys.service.api;

import com.hctxsys.HctxsysApplication;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.util.Base64Utils;
import com.hctxsys.util.Contant;
import com.hctxsys.util.UuidUtils;
import com.hctxsys.vo.api.ApiUserId;
import com.hctxsys.vo.api.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("apiImgService")
public class ApiImgServiceImpl {
    @Autowired
    private UserRepository userRepository;
    public JsonResult saveImg(ApiUserId user) {
        JsonResult jsonResult=new JsonResult();
        Date date = new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        String time = format.format(date);
//        File filePath = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\uploads\\"+time);
//        File newFile=new File(filePath+"\\"+user.getFileName());
        String postfix=user.getFileName().substring(user.getFileName().indexOf(".")-1, user.getFileName().length());
        File filePath = new File(Contant.IMG_PATH+"/"+time);
        File newFile=new File(filePath+"/"+UuidUtils.getUUID()+postfix);
        System.out.println(newFile.getAbsolutePath());

        try {
            if(!filePath.exists()) {//文件夹不存在则创建文件夹
                filePath.mkdirs();
            }
            if(!newFile.exists()) {//创建图像文件
                newFile.createNewFile();
            }
            Base64Utils.GenerateImage(user.getFileBase64(),newFile);
            YskUserEntity userEntity = userRepository.findById(user.getUserId()).get();
            userEntity.setHeadImg("/uploads/"+time+"/"+newFile.getName());
            jsonResult.setCode(1);
            jsonResult.setData("/uploads/"+time+"/"+newFile.getName());
            jsonResult.setMessage("上传成功");
            return jsonResult;
        } catch (IOException e) {
            e.printStackTrace();
            jsonResult.setCode(0);
            jsonResult.setMessage("上传失败");
            return jsonResult;
        }
    }
    public JsonResult saveIOS(MultipartFile file,String userId) {
        JsonResult save = save(file);
        try {
            if(save.getCode()==1) {
                YskUserEntity yskUserEntity = userRepository.findById(Integer.valueOf(userId)).get();
                yskUserEntity.setHeadImg((String) save.getData());
                userRepository.saveAndFlush(yskUserEntity);
            }
            else {
                save.setCode(0);
                save.setMessage("上传头像失败");
                return save;
            }
            return save;
        }
        catch (Exception e) {
            e.printStackTrace();
            save.setCode(0);
            save.setData(null);
            save.setMessage("上传头像失败");
            return save;
        }

    }
    public JsonResult save(MultipartFile file) {
        JsonResult jsonResult=new JsonResult();
        Date date = new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        String time = format.format(date);
        String name = file.getOriginalFilename();
        String postfix=name.substring(name.indexOf(".")-1, name.length());
        try {
//            File root = new File(ResourceUtils.getURL("").getPath());
//            File upload = new File(root.getAbsolutePath(),"src\\main\\resources\\static\\uploads\\");
//            System.out.println(upload.getCanonicalPath());
//            File filePath = new File(upload+"\\"+time);
            File filePath = new File(Contant.IMG_PATH+"/"+time);
            File newFile=new File(filePath+"/"+ UuidUtils.getUUID()+postfix);
            if(!filePath.exists()) {//文件夹不存在则创建文件夹
                filePath.mkdirs();
            }
            if(!newFile.exists()) {//创建图像文件
                newFile.createNewFile();
            }
            System.out.println(newFile.getCanonicalPath());
            file.transferTo(newFile);
            jsonResult.setCode(1);
            jsonResult.setMessage("上传成功");
            jsonResult.setData("/uploads/"+time+"/"+newFile.getName());
            return jsonResult;
        } catch (IOException e) {
            e.printStackTrace();
            jsonResult.setCode(0);
            jsonResult.setMessage("上传失败");
            return jsonResult;
        }
    }
}
