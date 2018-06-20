package com.hctxsys.service.api;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskUpdateOrderEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.UpdateOrderRepository;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.util.OrderUtils;
import com.hctxsys.vo.api.ApiUpdateOrderVo;
import com.hctxsys.vo.api.JsonResult;

@Service("apiUserUpdateService")
public class ApiUserUpdateServiceImpl {

	@Autowired
	private UpdateOrderRepository updateOrderRepository;
	@Autowired
	private UserRepository userRepository;
	
    //生成用户升级订单
	@Transactional
	public JsonResult userUpdateOrder(ApiUpdateOrderVo vo) {
		
		JsonResult returnVo = new JsonResult();
		
		//查询用户信息
		Optional<YskUserEntity> userEntity = userRepository.findById(vo.getUid());
		
		if (!userEntity.isPresent()) {
  			returnVo.setCode(0);
  			returnVo.setMessage("用户不存在");
  	        return returnVo;
		}
		
		List<YskUpdateOrderEntity> updateOrder = updateOrderRepository.findByUidAndUserLevel(vo.getUid(),vo.getUserLevel());
		
		if (updateOrder.size() > 0) {
			
			if (updateOrder.get(0).getStatus() == 1) {
	  			returnVo.setCode(0);
	  			returnVo.setMessage("升级订单已支付");
	  	        return returnVo;
			}
			
	    	//返回订单号
	        ApiUpdateOrderVo updateOrderVo = new ApiUpdateOrderVo();
	        updateOrderVo.setOrderNo(updateOrder.get(0).getOrderNo());
	        updateOrderVo.setMoney(updateOrder.get(0).getMoney());
	    	returnVo.setData(updateOrderVo);
			return returnVo;
		}
		
		YskUpdateOrderEntity updateOrderEntity = new YskUpdateOrderEntity();
        BeanUtils.copyProperties(vo, updateOrderEntity);//将Vo数据存入Entity中
        updateOrderEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
        updateOrderEntity.setStatus((byte) 0); //'0-未支付 1-已支付 2-不通过',
        updateOrderEntity.setTypeName("用户升级"); //说明
        //生成订单号
    	String orderNo = this.getOrderNo();
    	updateOrderEntity.setOrderNo(orderNo);
        
        updateOrderRepository.save(updateOrderEntity);

    	//返回订单号
        ApiUpdateOrderVo updateOrderVo = new ApiUpdateOrderVo();
        updateOrderVo.setOrderNo(orderNo);
        updateOrderVo.setMoney(vo.getMoney());
    	returnVo.setData(updateOrderVo);
        
		return returnVo;
	}
	
	//获取订单号
	private String getOrderNo() {
		String orderNo = OrderUtils.getOrderNo("UD");
		
		YskUpdateOrderEntity updateOrder = updateOrderRepository.findByOrderNo(orderNo);
		
		if (updateOrder != null) {
			this.getOrderNo();
		}
		return orderNo;
	}

}
