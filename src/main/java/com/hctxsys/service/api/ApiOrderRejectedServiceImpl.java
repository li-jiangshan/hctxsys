package com.hctxsys.service.api;

import com.hctxsys.entity.YskOrderDetailEntity;
import com.hctxsys.entity.YskOrderEntity;
import com.hctxsys.entity.YskOrderPayEntity;
import com.hctxsys.entity.YskOrderRejectedEntity;
import com.hctxsys.repository.OrderDetailRepository;
import com.hctxsys.repository.OrderPayRepository;
import com.hctxsys.repository.OrderRejectedRepository;
import com.hctxsys.repository.OrderRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.util.OrderUtils;
import com.hctxsys.vo.api.ApiOrderRejectedVo;
import com.hctxsys.vo.api.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("apiOrderRejectedService")
public class ApiOrderRejectedServiceImpl {
	
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderPayRepository orderPayRepository;
    @Autowired
    private OrderRejectedRepository orderRejectedRepository;
    
    //生成退货申请订单
    @Transactional
    public JsonResult addOrderRejected(ApiOrderRejectedVo vo) {
    	
        JsonResult returnVo = new JsonResult();
        
        //查询订单详细信息
        Optional<YskOrderDetailEntity> opOrderDetail = orderDetailRepository.findById(vo.getOrderDetailId());
        
		if (!opOrderDetail.isPresent()) {
  			returnVo.setCode(0);
  			returnVo.setMessage("订单信息不存在");
  	        return returnVo;
		}
		YskOrderDetailEntity orderDetail = opOrderDetail.get();
		
        //查询订单信息
        YskOrderEntity order = orderRepository.findById(orderDetail.getOrderId()).get();
        
        if (order.getUserId() != vo.getUserId().intValue()) {
  			returnVo.setCode(0);
  			returnVo.setMessage("该订单与用户信息不匹配");
  	        return returnVo;
        }
        
        if (order.getOrderStatus() != 2) {
  			returnVo.setCode(0);
  			returnVo.setMessage("该订单不可退货");
  	        return returnVo;
        }
        
        //查询支付订单信息
    	YskOrderPayEntity payOrderNo = orderPayRepository.findByOrderIdListLike("%" + order.getOrderNo() + "%");
        
        YskOrderRejectedEntity  orderRejected = new YskOrderRejectedEntity();
        BeanUtils.copyProperties(vo, orderRejected);//将Vo数据存入Entity中
        orderRejected.setPayOrderNo(payOrderNo.getOrderNo()); //支付订单编号
        orderRejected.setPayOrderPrice(payOrderNo.getOrderTotalPrice()); //支付订单金额
        orderRejected.setPayOrderCode(order.getOrderPayCode()); // 支付方式
        orderRejected.setRejectedNo(this.getOrderNo("TK")); //退款编号
        orderRejected.setRejectedPrice(orderDetail.getGoodPrice().multiply(new BigDecimal(orderDetail.getGoodNum()))); //退款金额
        orderRejected.setRejectedNum(String.valueOf(orderDetail.getGoodNum())); //退货数量
        orderRejected.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
        orderRejected.setOrderStatus(0); // 退货订单状态
        orderRejectedRepository.saveAndFlush(orderRejected);
        
        return returnVo;
    }
    
    //添加退货物流信息
    @Transactional
    public JsonResult updateOrderRejected(ApiOrderRejectedVo vo) {
    	
        JsonResult returnVo = new JsonResult();
        
        //查询退货订单详细信息
        Optional<YskOrderRejectedEntity> opOrderRejected = orderRejectedRepository.findById(vo.getRejectedId());
        
		if (!opOrderRejected.isPresent()) {
  			returnVo.setCode(0);
  			returnVo.setMessage("退货订单信息不存在");
  	        return returnVo;
		}
		YskOrderRejectedEntity orderRejected = opOrderRejected.get();
		
        if (orderRejected.getUserId().intValue() != vo.getUserId().intValue()) {
  			returnVo.setCode(0);
  			returnVo.setMessage("该订单与用户信息不匹配");
  	        return returnVo;
        }
        orderRejected.setLogisticsNo(vo.getLogisticsNo());//物流单号
        orderRejected.setLogisticsMobile(vo.getLogisticsMobile());//联系电话
        orderRejected.setLogisticsImg(vo.getLogisticsImg());//物流图片
        orderRejected.setOrderStatus(3); // 退货订单状态
        orderRejectedRepository.saveAndFlush(orderRejected);
        
        return returnVo;
    }
    
