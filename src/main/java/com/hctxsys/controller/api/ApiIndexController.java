package com.hctxsys.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskAppEditionEntity;
import com.hctxsys.entity.YskBannerEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskNewsEntity;
import com.hctxsys.service.api.ApiIndexSerivceImpl;
import com.hctxsys.vo.api.ApiIndexVo;
import com.hctxsys.vo.api.ApiUserLevelVo;
import com.hctxsys.vo.api.JsonResult;

@Controller
@RequestMapping("/api/index")
public class ApiIndexController {
	
	@Autowired  
    private ApiIndexSerivceImpl apiIndexService;

	//查询移动端首页相关信息
	@RequestMapping(value ="/getIndexInfo", method = RequestMethod.POST) 
	@ResponseBody
    public JsonResult getIndexInfo(@RequestBody ApiIndexVo vo) {
		
		JsonResult returnVo = new JsonResult();
//		/** 1. 查询首页banner图列表 */
//		if (StringUtils.isNullOrEmpty(vo.getbType())) {
//	        jsonResult.setCode(0);
//	        jsonResult.setMessage("查询图片类型不能为空");
//	        return jsonResult;
//		}
        List<YskBannerEntity> yskBannerList = apiIndexService.getBannerListByType(vo);
        
		/** 2. 查询喜欢产品 */
        List<YskGoodEntity> yskGoodList = apiIndexService.getLikeGoodList();
        
		/** 3. 查询资讯中心列表 */
        List<YskNewsEntity> yskNewsList = apiIndexService.getFiveNewsList();
        
        ApiIndexVo indexVo = new ApiIndexVo();
        indexVo.setYskBannerList(yskBannerList);
        indexVo.setYskGoodList(yskGoodList);
        indexVo.setYskNewsList(yskNewsList);
        
        returnVo.setCode(1);
        returnVo.setMessage("查询成功");
        returnVo.setData(indexVo);
        
        return returnVo;
    }
	
	//查询用户等级信息
	@RequestMapping(value ="/getUserLevelList", method = RequestMethod.POST) 
	@ResponseBody
    public JsonResult getUserLevelList(@RequestBody ApiIndexVo vo) {
		
		JsonResult returnVo = new JsonResult();
        List<ApiUserLevelVo> userLevelList = apiIndexService.getUserLevelList(vo);
        returnVo.setData(userLevelList);
        
        return returnVo;
    }
	
	//查询安卓版本信息
	@RequestMapping(value ="/getAndroidVersion", method = RequestMethod.POST) 
	@ResponseBody
    public JsonResult getAndroidVersion(@RequestBody ApiIndexVo vo) {
		
		JsonResult returnVo = new JsonResult();
		YskAppEditionEntity appEditionEntity = apiIndexService.getAndroidVersion(vo);
        returnVo.setData(appEditionEntity);
        
        return returnVo;
    }
	
    //查询客服信息
    @RequestMapping(value ="/getCustomerService", method = RequestMethod.POST) 
  	@ResponseBody
	public JsonResult getCustomerService() {
    	return apiIndexService.getCustomerService();
	}    
    
    //通过大分类查询商品信息
    @RequestMapping(value ="/getGoodByCategory", method = RequestMethod.POST) 
  	@ResponseBody
	public JsonResult getGoodByCategory(@RequestBody ApiIndexVo vo) {
    	return apiIndexService.getGoodByCategory(vo);
	}    
    
	//查询服务是否启动
	@RequestMapping(value ="/testService", method = RequestMethod.POST) 
	@ResponseBody
    public JsonResult testService(@RequestBody ApiIndexVo vo) {
		
		JsonResult returnVo = new JsonResult();
        
        return returnVo;
    }

}
