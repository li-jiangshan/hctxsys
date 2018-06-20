package com.hctxsys.controller.adminmall;

import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskGoodImgEntity;
import com.hctxsys.entity.YskGoodPriceEntity;
import com.hctxsys.repository.ConfigRepository;
import com.hctxsys.repository.YskGoodImgRepository;
import com.hctxsys.repository.YskGoodPriceRepository;
import com.hctxsys.service.YskGoodSerivceImpl;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.GoodPriceVo;
import com.hctxsys.vo.GoodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("Adminmall/Good")
public class YskGoodController {
	@Autowired  
    private YskGoodSerivceImpl yskGoodSerivceImpl;
    @Autowired
    private ConfigRepository configRepository;
	
	@Autowired 
	private YskGoodPriceRepository yskGoodPriceRepository;
	
	@Autowired 
	private YskGoodImgRepository yskGoodImgRepository;
	
	/**
	 * 查询商品列表 (自营,有分类名)
	 * @return
	 */
	@GetMapping(path = "/index")
	public ModelAndView getAllGoods(TableResult result) {
		TableResult tableResult = yskGoodSerivceImpl.findList(result);
        YskConfigEntity yskConfigEntity = configRepository.findById(27).get();
        ModelAndView modelAndView = new ModelAndView("adminmall/good/index");
		modelAndView.addObject("tableResult", tableResult);
        modelAndView.addObject("configValue", Integer.valueOf(yskConfigEntity.getValue()));
		return modelAndView;
	}
	/**
	 * 商品上架    商品下架
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/putOnSale/{id}")
	@ResponseBody
	public Result putOnSale(@PathVariable String id,TableResult result) {
		YskGoodEntity entity = yskGoodSerivceImpl.putOnSale(Integer.valueOf(id));
		if(1==entity.getStatus()) {
			return new Result(1, "操作成功", "/Adminmall/Good/index", "");
		}else {
			return new Result(1, "操作成功", "/Adminmall/Good/index", "");
		}
		
	}
	/**
	 * 跳转添加页面
	 * @return
	 */
	@RequestMapping("/add")
	public String page() {
		return "adminmall/good/add"; //返回页面
	}
	/**
	 * 
	 * 插入
	 * @param name
	 * @param email
	 * @return
	 */
	@RequestMapping(path = "/addgood",method=RequestMethod.POST)
	@ResponseBody
	public Result addNewGood(@RequestBody GoodVo testEntity){
		YskGoodEntity entity = new YskGoodEntity();
		String goodId = testEntity.getGoodId();
		
		goodId = String.valueOf(yskGoodSerivceImpl.findMaxId().getGoodId()+1);
		
		entity.setGoodId(Integer.valueOf(goodId));
		String goodName = testEntity.getGoodName();
		if(goodName==null||"".equals(goodName)) {
			return new Result(0, "商品名称不能为空");
		}
		entity.setGoodName(goodName);
		String goodNo = testEntity.getGoodNo();
		if(goodNo==null||"".equals(goodNo)) {
			goodNo = " ";
		}
		if(goodNo!=" ") {
			String no = yskGoodSerivceImpl.getGood(goodId).getGoodNo();
			if(goodNo!=no) {
				List<YskGoodEntity> list = yskGoodSerivceImpl.findByGoodNo(goodNo);
				if(list.size()!=0) {
					return new Result(0, "商品编号已存在");
				}
			}
		}
		entity.setGoodNo(goodNo);
		String categoryId = testEntity.getCategoryId();
		if(categoryId==null||"".equals(categoryId)) {
			return new Result(0, "请选择商品分类");
		}
		entity.setCategoryId(Integer.valueOf(categoryId));
		String brandId = testEntity.getBrandId();
		if(brandId==null||"".equals(brandId)) {
			brandId = "0";
		}
		entity.setBrandId(Integer.valueOf(brandId));
		String goodSupplier = testEntity.getGoodSupplier();
		entity.setGoodSupplier(goodSupplier);
		String keywords = testEntity.getKeywords();
		if(keywords==null||"".equals(keywords)) {
			keywords = " ";
		}
		entity.setKeywords(keywords);
		String goodPrice = testEntity.getGoodPrice();
		if(goodPrice==null||"".equals(goodPrice)) {
			return new Result(0, "价格不能为空");
		}
		entity.setGoodPrice(BigDecimal.valueOf(Double.valueOf(goodPrice)));
		String marketPrice = testEntity.getMarketPrice();
		if(marketPrice==null||"".equals(marketPrice)) {
			marketPrice = "0";
		}
		entity.setMarketPrice(BigDecimal.valueOf(Double.valueOf(marketPrice)));
		String costPrice = testEntity.getCostPrice();
		if(costPrice==null||"".equals(costPrice)) {
			costPrice = "0";
		}
		entity.setCostPrice(BigDecimal.valueOf(Double.valueOf(costPrice)));
		String goodCommission = testEntity.getGoodCommission();
		if(goodCommission==null||"".equals(goodCommission)) {
			goodCommission = "0";
		}
		entity.setGoodCommission(goodCommission);
		String goodWeight = testEntity.getGoodWeight();
		if(goodWeight==null||"".equals(goodWeight)) {
			goodWeight = "0";
		}
		entity.setGoodWeight(Integer.valueOf(goodWeight));
		String shipFee = testEntity.getShipFee();
		if(shipFee==null||"".equals(shipFee)) {
			shipFee = "0";
		}
		entity.setShipFee(Integer.valueOf(shipFee));
		String goodStore = testEntity.getGoodStore();
		if(goodStore==null||"".equals(goodStore)) {
			return new Result(0, "商品库存不能为空");
		}
		entity.setGoodStore(Integer.valueOf(goodStore));
		String goodIntegral = testEntity.getGoodIntegral();
		if(goodIntegral==null||"".equals(goodIntegral)) {
			goodIntegral = "0";
		}
		entity.setGoodIntegral(Integer.valueOf(goodIntegral));
		String goodSort = testEntity.getGoodSort();
		if(goodSort==null||"".equals(goodSort)) {
			goodSort = "50";
		}
		if(Integer.valueOf(goodSort)>65535) {
			return new Result(0, "商品排序超过最大值");
		}
		entity.setGoodSort(Integer.valueOf(goodSort));
		String goodContent = testEntity.getGoodContent();
		entity.setGoodContent(goodContent);
		String modelId = testEntity.getModelId();
		if(modelId==null||"".equals(modelId)) {
			modelId = "0";
		}
		entity.setModelId(Integer.valueOf(modelId));
		String goodCoverImg = testEntity.getGoodCoverImg();
		if(goodCoverImg==null||"".equals(goodCoverImg)) {
			return new Result(0, "商品封面图不能为空");
		}
		entity.setGoodCoverImg(goodCoverImg);

		entity.setSellerId(0);
		entity.setGoodType(0);
		
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
        entity.setCreateTime(Integer.valueOf(time));
		//good表
        entity.setIsHot(Integer.valueOf(testEntity.getIsHot()));
        entity.setIsRecommend(Integer.valueOf(testEntity.getIsRecommend()));
        entity.setIsNew(Integer.valueOf(testEntity.getIsNew()));
		yskGoodSerivceImpl.insertGood(entity);
		//price
		List<GoodPriceVo> tests = testEntity.getTests();
		if(tests!=null&&tests.size()!=0) {
			List<YskGoodPriceEntity> findByPriceId = yskGoodPriceRepository.findByPriceId(Integer.valueOf(goodId));
			if(findByPriceId!=null) {
				yskGoodPriceRepository.deleteByGoodId(Integer.valueOf(goodId));
			}
			for (GoodPriceVo test : tests) {
				YskGoodPriceEntity priceEntity = new YskGoodPriceEntity();
				List<YskGoodPriceEntity> list = yskGoodPriceRepository.findMaxId();
				int id = 0;
				if(list==null||list.size()==0) {
					id = 1;
				}else {
					id = list.get(0).getId()+1;
				}
				priceEntity.setId(id);
				priceEntity.setGoodId(Integer.valueOf(goodId));
				priceEntity.setGoodAttrName(" ");
				priceEntity.setGoodAttrValue(test.getAttrvalue());
				priceEntity.setGoodArrOrder(0);
				if(test.getPrice()==0) {
					return new Result(0, "请填写商品规格对应的价格");
				}else {
					priceEntity.setPrice(BigDecimal.valueOf(test.getPrice()));
				}
				if(test.getStore()==0) {
					return new Result(0, "请填写商品规格对应的价格");
				}else {
					priceEntity.setStore(test.getStore());
				}
				priceEntity.setGoodAttrText(test.getAttrtext());
				yskGoodPriceRepository.save(priceEntity);
			}
		}
		List<String> imgUrl = testEntity.getImgUrl();
		for (String string : imgUrl) {
			List<YskGoodImgEntity> list = yskGoodImgRepository.findMaxId();
			int id = 0;
			if(list==null||list.size()==0) {
				id = 1;
			}else {
				id = list.get(0).getId()+1;
			}
			YskGoodImgEntity imgEntity = new YskGoodImgEntity();
			imgEntity.setId(id);
			imgEntity.setGoodId(Integer.valueOf(goodId));
			if(string==null||"".equals(string)) {
				return new Result(0, "请上传新添加的图片");
			}
			imgEntity.setImgUrl(string);
			yskGoodImgRepository.saveAndFlush(imgEntity);
		}
		return new Result(1, "保存成功","/Adminmall/Good/findById/"+goodId);
	}
	
