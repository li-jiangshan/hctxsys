package com.hctxsys.controller.api;

import com.hctxsys.service.api.ApiWithdrawServiceImpl;
import com.hctxsys.util.Contant;
import com.hctxsys.vo.api.ApiWithdrawVo;
import com.hctxsys.vo.api.JsonResult;
import com.mysql.jdbc.StringUtils;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//提现Controller
@Controller
@RequestMapping("/api/withdraw")
public class ApiWithdrawController {
	
    @Autowired
    private ApiWithdrawServiceImpl apiWithdrawService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    //获取用户提现信息
    @RequestMapping(value ="/getUserWithdrawInfo", method = RequestMethod.POST) 
  	@ResponseBody
  	public JsonResult getUserWithdrawInfo(@RequestBody ApiWithdrawVo vo) {
    	
    	JsonResult returnVo = new JsonResult();
  		//校验用户账号是否为空
  		if (vo.getUid() == null) {
  			returnVo.setCode(0);
  			returnVo.setMessage("用户id为空");
  	        return returnVo;
  		}
  		returnVo = apiWithdrawService.getUserWithdrawInfo(vo);
    	return returnVo;
	}

    //提现记录
    @RequestMapping(value ="/getWithdrawList", method = RequestMethod.POST) 
  	@ResponseBody
  	public JsonResult getWithdrawList(@RequestBody ApiWithdrawVo vo) {
    	
    	JsonResult returnVo = new JsonResult();
  		//校验用户账号是否为空
  		if (vo.getUid() == null) {
  			returnVo.setCode(0);
  			returnVo.setMessage("用户id为空");
  	        return returnVo;
  		}
  		
		if(vo.getPage() == null) {
  			returnVo.setCode(0);
  			returnVo.setMessage("页码为空");
  	        return returnVo;
		}
		if(vo.getPageSize() == null) {
  			returnVo.setCode(0);
  			returnVo.setMessage("每页数量为空");
  	        return returnVo;
		}
  		returnVo = apiWithdrawService.getWithdrawList(vo);
    	return returnVo;
	}    
    
    //提现申请
    @RequestMapping(value ="/withdrawApply", method = RequestMethod.POST) 
  	@ResponseBody
	public JsonResult withdrawApply(@RequestBody ApiWithdrawVo vo) {

    	JsonResult returnVo = this.checkWithdrawApply(vo);

  		//如果校验失败返回错误信息
		if (returnVo.getCode() == 0) {
  			return returnVo;
		}

  		returnVo = apiWithdrawService.withdrawApply(vo);
    	
    	return returnVo;
	}
    
    //校验用户id与金额
    private JsonResult checkWithdrawApply(ApiWithdrawVo vo) {
    	
  		JsonResult returnVo = new JsonResult();
    	//校验用户账号是否为空
  		if (vo.getUid() == null) {
  			returnVo.setCode(0);
  			returnVo.setMessage("用户id为空");
  	        return returnVo;
  		}
  		//校验金额是否为空
  		if (StringUtils.isNullOrEmpty(String.valueOf(vo.getMoney()))) {
  			returnVo.setCode(0);
  			returnVo.setMessage("请输入金额");
  	        return returnVo;
  		}
		//卡号是否为空
		if (StringUtils.isNullOrEmpty(vo.getCardNo())) {
			returnVo.setCode(0);
			returnVo.setMessage("请填写卡号");
	        return returnVo;
		}
		//手机号
		if (StringUtils.isNullOrEmpty(vo.getMobile())) {
			returnVo.setCode(0);
			returnVo.setMessage("请填写手机号");
	        return returnVo;
		}
		//验证码
		if (StringUtils.isNullOrEmpty(vo.getVerificationCode())) {
			returnVo.setCode(0);
			returnVo.setMessage("请填写验证码");
	        return returnVo;
		}

  		//提现金额应该大于0
  		if (vo.getMoney().compareTo(new BigDecimal(0)) != 1) {
  			returnVo.setCode(0);
  			returnVo.setMessage("提现金额需大于0");
  	        return returnVo;
  		}

        String verificationCode = redisTemplate.opsForValue().get(Contant.MOBILE_VERIFICATION_CODE + vo.getMobile());
        //校验验证码是否正确
        if (verificationCode == null || !verificationCode.equals(vo.getVerificationCode())) {
            returnVo.setCode(0);
            returnVo.setMessage("验证码错误或已过期");
            return returnVo;
        }
		//安全密码
		if (StringUtils.isNullOrEmpty(vo.getSafetyPwd())) {
			returnVo.setCode(0);
			returnVo.setMessage("请填写安全密码");
	        return returnVo;
		}
  		return returnVo;
    }
    
}
