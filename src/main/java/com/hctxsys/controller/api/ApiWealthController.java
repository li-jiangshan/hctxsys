package com.hctxsys.controller.api;

import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.service.api.ApiWealthServiceImpl;
import com.hctxsys.util.CheckUtils;
import com.hctxsys.util.Contant;
import com.hctxsys.vo.api.ApiKuncunIntegralDetailVo;
import com.hctxsys.vo.api.JsonResult;
import com.mysql.jdbc.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/wealth")
public class ApiWealthController {
	
    @Autowired
    private ApiWealthServiceImpl wealthService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @PostMapping("/getWealth")
    @ResponseBody
    public JsonResult getWealth(@RequestBody YskUserEntity userEntity) {
    	 JsonResult jsonResult = new JsonResult();
	   	 if (userEntity.getUserid() == null) {
			 jsonResult.setCode(0);
			 jsonResult.setMessage("用户ID不能为空");
	          return jsonResult;
		 }
        return wealthService.getWealth(userEntity);
    }

    //获取购买用户余额与库存积分
    @RequestMapping(value = "/getKucunInfo", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getKucunInfo(@RequestBody ApiKuncunIntegralDetailVo vo) {

    	 JsonResult jsonResult = new JsonResult();
    	 if (vo.getUid() == 0) {
    		 jsonResult.setCode(0);
    		 jsonResult.setMessage("用户ID不能为空");
	          return jsonResult;
    	 }
    	 return  wealthService.getKucunInfo(vo);
    }
    
    //获取购买库存积分
    @RequestMapping(value = "/getKucunInfoByMoney", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getKucunInfoByMoney(@RequestBody ApiKuncunIntegralDetailVo vo) {
    	JsonResult returnVo = new JsonResult();
    	if (vo.getMoney() == null) {
    		return returnVo;
    	}
    	 return  wealthService.getKucunInfoByMoney(vo);
    }
    
    //获取发放库存积分
    @RequestMapping(value = "/getSellKucunInfoByMoney", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getSellKucunInfoByMoney(@RequestBody ApiKuncunIntegralDetailVo vo) {
    	JsonResult returnVo = new JsonResult();
    	if (vo.getMoney() == null) {
    		return returnVo;
    	 }
    	 return  wealthService.getSellKucunInfoByMoney(vo);
    }
    
    //购买库存提交申请积分
    @RequestMapping(value = "/saveKucunIntegral", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveKucunIntegral(@RequestBody ApiKuncunIntegralDetailVo vo) throws Exception {
    	
        JsonResult returnVo = new JsonResult();
        if (vo.getUid() == 0) {
        	returnVo.setCode(0);
        	returnVo.setMessage("用户ID不能为空");
            return returnVo;
        }
        if(vo.getMoney() == null){
        	returnVo.setCode(0);
        	returnVo.setMessage("请输入购买金额");
            return returnVo;
        }
        if(!CheckUtils.isCheckNum(String.valueOf(vo.getMoney()))){
        	returnVo.setCode(0);
        	returnVo.setMessage("购买金额请输入数字");
            return returnVo;
        }
        if(vo.getKucunIntegral() == null){
        	returnVo.setCode(0);
        	returnVo.setMessage("请输入库存积分");
            return returnVo;
        }
        if(StringUtils.isNullOrEmpty(vo.getVerificationCode())){
        	returnVo.setCode(0);
        	returnVo.setMessage("请输入验证码");
            return returnVo;
        }
        if(StringUtils.isNullOrEmpty(vo.getMobile())){
        	returnVo.setCode(0);
        	returnVo.setMessage("手机号不能为空");
            return returnVo;
        }
        if(StringUtils.isNullOrEmpty(vo.getImg())){
        	returnVo.setCode(0);
        	returnVo.setMessage("请上传凭证");
            return returnVo;
        }
        if(!CheckUtils.isCheckMobile(vo.getMobile())){
        	returnVo.setCode(0);
        	returnVo.setMessage("请输入正确手机号");
            return returnVo;
        }
        String verificationCode=redisTemplate.opsForValue().get(Contant.MOBILE_VERIFICATION_CODE + vo.getMobile());
        if (verificationCode == null || !verificationCode.equals(vo.getVerificationCode())) {
        	returnVo.setCode(0);
        	returnVo.setMessage("验证码错误或已过期");
            return returnVo;
        }
        returnVo = wealthService.saveKucunIntegral(vo);
        
        return returnVo;
    }
    
    //发放库存积分
    @RequestMapping(value = "/sellKucunintegral", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sellKucunintegral(@RequestBody ApiKuncunIntegralDetailVo vo) {
    	
        JsonResult returnVo = new JsonResult();

        if (vo.getUid() == 0) {
        	returnVo.setCode(0);
        	returnVo.setMessage("用户ID不能为空");
            return returnVo;
        }
        if(StringUtils.isNullOrEmpty(vo.getMobile())){
        	returnVo.setCode(0);
        	returnVo.setMessage("手机号不能为空");
            return returnVo;
        }
        if(!CheckUtils.isCheckMobile(vo.getMobile())){
        	returnVo.setCode(0);
        	returnVo.setMessage("请输入正确手机号");
            return returnVo;
        }
        if(vo.getMoney() == null){
        	returnVo.setCode(0);
        	returnVo.setMessage("请输入购买金额");
            return returnVo;
        }
        if(!CheckUtils.isCheckNum(String.valueOf(vo.getMoney()))){
        	returnVo.setCode(0);
        	returnVo.setMessage("购买金额请输入数字");
            return returnVo;
        }
        if(vo.getKucunIntegral() == null){
        	returnVo.setCode(0);
        	returnVo.setMessage("请输入库存积分");
            return returnVo;
        }
        if(StringUtils.isNullOrEmpty(vo.getContent())){
        	returnVo.setCode(0);
        	returnVo.setMessage("请输入交易说明");
            return returnVo;
        }
    	
        return  wealthService.sellKucunintegral(vo);
    }
    
}
