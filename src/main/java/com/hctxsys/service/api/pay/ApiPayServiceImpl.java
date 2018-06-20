package com.hctxsys.service.api.pay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskGoodPriceEntity;
import com.hctxsys.entity.YskMoneyDetailEntity;
import com.hctxsys.entity.YskMoneyRechargeEntity;
import com.hctxsys.entity.YskOrderDetailEntity;
import com.hctxsys.entity.YskOrderEntity;
import com.hctxsys.entity.YskOrderPayEntity;
import com.hctxsys.entity.YskUpdateOrderEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.entity.YskUserWealthEntity;
import com.hctxsys.repository.ConfigRepository;
import com.hctxsys.repository.GoodCarRepository;
import com.hctxsys.repository.GoodPriceRepository;
import com.hctxsys.repository.GoodRepository;
import com.hctxsys.repository.MoneyDetailRepository;
import com.hctxsys.repository.MoneyRechargeRepository;
import com.hctxsys.repository.OrderDetailRepository;
import com.hctxsys.repository.OrderPayRepository;
import com.hctxsys.repository.OrderRepository;
import com.hctxsys.repository.UpdateOrderRepository;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.repository.UserWealthRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.PswUtils;
import com.hctxsys.vo.api.ApiPayVo;
import com.hctxsys.vo.api.JsonResult;

@Service("apiPayService")
public class ApiPayServiceImpl {

	@Autowired
	private MoneyRechargeRepository moneyRechargeRepository;
	@Autowired
	private UpdateOrderRepository updateOrderRepository;
	@Autowired
	private OrderPayRepository orderPayRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
    @Autowired
    private UserWealthRepository wealthRepository;
    @Autowired
    private MoneyDetailRepository moneyDetailRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private GoodRepository goodRepository;
	@Autowired
	private GoodPriceRepository goodPriceRepository;
	@Autowired
	private UserWealthRepository userWealthRepository;
	@Autowired
	private ConfigRepository configRepository;
	@Autowired
	private GoodCarRepository goodCarRepository;
	
	
	
    //判断支付密码是否正确
	@Transactional
	public boolean isValidSafePwd(int userID, String safetyPwdBeforeMD5) {
		YskUserEntity userEntity = userRepository.findById(userID).get();
		String safetyPwd = userEntity.getSafetyPwd();
		String safetySalt = userEntity.getSafetySalt();
		String safetyPwdAfterMD5 = PswUtils.getCipher(safetyPwdBeforeMD5,safetySalt);
		if (safetyPwdAfterMD5.equals(safetyPwd)) {
			return true;
		} else {
			return false;
		}
	}
	
