package com.hctxsys.service.api;

import java.util.Date;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskGoodCarEntity;
import com.hctxsys.entity.YskGoodPriceEntity;
import com.hctxsys.repository.GoodCarRepository;
import com.hctxsys.repository.GoodPriceRepository;
import com.hctxsys.repository.GoodRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.vo.api.JsonResult;

@Service("goodCarService")
public class ApiGoodCarServiceImpl {
	@Autowired
	private GoodCarRepository carRepository;
	@Autowired
	private GoodPriceRepository goodPriceRepository;
	@Autowired
	private GoodRepository goodRepository;
	
	@Transient
	public JsonResult saveGoodCar(YskGoodCarEntity carEntity) {
		
		JsonResult result = new JsonResult();
		if(carEntity.getGoodId()<1) {
			result.setCode(0);
			result.setMessage("商品id不能小于0");
			return result;
		}
		if(carEntity.getUid()<1) {
			result.setCode(0);
			result.setMessage("用户id不能小于0");
			return result;
		}
		if(carEntity.getGoodNum()<1) {
			result.setCode(0);
			result.setMessage("商品数量不能小于0");
			return result;
		}
		
		Integer price = carEntity.getPriceId();
		//如果没有规格
		if(null==price||0==price) {
			YskGoodCarEntity goodCar = carRepository.findByUidAndGoodId(carEntity.getUid(), carEntity.getGoodId());
			//如果没有相同的商品就添加数据
			if(null==goodCar) {
				carEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
				carRepository.saveAndFlush(carEntity);
				result.setCode(1);
				result.setMessage("添加到购物车成功");
				return result;
			}else {
				//如果相同就增加数量
				goodCar.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
				goodCar.setGoodNum(carEntity.getGoodNum()+goodCar.getGoodNum());
				carRepository.saveAndFlush(goodCar);
				result.setCode(1);
				result.setMessage("添加到购物车成功");
				return result;
			}
		}else {
			//如果商品有规格
			YskGoodPriceEntity goodPriceEntity = goodPriceRepository.findById(price).get();
			YskGoodCarEntity goodPriceCar = carRepository.findByUidAndPriceId(carEntity.getUid(),price);
			if(null==goodPriceCar) {
				//如果没有相同的规格商品就添加数据
				carEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
				carRepository.saveAndFlush(carEntity);
				result.setCode(1);
				result.setMessage("添加到购物车成功");
				return result;
			}else {
				//如果相同就增加数量
				goodPriceCar.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
				goodPriceCar.setGoodNum(goodPriceCar.getGoodNum()+carEntity.getGoodNum());
				carRepository.saveAndFlush(goodPriceCar);
				result.setCode(1);
				result.setMessage("添加到购物车成功");
				return result;
			}
		}
	}
	
	
	
	
}
