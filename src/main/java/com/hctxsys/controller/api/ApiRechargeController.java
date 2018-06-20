package com.hctxsys.controller.api;

import com.hctxsys.service.api.ApiRechargeServiceImpl;
import com.hctxsys.vo.api.ApiRechargeVo;
import com.hctxsys.vo.api.JsonResult;
import com.mysql.jdbc.StringUtils;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//充值Controller
@Controller
@RequestMapping("/api/recharge")
public class ApiRechargeController {
	
    @Autowired
    private ApiRechargeServiceImpl apiRechargeService;

    //查询银行列表
    @RequestMapping(value ="/getBankInfo", method = RequestMethod.POST) 
  	@ResponseBody
      public JsonResult getBankInfo() {
          return apiRechargeService.getBankInfo();
      }
    
    //充值记录
    @RequestMapping(value ="/getRechargeList", method = RequestMethod.POST) 
  	@ResponseBody
  	public JsonResult getRechargeList(@RequestBody ApiRechargeVo vo) {
    	
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
  		returnVo = apiRechargeService.getRechargeList(vo);
    	return returnVo;
	}    
    
    //查询公司账户信息
    @RequestMapping(value ="/getCompanyInfo", method = RequestMethod.POST) 
  	@ResponseBody
	public JsonResult getCompanyInfo() {
    	return apiRechargeService.getCompanyInfo();
	}    
    
    //充值生成订单
    @RequestMapping(value ="/rechargeOrder", method = RequestMethod.POST) 
  	@ResponseBody
	public JsonResult rechargeOrder(@RequestBody ApiRechargeVo vo) {

    	JsonResult returnVo = new JsonResult();
    	//校验充值类型是否为空
  		if (StringUtils.isNullOrEmpty(vo.getRechargeType())) {
  			returnVo.setCode(0);
  			returnVo.setMessage("充值类型为空");
  	        return returnVo;
  		}
        if ("1".equals(vo.getRechargeType())) { //在线充值
        	returnVo = this.checkMoney(vo);
        } else if ("2".equals(vo.getRechargeType())) { //线下转账
        	returnVo = this.checkRechageUnderLine(vo);
        } else {
  			returnVo.setCode(0);
  			returnVo.setMessage("充值类型错误");
  	        return returnVo;
        }

  		//如果校验失败返回错误信息
		if (returnVo.getCode() == 0) {
  			return returnVo;
		}

  		returnVo = apiRechargeService.rechargeOrder(vo);
    	
    	return returnVo;
	}
    
    //校验用户id与金额
    private JsonResult checkMoney(ApiRechargeVo vo) {
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
  		return returnVo;
    }
    
    //校验转账信息
    private JsonResult checkRechageUnderLine(ApiRechargeVo vo) {

  		JsonResult returnVo = this.checkMoney(vo);
  		
  		//如果校验失败返回错误信息
		if (returnVo.getCode() == 0) {
  			return returnVo;
		}

        if(vo.getMoney().compareTo(new BigDecimal(100)) == -1){
  			returnVo.setCode(0);
  			returnVo.setMessage("金额不能小于100元");
  	        return returnVo;
        }
  		//转账银行是否为空
  		if (StringUtils.isNullOrEmpty(vo.getBankName())) {
  			returnVo.setCode(0);
  			returnVo.setMessage("请选择转账银行");
  	        return returnVo;
  		}
  		//户名是否为空
  		if (StringUtils.isNullOrEmpty(vo.getUserName())) {
  			returnVo.setCode(0);
  			returnVo.setMessage("请填写户名");
  	        return returnVo;
  		}
  		//卡名是否为空
  		if (StringUtils.isNullOrEmpty(vo.getCardNo())) {
  			returnVo.setCode(0);
  			returnVo.setMessage("请填写卡名");
  	        return returnVo;
  		}
  		//支付凭证是否为空
  		if (StringUtils.isNullOrEmpty(vo.getImg())) {
  			returnVo.setCode(0);
  			returnVo.setMessage("请上传支付凭证");
  	        return returnVo;
  		}
  		
  		return returnVo;
    }
    
     
    
}