	//更新支付类型
	@Transactional
	public boolean setPayType(String orderNo, Integer payType, int uid) {
		HashMap<Integer,String> payName = new HashMap<Integer,String>();
		payName.put(1, "余额支付");
		payName.put(2, "微信支付");
		payName.put(3, "支付宝支付");
		payName.put(4, "一网通支付");
		try {
			switch(orderNo.substring(0, 2)) {
				// 充值
				case "CZ":
					YskMoneyRechargeEntity moneyRechargeEntity = moneyRechargeRepository.findByOrderNoAndUidAndStatus(orderNo, uid, (byte)0);
					moneyRechargeEntity.setPaytype(payName.get(payType));
					moneyRechargeRepository.saveAndFlush(moneyRechargeEntity);
					
					return true;
				// 会员升级
				case "UD":
					YskUpdateOrderEntity updateOrderEntity = updateOrderRepository.findByOrderNoAndUidAndStatus(orderNo, uid, (byte)0);
					updateOrderEntity.setPaytype(payName.get(payType));
					updateOrderRepository.saveAndFlush(updateOrderEntity);
					
					return true;
				// 支付订单
				case "TP":
					YskOrderPayEntity orderPayEntity = orderPayRepository.findByOrderNoAndUserIdAndOrderStatus(orderNo, uid, (byte)0);
			 		List<String> orderIdList = this.getOrderIdListByPayOrder(orderPayEntity);
					List<YskOrderEntity> orderEntityList = orderRepository.findByUserIdAndOrderStatusAndOrderNoIn(uid, (byte)0, orderIdList);
					for(YskOrderEntity orderEntity : orderEntityList) {
						orderEntity.setOrderPayCode(payType.toString());
						orderEntity.setOrderPayName(payName.get(payType));
						orderRepository.saveAndFlush(orderEntity);
					}
					
					return true;
				
				default :
					return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
		}
	}
	
	//获取订单状态
	@Transactional
	public ApiPayVo getOrderStatus(String orderNo) {
		
		ApiPayVo returnVo = new ApiPayVo();
		
		int uid = 0;
		
		try {
			//根据订单编号查询订单状态
			switch(orderNo.substring(0, 2)) {
				// 充值  
				case "CZ":
					YskMoneyRechargeEntity moneyRechargeEntity = moneyRechargeRepository.findByOrderNo(orderNo);
					returnVo.setStatus(String.valueOf(moneyRechargeEntity.getStatus()));
					returnVo.setGoodsName(moneyRechargeEntity.getTypeName());
					returnVo.setAmount(moneyRechargeEntity.getMoney());
					if (moneyRechargeEntity.getStatus() == 1) {
						returnVo.setPayTime(moneyRechargeEntity.getPayTime());
						uid = moneyRechargeEntity.getUid();
					}
					break;
				// 会员升级
				case "UD":
					YskUpdateOrderEntity updateOrderEntity = updateOrderRepository.findByOrderNo(orderNo);
					returnVo.setStatus(String.valueOf(updateOrderEntity.getStatus()));
					returnVo.setGoodsName(updateOrderEntity.getTypeName());
					returnVo.setAmount(updateOrderEntity.getMoney());
					if (updateOrderEntity.getStatus() == 1) {
						returnVo.setPayTime(updateOrderEntity.getPayTime());
						uid = updateOrderEntity.getUid();
					}
					break;
				// 支付订单
				case "TP":
					YskOrderPayEntity orderPayEntity = orderPayRepository.findByOrderNo(orderNo);
					returnVo.setStatus(String.valueOf(orderPayEntity.getOrderStatus()));
					returnVo.setGoodsName("购买商品");
					returnVo.setAmount(orderPayEntity.getOrderTotalPrice());
					if (orderPayEntity.getOrderStatus() == 1) {
				 		List<String> orderIdList = this.getOrderIdListByPayOrder(orderPayEntity);
				 		List<YskOrderEntity> orderEntityList = orderRepository.findByOrderNoIn(orderIdList);
						returnVo.setPayTime(orderEntityList.get(0).getOrderPayTime());
						uid = orderPayEntity.getUserId();
					}
					break;
			}

	        if ("1".equals(returnVo.getStatus())) {
				List<Integer> ids = new ArrayList<Integer>();
				ids.add(39); //账户名称id
				List<YskConfigEntity> configList = configRepository.findByIdIn(ids);  //通过用户id查询
				returnVo.setPayee(configList.get(0).getValue()); //收款人
				
				YskUserEntity userEntity = userRepository.getOne(uid);
				returnVo.setPayer(userEntity.getUsername());
				returnVo.setOrderNo(orderNo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return returnVo;
	}
	
	//支付成功
	@Transactional
	public boolean paySuccess(String orderNo) {
		
		try {
			// create time
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			int time = Integer.valueOf(DateUtils.getTime(dateFormat.format(date), dateFormat));
			
			// 向message表中添加一条,操作messageread表
//			successMessage(time, orderNo, time);
			
			switch (orderNo.substring(0, 2)) {
				// 充值
				case "CZ":
					return processCZ(orderNo, time);
				// 会员升级
				case "UD":
					return processUD(orderNo, time);
				// 购买商品
				case "TP":
					return processTP(orderNo, time);
			}
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
		}
	}
	
	//立即支付 余额支付
    @Transactional
    public JsonResult payOrderByBalance(ApiPayVo apiPayVo) {

    	JsonResult returnVo=new JsonResult();
    	try {

			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			int time = Integer.valueOf(DateUtils.getTime(format.format(date), format));
	        //根据订单编号查询订单状态
			switch(apiPayVo.getOrderNo().substring(0, 2)) {
				// 会员升级
				case "UD":
					YskUpdateOrderEntity updateOrderEntity = updateOrderRepository.findByOrderNo(apiPayVo.getOrderNo());
	                if(updateOrderEntity.getStatus()==1) {//订单状态已判断
	                	returnVo.setCode(0);
	                	returnVo.setMessage("订单已支付");
	                    return returnVo;
	                }
	                //更新用户财富信息
	                returnVo = this.updateUserWealth(apiPayVo.getUserID(),updateOrderEntity.getMoney(),updateOrderEntity.getOrderNo(),time,"updateuser","用户升级");
	          		//如果校验失败返回错误信息
	        		if (returnVo.getCode() == 0) {
	          			return returnVo;
	        		}
                    //保存用户升级相关信息
                    this.processUD(apiPayVo.getOrderNo(),time);
					return returnVo;
				// 支付购物订单
				case "TP":
		        	YskOrderPayEntity orderEntity = orderPayRepository.findByOrderNo(apiPayVo.getOrderNo());
	                if(orderEntity.getOrderStatus()==1) {//订单状态已判断
	                	returnVo.setCode(0);
	                	returnVo.setMessage("订单已支付");
	                    return returnVo;
	                }
	                //更新用户财富信息
	                returnVo = this.updateUserWealth(apiPayVo.getUserID(),orderEntity.getOrderTotalPrice(),orderEntity.getOrderNo(),time,"buygood","购买商品");
	          		//如果校验失败返回错误信息
	        		if (returnVo.getCode() == 0) {
	          			return returnVo;
	        		}
                    //保存购买商品相关信息
                    this.processTP(apiPayVo.getOrderNo(),time);
                    
					return returnVo;
			}
            return returnVo;
        }
        catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        
		return returnVo;
    }
	
	//更新用户财富信息
	private JsonResult updateUserWealth(int uid, BigDecimal totalPrice, String orderNo, int time, String type, String typeName) {

    	JsonResult returnVo=new JsonResult();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		//查询用户余额信息
    	YskUserWealthEntity userWealth = wealthRepository.findById(uid).get();
        BigDecimal money = userWealth.getMoney();//获取用户余额
        if(money.subtract(totalPrice).compareTo(BigDecimal.ZERO) == -1) {//账户余额判断
        	returnVo.setCode(0);
        	returnVo.setMessage("账户余额不足");
            return returnVo;
        }
        //保存用户财富信息
        userWealth.setMoney(money.subtract(totalPrice));//用户余额减免
        userWealth.setUptimeing(DateUtils.getTimestamp(format.format(date),format));//财富信息更新
        wealthRepository.saveAndFlush(userWealth);//保存用户财富信息
    	
        YskMoneyDetailEntity moneyDetailEntity=new YskMoneyDetailEntity();
        //设置余额详情表数据
        moneyDetailEntity.setUid(uid);
        moneyDetailEntity.setCreateTime(Integer.valueOf(time));
        moneyDetailEntity.setOrderNo(orderNo);
        moneyDetailEntity.setStatus((byte) 1);
        moneyDetailEntity.setFromType((byte) 2);
        moneyDetailEntity.setMoneyRecord(money.subtract(totalPrice));
        moneyDetailEntity.setMoney(totalPrice);
        moneyDetailEntity.setType(type);
        moneyDetailEntity.setTypeName(typeName);
        moneyDetailEntity.setContent(typeName + totalPrice);
        moneyDetailRepository.saveAndFlush(moneyDetailEntity);
        
        return returnVo;
	}
    
    // 充值
 	private boolean processCZ(String orderNo ,int time) {
 		// 操作ysk_money_recharge,操作status和pay_time
 		YskMoneyRechargeEntity moneyRechargeEntity = moneyRechargeRepository.findByOrderNo(orderNo);
 		moneyRechargeEntity.setStatus((byte)1);
 		moneyRechargeEntity.setPayTime(time);
 		moneyRechargeRepository.saveAndFlush(moneyRechargeEntity);
 		
 		// moneyRecharge实体中取，money
 		BigDecimal CZMoney = moneyRechargeEntity.getMoney();
 		// 更新user_wealth表
 		YskUserWealthEntity userWealthEntity = userWealthRepository.findById(moneyRechargeEntity.getUid()).get();
 		userWealthEntity.setMoney(userWealthEntity.getMoney().add(CZMoney));
 		userWealthRepository.saveAndFlush(userWealthEntity);
 		// money_detail表增加一条
 		YskMoneyDetailEntity moneyDetailEntity = new YskMoneyDetailEntity();
 		moneyDetailEntity.setContent("在线充值"+CZMoney.toString());
 		moneyDetailEntity.setFromType((byte)1);
 		moneyDetailEntity.setType("online");
 		moneyDetailEntity.setUid(moneyRechargeEntity.getUid());
 		moneyDetailEntity.setOrderNo(moneyRechargeEntity.getOrderNo());
 		moneyDetailEntity.setCreateTime(time);
 		moneyDetailEntity.setStatus((byte)1);
 		moneyDetailEntity.setMoney(CZMoney);
 		moneyDetailEntity.setMoneyRecord(userWealthEntity.getMoney());
 		moneyDetailRepository.saveAndFlush(moneyDetailEntity);
 		return true;
 	}
 	
 	// 会员升级
 	private boolean processUD(String orderNo ,int time) {
 		// 操作ysk_update_order,操作status和pay_time
 		YskUpdateOrderEntity updateOrderEntity = updateOrderRepository.findByOrderNo(orderNo);
 		updateOrderEntity.setStatus((byte)1);
 		updateOrderEntity.setPayTime(time);
 		updateOrderRepository.saveAndFlush(updateOrderEntity);
 		
 		if (updateOrderEntity.getUserLevel() == 2) {
 			return true;
 		}
 		
 		// update_order实体中取，status判断是否支付，userlevel判断等级，money确认积分
 		byte updateOrderStatus = updateOrderEntity.getStatus();
 		int userlevel = updateOrderEntity.getUserLevel();
// 		BigDecimal UDMoney = updateOrderEntity.getMoney();
 		if (updateOrderStatus != 1) {
 			return false;
 		}
 		// 更新user表
 		YskUserEntity userEntity = userRepository.findById(updateOrderEntity.getUid()).get();
 		int nowUserLevel = userEntity.getLevel();
// 		int pid = userEntity.getPid();
// 		int gid = userEntity.getGid();
// 		int ggid = userEntity.getGgid();
 		if (nowUserLevel < userlevel) {
 			userEntity.setLevel((byte)userlevel);
 			userRepository.saveAndFlush(userEntity);
 			
 			// 积分
// 			excIntegral(UDMoney, updateOrderEntity.getUid(), time);
 			
 			// 二级积分
// 			int[] pgArr = {pid,gid};
// 			threeCome(pid,gid,ggid,UDMoney,time);
 			
 		}
 		return true;
 	}
 	
 	//购买产品
 	private boolean processTP(String orderNo ,int time) {
 		// 操作ysk_order_pay,操作status
 		YskOrderPayEntity orderPayEntity = orderPayRepository.findByOrderNo(orderNo);
 		orderPayEntity.setOrderStatus((byte)1);
 		orderPayRepository.saveAndFlush(orderPayEntity);
 		
 		//保存商品订单表
 		List<String> orderIdList = this.getOrderIdListByPayOrder(orderPayEntity);
 		List<YskOrderEntity> orderEntityList = orderRepository.findByOrderNoIn(orderIdList);
 		for(YskOrderEntity orderEntity : orderEntityList) {
 			orderEntity.setOrderStatus((byte)1);
 	 		orderEntity.setOrderPayTime(time);
 	 		orderRepository.saveAndFlush(orderEntity);
 	 		
 	 		// 增加产品销量
 	 		updateSellNumAndStore(orderEntity);
 		}
// 		BigDecimal TPMoney = orderPayEntity.getOrderTotalPrice();
 		
 		// 积分
// 		excIntegral(TPMoney, time, time);
 		return true;
 	}
 	
 	// 库存
 	private void updateSellNumAndStore(YskOrderEntity orderEntity) {
 		
 		// ysk_order_detail
 		List<YskOrderDetailEntity> orderDetailEntityList = orderDetailRepository.findByOrderId(orderEntity.getOrderId());
 		
 		// good中改good_sell_num和good_store,变更值为ysk_order_detail中商品数量
 		// attr_value排空,good_price中变更store
 		for (YskOrderDetailEntity orderDetailEntity : orderDetailEntityList){
 			
 			YskGoodEntity goodEntity = goodRepository.findById(orderDetailEntity.getGoodId()).get();
 			goodEntity.setGoodSellNum(goodEntity.getGoodSellNum() + orderDetailEntity.getGoodNum());
 			goodEntity.setGoodStore(goodEntity.getGoodStore() - orderDetailEntity.getGoodNum());
 			goodRepository.saveAndFlush(goodEntity);
 			if ((orderDetailEntity.getAttrValue() != "") && (orderDetailEntity.getAttrValue() != null)) {
 				YskGoodPriceEntity goodPriceEntity = goodPriceRepository.findByGoodIdAndGoodAttrValue(orderDetailEntity.getGoodId(), orderDetailEntity.getAttrValue());
 				goodPriceEntity.setStore(goodPriceEntity.getStore() - orderDetailEntity.getGoodNum());
 				goodPriceRepository.saveAndFlush(goodPriceEntity);
 	 	 		//删除购物车数据
 	 	 		goodCarRepository.deleteByUidAndGoodIdAndPriceId(orderEntity.getUserId(), orderDetailEntity.getGoodId(), orderDetailEntity.getPriceId());
 				
 			} else {
 	 	 		//删除购物车数据
 	 	 		goodCarRepository.deleteByUidAndGoodId(orderEntity.getUserId(), orderDetailEntity.getGoodId());
 			}
 			
 		}
 	}
 	
 	//获取订单id
 	private List<String> getOrderIdListByPayOrder(YskOrderPayEntity orderPayEntity) {
 		String[] orderIDArray = orderPayEntity.getOrderIdList().split(",");
 		List<String> orderIDList = Arrays.asList(orderIDArray);
 		return orderIDList;
 	}
 	
 	
 	
}