	@RequestMapping(path = "/editgood",method=RequestMethod.POST)
	@ResponseBody
	public Result editGood(@RequestBody GoodVo testEntity){
		YskGoodEntity entity = new YskGoodEntity();
		String goodId = testEntity.getGoodId();
		
		entity.setGoodId(Integer.valueOf(goodId));
		String goodName = testEntity.getGoodName();
		if(goodName==null||"".equals(goodName)) {
			return new Result(0, "商品名称不能为空");
		}
		entity.setGoodName(goodName);
		String goodNo = testEntity.getGoodNo();
		String no = yskGoodSerivceImpl.getGood(goodId).getGoodNo();
		if(!goodNo.equals(no)) {
			List<YskGoodEntity> list = yskGoodSerivceImpl.findByGoodNo(goodNo);
			if(list.size()!=0) {
				return new Result(0, "商品编号已存在");
			}
		}
		entity.setGoodNo(goodNo);
		String categoryId = testEntity.getCategoryId();
		if(categoryId==null||"".equals(categoryId)) {
			return new Result(0, "请选择商品分类");
		}
		entity.setCategoryId(Integer.valueOf(categoryId));
		String brandId = testEntity.getBrandId();
		if(brandId==null||"".equals(brandId)) {
			brandId = "0";
		}
		entity.setBrandId(Integer.valueOf(brandId));
		String goodSupplier = testEntity.getGoodSupplier();
		entity.setGoodSupplier(goodSupplier);
		String keywords = testEntity.getKeywords();
		if(keywords==null||"".equals(keywords)) {
			keywords = " ";
		}
		entity.setKeywords(keywords);
		String goodPrice = testEntity.getGoodPrice();
		if(goodPrice==null||"".equals(goodPrice)) {
			return new Result(0, "价格不能为空");
		}
		entity.setGoodPrice(BigDecimal.valueOf(Double.valueOf(goodPrice)));
		String marketPrice = testEntity.getMarketPrice();
		if(marketPrice==null||"".equals(marketPrice)) {
			marketPrice = "0";
		}
		entity.setMarketPrice(BigDecimal.valueOf(Double.valueOf(marketPrice)));
		String costPrice = testEntity.getCostPrice();
		if(costPrice==null||"".equals(costPrice)) {
			costPrice = "0";
		}
		entity.setCostPrice(BigDecimal.valueOf(Double.valueOf(costPrice)));
		String goodCommission = testEntity.getGoodCommission();
		if(goodCommission==null||"".equals(goodCommission)) {
			goodCommission = "0";
		}
		entity.setGoodCommission(goodCommission);
		String goodWeight = testEntity.getGoodWeight();
		if(goodWeight==null||"".equals(goodWeight)) {
			goodWeight = "0";
		}
		entity.setGoodWeight(Integer.valueOf(goodWeight));
		String shipFee = testEntity.getShipFee();
		if(shipFee==null||"".equals(shipFee)) {
			shipFee = "0";
		}
		entity.setShipFee(Integer.valueOf(shipFee));
		String goodStore = testEntity.getGoodStore();
		if(goodStore==null||"".equals(goodStore)) {
			return new Result(0, "商品库存不能为空");
		}
		entity.setGoodStore(Integer.valueOf(goodStore));
		String goodIntegral = testEntity.getGoodIntegral();
		if(goodIntegral==null||"".equals(goodIntegral)) {
			goodIntegral = "0";
		}
		entity.setGoodIntegral(Integer.valueOf(goodIntegral));
		String goodSort = testEntity.getGoodSort();
		if(goodSort==null||"".equals(goodSort)) {
			goodSort = "50";
		}
		if(Integer.valueOf(goodSort)>65535) {
			return new Result(0, "商品排序超过最大值");
		}
		entity.setGoodSort(Integer.valueOf(goodSort));
		String goodContent = testEntity.getGoodContent();
		entity.setGoodContent(goodContent);
		String modelId = testEntity.getModelId();
		if(modelId==null||"".equals(modelId)) {
			modelId = "0";
		}
		entity.setModelId(Integer.valueOf(modelId));
		String goodCoverImg = testEntity.getGoodCoverImg();
		if(goodCoverImg==null||"".equals(goodCoverImg)) {
			return new Result(0, "商品封面图不能为空");
		}
		entity.setGoodCoverImg(goodCoverImg);
		entity.setSellerId(0);
		entity.setGoodType(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        Date date = new Date();
        String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
        entity.setCreateTime(Integer.valueOf(time));
        entity.setIsHot(Integer.valueOf(testEntity.getIsHot()));
        entity.setIsRecommend(Integer.valueOf(testEntity.getIsRecommend()));
        entity.setIsNew(Integer.valueOf(testEntity.getIsNew()));
		//good表
		yskGoodSerivceImpl.updateGood(entity);
		//TODO:暂时操作
//		yskGoodSerivceImpl.update(entity);
		//price
		List<GoodPriceVo> tests = testEntity.getTests();
		List<YskGoodPriceEntity> findByPriceId = yskGoodPriceRepository.findByPriceId(Integer.valueOf(goodId));
		if(findByPriceId!=null) {
			yskGoodPriceRepository.deleteByGoodId(Integer.valueOf(goodId));
		}
		if(tests!=null&&tests.size()!=0) {
			
			for (GoodPriceVo test : tests) {
				YskGoodPriceEntity priceEntity = new YskGoodPriceEntity();
				List<YskGoodPriceEntity> list = yskGoodPriceRepository.findMaxId();
				int id = 0;
				if(list==null||list.size()==0) {
					id = 1;
				}else {
					id = list.get(0).getId()+1;
				}
				priceEntity.setId(id);
				priceEntity.setGoodId(Integer.valueOf(goodId));
				priceEntity.setGoodAttrName(" ");
				priceEntity.setGoodAttrValue(test.getAttrvalue());
				priceEntity.setGoodArrOrder(0);
				if(test.getPrice()==0) {
					return new Result(0, "请填写商品规格对应的价格");
				}else {
					priceEntity.setPrice(BigDecimal.valueOf(test.getPrice()));
				}
				if(test.getStore()==0) {
					return new Result(0, "请填写商品规格对应的价格");
				}else {
					priceEntity.setStore(test.getStore());
				}
				priceEntity.setGoodAttrText(test.getAttrtext());
				yskGoodPriceRepository.save(priceEntity);
			}
		}
		List<String> imgUrl = testEntity.getImgUrl();
		for (String string : imgUrl) {
			List<YskGoodImgEntity> list = yskGoodImgRepository.findMaxId();
			int id = 0;
			if(list==null||list.size()==0) {
				id = 1;
			}else {
				id = list.get(0).getId()+1;
			}
			YskGoodImgEntity imgEntity = new YskGoodImgEntity();
			imgEntity.setId(id);
			imgEntity.setGoodId(Integer.valueOf(goodId));
			if(string==null||"".equals(string)) {
				return new Result(0, "请上传新添加的图片");
			}
			imgEntity.setImgUrl(string);
			yskGoodImgRepository.saveAndFlush(imgEntity);
		}
		return new Result(1, "保存成功","/Adminmall/Good/findById/"+goodId);
	}
	/**
	 * 单查商品
	 * @return
	 */
	@GetMapping(path = "/findById/{id}")
	public @ResponseBody ModelAndView getGood(@PathVariable String id) {
		YskGoodEntity good = yskGoodSerivceImpl.getGood(id);
		//List<YskGoodImgEntity> imgs = yskGoodImgSerivceImpl.getImgByGoodId(Integer.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("adminmall/good/edit");
		modelAndView.addObject("good", good);
		//modelAndView.addObject("imgs", imgs);
		return modelAndView;
	} 
	/**
	 * 删除商品
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/deleteGood/{id}")
	@ResponseBody
	public Result deleteGood(@PathVariable String id,TableResult result) {
		int i = yskGoodSerivceImpl.deleteGood(Integer.valueOf(id));
		if (i==1) {
			return new Result(1, "删除成功", "/Adminmall/Good/index", "");
		}
		return new Result(0, "删除失败", "/Adminmall/Good/index", "");
	}
	
	
	/**
	 * 修改排序
	 * @param id
	 * @param sort
	 * @return
	 */
	@PostMapping(path = "/updateSort")
	@ResponseBody
	public Result updateSort(@RequestParam String id,@RequestParam String sort) {
		try {
			YskGoodEntity s = yskGoodSerivceImpl.updateSort(Integer.valueOf(id), Short.valueOf(sort));
			if (s==null) {
				return new Result(0, "修改失败！", "/Adminmall/Good/index", "");
			}
			return new Result(1, "修改成功！", "/Adminmall/Good/index", "");
		} catch (Exception e) {
			return new Result(0, "修改失败！", "/Adminmall/Good/index", "");
		}
		
	}
	
	/**
	 * 设置推荐    取消推荐
	 * isRecommend   1-推荐    0-不推荐
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/turnRecommend/{id}")
	@ResponseBody
	public Result turnRecommend(@PathVariable String id,TableResult tableResult) {
		Result result = yskGoodSerivceImpl.turnRecommend(Integer.valueOf(id));
		return result;
	}
	
	/**
	 * 设置新品    取消新品 
	 * isNew   1-新品     0-不是新品 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/turnNew/{id}")
	@ResponseBody
	public Result turnNew(@PathVariable String id) {
		Result result = yskGoodSerivceImpl.turnNew(Integer.valueOf(id));
		return result;
	}
	
	/**
	 * 设置热卖   取消热卖
	 * isHot   1-热卖    0-不热卖
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/turnHot/{id}")
	@ResponseBody
	public Result turnHot(@PathVariable String id) {
		Result result = yskGoodSerivceImpl.turnHot(Integer.valueOf(id));
		return result;
	}
}
