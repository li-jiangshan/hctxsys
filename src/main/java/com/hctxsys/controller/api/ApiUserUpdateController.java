package com.hctxsys.controller.api;

import com.hctxsys.service.api.ApiUserUpdateServiceImpl;
import com.hctxsys.vo.api.ApiUpdateOrderVo;
import com.hctxsys.vo.api.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//充值Controller
@Controller
@RequestMapping("/api/userUpdate")
public class ApiUserUpdateController {
	
    @Autowired
    private ApiUserUpdateServiceImpl userUpdateServiceImpl;

    //充值生成订单
    @RequestMapping(value ="/userUpdateOrder", method = RequestMethod.POST) 
  	@ResponseBody
	public JsonResult userUpdateOrder(@RequestBody ApiUpdateOrderVo vo) {

    	JsonResult returnVo = this.checkUserUpdate(vo);

  		//如果校验失败返回错误信息
		if (returnVo.getCode() == 0) {
  			return returnVo;
		}

  		returnVo = userUpdateServiceImpl.userUpdateOrder(vo);
    	
    	return returnVo;
	}
    
    //校验升级相关信息
    private JsonResult checkUserUpdate(ApiUpdateOrderVo vo) {
  		JsonResult returnVo = new JsonResult();
    	//校验用户账号是否为空
  		if (vo.getUid() == null) {
  			returnVo.setCode(0);
  			returnVo.setMessage("用户id为空");
  	        return returnVo;
  		}
  		//校验金额是否为空
  		if (vo.getMoney() == null) {
  			returnVo.setCode(0);
  			returnVo.setMessage("升级金额为空");
  	        return returnVo;
  		}
  		//用户升级等级
  		if (vo.getUserLevel() == null) {
  			returnVo.setCode(0);
  			returnVo.setMessage("升级等级为空");
  	        return returnVo;
  		}
  		return returnVo;
    }
    
}