    //退货订单查询
    @Transactional
    public JsonResult searchOrderRejected(ApiOrderRejectedVo vo) {
    	
        JsonResult returnVo = new JsonResult();
        
        //查询退货订单详细信息
		YskOrderRejectedEntity orderRejectedEntity = orderRejectedRepository.findByOrderDetailIdAndOrderStatusNot(vo.getOrderDetailId(), 6);
        
		if (orderRejectedEntity == null) {
  			returnVo.setCode(0);
  			returnVo.setMessage("退货订单信息不存在");
  	        return returnVo;
		}
		
		returnVo.setData(orderRejectedEntity);
		
        return returnVo;
    }
    
    //撤销退货申请订单
    @Transactional
    public JsonResult cancelOrderRejected(ApiOrderRejectedVo vo) {
    	
        JsonResult returnVo = new JsonResult();
        
        //查询退货订单详细信息
        Optional<YskOrderRejectedEntity> opOrderRejected = orderRejectedRepository.findById(vo.getRejectedId());
        
		if (!opOrderRejected.isPresent()) {
  			returnVo.setCode(0);
  			returnVo.setMessage("退货订单信息不存在");
  	        return returnVo;
		}
		YskOrderRejectedEntity orderRejected = opOrderRejected.get();
		
        if (orderRejected.getUserId().intValue() != vo.getUserId().intValue()) {
  			returnVo.setCode(0);
  			returnVo.setMessage("该订单与用户信息不匹配");
  	        return returnVo;
        }
        
        orderRejected.setOrderStatus(6); // 退货订单状态
        orderRejectedRepository.saveAndFlush(orderRejected);
        
        return returnVo;
    }
    
    //获取订单号
    private String getOrderNo(String orderFlag) {
    	
        String orderNo = OrderUtils.getOrderNo(orderFlag);
        
    	List<YskOrderRejectedEntity> allByOrderNo = orderRejectedRepository.findAllByRejectedNo(orderNo);
    	
    	if (allByOrderNo.size() != 0) {
    		this.getOrderNo(orderFlag);
    	}
    	
        return orderNo;
    }

    //再次退货
    public JsonResult SecondOrderRejected(ApiOrderRejectedVo vo) {
        JsonResult returnVo = new JsonResult();
        Optional<YskOrderRejectedEntity> rejected = orderRejectedRepository.findById(vo.getRejectedId());
        if (!rejected.isPresent()) {
            return new JsonResult(0, "退货订单不存在");
        }
        YskOrderRejectedEntity orderRejected = rejected.get();
        //查询订单详细信息
        Optional<YskOrderDetailEntity> opOrderDetail = orderDetailRepository.findById(orderRejected.getOrderDetailId());

        if (!opOrderDetail.isPresent()) {
            returnVo.setCode(0);
            returnVo.setMessage("订单信息不存在");
            return returnVo;
        }
        YskOrderDetailEntity orderDetail = opOrderDetail.get();

        //查询订单信息
        YskOrderEntity order = orderRepository.findById(orderDetail.getOrderId()).get();

        if (order.getUserId() != orderRejected.getUserId().intValue()) {
            returnVo.setCode(0);
            returnVo.setMessage("该订单与用户信息不匹配");
            return returnVo;
        }

        if (order.getOrderStatus() != 2) {
            returnVo.setCode(0);
            returnVo.setMessage("该订单不可退货");
            return returnVo;
        }

        orderRejected.setRejectedReason(vo.getRejectedReason());//设置新的退货理由
        orderRejected.setRejectedImg(vo.getRejectedImg());//退货凭证url
        orderRejected.setOrderStatusReason("");//拒绝理由清空
        orderRejected.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
        orderRejected.setOrderStatus(0); // 退货订单状态
        orderRejectedRepository.saveAndFlush(orderRejected);

        return returnVo;
    }
}
