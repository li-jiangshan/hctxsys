package com.hctxsys.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskGoodCollectEntity;
import com.hctxsys.service.api.ApiGoodCollectServiceImpl;
import com.hctxsys.vo.api.ApiCollectVo;
import com.hctxsys.vo.api.JsonResult;

@Controller
@RequestMapping("/api/goodCollect")
public class ApiGoodCollectController {
	@Autowired
	private ApiGoodCollectServiceImpl goodCollectService;
	
	/**根据用户id找商品
	 * @param uid 用户id
	 * @return
	 */
	@RequestMapping(value="/myCollect",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult myCollect(@RequestBody YskGoodCollectEntity vo) {
		JsonResult result = goodCollectService.getGoodCollectByUid(vo.getUid());
		return result;
	}
	
	/**收藏或删除商品
	 * @param uid 用户id
	 * @param goodId 商品id
	 * @return
	 */
	@RequestMapping(value="/collectGood",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult saveCollect(@RequestBody ApiCollectVo vo) {
		if(vo.getType()==1) {
			JsonResult result = goodCollectService.saveCollect(vo.getUid(), vo.getGoodId());
			return result;
		}else {
			JsonResult result = goodCollectService.delectCollect(vo.getUid(), vo.getGoodId());
			return result;
		}
	}
	
}
