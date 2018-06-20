package com.hctxsys.controller.api;

import com.hctxsys.entity.YskSellerApplyEntity;
import com.hctxsys.service.api.ApiSellerServiceImpl;
import com.hctxsys.util.CheckUtils;
import com.hctxsys.vo.api.JsonResult;
import com.mysql.jdbc.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/seller")
public class ApiSellerController {
	
    @Autowired
    private ApiSellerServiceImpl sellerService;
    
    //查询分类信息
    @RequestMapping(value = "/getIndustryInfo", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getIndustryInfo() throws Exception {
        return sellerService.getIndustryInfo();
    }
    
    //查询是否申请过联盟商家
    @RequestMapping(value = "/getApplyInfo", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getApplyInfo(@RequestBody YskSellerApplyEntity sellerApply) throws Exception {
    	
        JsonResult jsonResul = new JsonResult();
        if (sellerApply.getUid() == 0) {
            jsonResul.setCode(0);
            jsonResul.setMessage("用户ID不能为空");
            return jsonResul;
        }
        
        return sellerService.getApplyInfo(sellerApply);
    }
    
    //校验是否可以申请联盟商家
    @RequestMapping(value = "/checkApplySeller", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult checkApplySeller(@RequestBody YskSellerApplyEntity sellerApply) throws Exception {
    	
        JsonResult jsonResul = new JsonResult();
        if (sellerApply.getUid() == 0) {
            jsonResul.setCode(0);
            jsonResul.setMessage("用户ID不能为空");
            return jsonResul;
        }
        
        return sellerService.checkApplySeller(sellerApply);
    }
    
    //联盟商家申请
    @RequestMapping(value = "/applySeller", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult applySeller(@RequestBody YskSellerApplyEntity sellerApply) throws Exception {
    	
        JsonResult returnVo = new JsonResult();
        if (sellerApply.getUid() == 0) {
        	returnVo.setCode(0);
            returnVo.setMessage("用户ID不能为空");
            return returnVo;
        }
        if(StringUtils.isNullOrEmpty(sellerApply.getConName())){
			returnVo.setCode(0);
			returnVo.setMessage("请输入联系人姓名");
	        return returnVo;
        }
        if(StringUtils.isNullOrEmpty(sellerApply.getConMobile())){
			returnVo.setCode(0);
			returnVo.setMessage("请输入联系人手机号");
	        return returnVo;
        }
        if(!CheckUtils.isCheckMobile(sellerApply.getConMobile())){
			returnVo.setCode(0);
			returnVo.setMessage("联系人手机号码格式错误");
	        return returnVo;
        }
        if(StringUtils.isNullOrEmpty(sellerApply.getConEmail())){
			returnVo.setCode(0);
			returnVo.setMessage("请输入联系人邮箱");
	        return returnVo;
        }
        if(!CheckUtils.checkEmail(sellerApply.getConEmail())){
			returnVo.setCode(0);
			returnVo.setMessage("联系人邮箱格式错误");
	        return returnVo;
        }
        if(StringUtils.isNullOrEmpty(sellerApply.getShopName())){
			returnVo.setCode(0);
			returnVo.setMessage("请填写店铺名称");
	        return returnVo;
        }
        if(StringUtils.isNullOrEmpty(sellerApply.getResponName())){
			returnVo.setCode(0);
			returnVo.setMessage("请填写负责人姓名");
	        return returnVo;
        }        
        if(StringUtils.isNullOrEmpty(sellerApply.getResponMobile())){
			returnVo.setCode(0);
			returnVo.setMessage("请输入负责人手机号");
	        return returnVo;
        }
        if(StringUtils.isNullOrEmpty(sellerApply.getResponEmail())){
			returnVo.setCode(0);
			returnVo.setMessage("请输入负责人邮箱");
	        return returnVo;
        }
        if(StringUtils.isNullOrEmpty(sellerApply.getProvince()) ||  //省
        		StringUtils.isNullOrEmpty(sellerApply.getCity())	||  //市
        			StringUtils.isNullOrEmpty(sellerApply.getDistrict())){ //区
			returnVo.setCode(0);
			returnVo.setMessage("请填写店铺所在地");
	        return returnVo;
        }        
        if(StringUtils.isNullOrEmpty(sellerApply.getAddresssDetail())){
			returnVo.setCode(0);
			returnVo.setMessage("请填写店铺详细地址");
	        return returnVo;
        }
        if(sellerApply.getIndustryId()== 0){
			returnVo.setCode(0);
			returnVo.setMessage("请填写所属行业");
	        return returnVo;
        }
        if(!CheckUtils.isCheckMobile(sellerApply.getResponMobile())){
			returnVo.setCode(0);
			returnVo.setMessage("负责人手机号码格式错误");
	        return returnVo;
        }
        if(!CheckUtils.checkEmail(sellerApply.getResponEmail())){
			returnVo.setCode(0);
			returnVo.setMessage("负责人邮箱格式错误");
	        return returnVo;
        }
        
        return sellerService.applySeller(sellerApply);
    }
    
    
}
