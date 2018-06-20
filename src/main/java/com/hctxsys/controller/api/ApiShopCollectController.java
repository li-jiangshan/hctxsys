package com.hctxsys.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskShopCollectEntity;
import com.hctxsys.service.api.ApiShopCollectServiceImpl;
import com.hctxsys.vo.api.ApiCollectVo;
import com.hctxsys.vo.api.JsonResult;

@Controller
@RequestMapping("/api/shopCollect")
public class ApiShopCollectController {
	@Autowired
	private ApiShopCollectServiceImpl  shopCollectServiceImpl;
	
	/**根据用户id查找收藏的店家
	 * @param uid 用户id
	 * @return
	 */
	@RequestMapping(value="/myCollect",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult myCollect(@RequestBody YskShopCollectEntity vo) {
		JsonResult result = shopCollectServiceImpl.getShopCollectByUid(vo.getUid());
		return result;
	}
	
	
	/**收藏或删除商家
	 * @param uid 用户id
	 * @param sellerId 商家id
	 * @return
	 */
	@RequestMapping(value="/collectShop",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult saveCollect(@RequestBody ApiCollectVo vo) {
		if(vo.getType()==1) {
			JsonResult result = shopCollectServiceImpl.saveCollect(vo.getUid(), vo.getSellerId());
			return result;
		}else {
			JsonResult result = shopCollectServiceImpl.deleteCollect(vo.getUid(),vo.getSellerId());
			return result;
		}
	}

/*	*//**删除用户收藏的商家
	 * @param uid 用户id
	 * @param sellerId 商家id
	 * @return
	 *//*
	@RequestMapping(value="/deleteCollect",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteCollect(@RequestBody ApiCollectVo vo) {
		
	}*/
	
}
