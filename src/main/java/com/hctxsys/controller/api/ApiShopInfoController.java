package com.hctxsys.controller.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hctxsys.entity.YskGoodPriceEntity;
import com.hctxsys.util.DistanceComparator;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.service.api.ApiShopInfoServiceImpl;
import com.hctxsys.util.MapUtil;
import com.hctxsys.vo.api.ApiPositionVo;
import com.hctxsys.vo.api.JsonResult;

@Controller
@RequestMapping("/api/shopInfo")
public class ApiShopInfoController {
	@Autowired
	private ApiShopInfoServiceImpl shopInfoService;
	
	/**查询猜你喜欢的商家列表
	 * @return 
	 */
	@RequestMapping(value="/likeShop",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult likeShopInfo(@RequestBody ApiPositionVo vo) {
		JsonResult result = new JsonResult();
		Page<YskShopInfoEntity> likeShopinfo = shopInfoService.getShopinfo(vo.getPage(), vo.getPageSize(),vo.getLongitude(),vo.getLatitude(),vo.getIndustryid());
		if(likeShopinfo.getContent().size()==0) {
			result.setCode(1);
			result.setMessage("附近没有商家");
			return result;
		}
		for (YskShopInfoEntity yskShopInfoEntity : likeShopinfo.getContent()) {
			//计算距离
			double distance = MapUtil.GetDistance(Double.valueOf(vo.getLatitude()), Double.valueOf(vo.getLongitude()),Double.valueOf(yskShopInfoEntity.getShopW()), Double.valueOf(yskShopInfoEntity.getShopJ()));
			yskShopInfoEntity.setDistance(distance);
			yskShopInfoEntity.setUser(null);
			yskShopInfoEntity.setUserCheckinfo(null);
		}
        List<YskShopInfoEntity> content = likeShopinfo.getContent();
        List<YskShopInfoEntity> shopInfoList=new ArrayList<>();
		for (YskShopInfoEntity yskShopInfoEntity : content) {
            shopInfoList.add(yskShopInfoEntity);
        }
		
		//对距离排序
		Collections.sort(shopInfoList,new DistanceComparator());
//		Collections.reverse(shopInfoList);
			
		result.setCode(1);
		result.setMessage("查询猜你喜欢商家的列表成功");
		result.setData(shopInfoList);
		return result;
	}
	
	/**查询商家详情
	 * @param sellerId 商家id
	 * @return
	 */
	@RequestMapping(value="/shopDetail",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult shopDetail(@RequestBody YskShopInfoEntity vo) {
		JsonResult result = shopInfoService.shopInfoDetail(vo.getUid());
		return result;
	}

    /**
     * 根据商品ID与规格返回价格与库存
     *
     * @param goodPrice 规格实体
     * @return Json规格对象
     */
    @PostMapping("getGoodAttr")
    @ResponseBody
    public JsonResult getGoodAttr(@RequestBody YskGoodPriceEntity goodPrice) {
        if (StringUtils.isNullOrEmpty(goodPrice.getGoodAttrValue())) {
            return new JsonResult(0, "规格项不许为空");
        }
        if (goodPrice.getGoodId() == 0) {
            return new JsonResult(0, "goodID不许为空");
        }
        return shopInfoService.getGoodAttr(goodPrice);
    }
}
