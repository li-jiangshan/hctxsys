package com.hctxsys.service.api;

import com.hctxsys.entity.*;
import com.hctxsys.repository.*;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.OrderUtils;
import com.hctxsys.vo.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("apiOrderService")
public class ApiOrderServiceImpl {
	
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository detailRepository;
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private OrderPayRepository payRepository;
    @Autowired
    private UserAddressRepository addressRepository;
    @Autowired
    private ShopInfoRepository shopInfoRepository;
    @Autowired
    private OrderRejectedRepository orderRejectedRepository;
    @Autowired
    private UserWealthRepository wealthRepository;
    @Autowired
    private MoneyDetailRepository moneyDetailRepository;
	@Autowired
	private GoodPriceRepository goodPriceRepository;
    
    /**
     * 获取用户订单信息
     * @param vo 订单信息实体
     *
     * @return
     */
    @Transactional
    public JsonResult getOrders(OrderPageVo vo) {
        JsonResult jsonResult=new JsonResult();
        PageRequest of = PageRequest.of(vo.getPageNo(), vo.getPageSize());
        Page<YskOrderEntity> all=null;
        if(vo.getOrderStatus()==4) {//状态码为4代表全查
            all = orderRepository.findAllByUserIdOrderByOrderIdDesc(vo.getUserId(),of);
        }
        else {//0 未支付 1已支付  2已发货 3已完成
            all=orderRepository.findAllByOrderStatusAndUserIdOrderByOrderIdDesc(vo.getOrderStatus(),vo.getUserId(),of);
        }
        if(all.getContent().size()==0) {
            jsonResult.setCode(0);
            jsonResult.setMessage("暂无数据");
            return jsonResult;
        }
        //该用户所有订单
        List<YskOrderEntity> content = all.getContent();
        
        List<ApiOrderVo> returnList = new ArrayList<ApiOrderVo>();
        
        //整理订单信息
        for (int i = 0; i < content.size(); i++) {
        	//订单信息
        	ApiOrderVo returnVo = new ApiOrderVo();
        	returnVo.setOrderNo(content.get(i).getOrderNo()); //订单号
        	returnVo.setOrderStatus(Integer.valueOf(content.get(i).getOrderStatus())); // 订单状态
        	returnVo.setOrderTotalPrice(content.get(i).getOrderTotalPrice()); //订单总价
        	String shopName = "大连华彩自营";
        	if (content.get(i).getSellerId() != 0) {
        		Optional<YskShopInfoEntity> shopInfoOp = shopInfoRepository.findById(content.get(i).getSellerId());
        		if (!shopInfoOp.isPresent()) {
        			jsonResult.setCode(0);
        			jsonResult.setMessage("订单中商家信息不存在");
        			return jsonResult;
        		}
        		YskShopInfoEntity shopInfo = shopInfoOp.get();
        		shopName = shopInfo.getShopName();
        	}
        	returnVo.setShopName(shopName); //商家名称
        	//订单详细信息
            List<ApiOrderDetailVo> orderDetailVoList = new ArrayList<ApiOrderDetailVo>();
            List<YskOrderDetailEntity> detailEntityList = content.get(i).getDetailEntity();
            for (int j = 0; j < detailEntityList.size(); j++) {
            	ApiOrderDetailVo detailVo = new ApiOrderDetailVo();
            	detailVo.setOrderDetailId(detailEntityList.get(j).getId()); //订单详细id
            	detailVo.setGoodName(detailEntityList.get(j).getGoodName()); //商品名称
            	detailVo.setGoodNum(String.valueOf(detailEntityList.get(j).getGoodNum())); //购买数量 
            	detailVo.setGoodPrice(detailEntityList.get(j).getGoodPrice()); //商品价格
            	detailVo.setGoodCoverImg(detailEntityList.get(j).getGoodCoverImg()); //商品图片
            	detailVo.setOrderRejectedStatus(-1); //初始状态为-1  没有发起过退货申请
                detailVo.setAttrValue(detailEntityList.get(j).getAttrValue());//商品规格
//            	if (vo.getOrderStatus()==2) { // 如果是已发货状态可以退货，查询退货状态
            		YskOrderRejectedEntity orderRejectedEntity = orderRejectedRepository.findByOrderDetailIdAndOrderStatusNot(detailEntityList.get(j).getId(), 6);
            		if (orderRejectedEntity != null) {
            			detailVo.setOrderRejectedStatus(orderRejectedEntity.getOrderStatus()); //退货状态
            		}
//            	}
            	
            	orderDetailVoList.add(detailVo);
			}
            returnVo.setOrderDetailList(orderDetailVoList);
        	returnList.add(returnVo);
		}
        
        jsonResult.setCode(1);
        jsonResult.setMessage("获取成功");
        jsonResult.setData(returnList);
        return jsonResult;
    }
    
     //获取支付订单
    @Transactional
    public JsonResult getPayOrders(ApiPayOrderVo vo) {
   	
        JsonResult jsonResult=new JsonResult();
        
    	YskOrderPayEntity payOrderNo = payRepository.findByOrderIdListLike("%" + vo.getOrderNo() + "%");
    	ApiPayOrderVo returnVo = new ApiPayOrderVo();
    	returnVo.setOrderNo(payOrderNo.getOrderNo());
    	returnVo.setOrderTotalPrice(payOrderNo.getOrderTotalPrice());
    	jsonResult.setData(returnVo);
        
        return jsonResult;
    }
    private String getOrderNo(String orderFlag) {
        String orderNo = OrderUtils.getOrderNo(orderFlag);
        
        if ("NZ".equals(orderFlag)) {
        	List<YskOrderEntity> allByOrderNo = orderRepository.findAllByOrderNo(orderNo);
        	
        	if (allByOrderNo.size() != 0) {
        		this.getOrderNo(orderFlag);
        	}
        }
        
        if ("TP".equals(orderFlag)) {
        	YskOrderPayEntity payOrderNo = payRepository.findByOrderNo(orderNo);
        	
        	if (payOrderNo != null) {
        		this.getOrderNo(orderFlag);
        	}
        }
        
        return orderNo;
    }

    /**
     * 取消订单订单
     * @param orderVo 订单消息
     * @return
     */
    @Transactional
    public JsonResult cancelOrder(ApiOrderVo orderVo) {

        JsonResult returnVo = new JsonResult();
    	
    	YskOrderEntity orderEntity =  orderRepository.findByOrderNo(orderVo.getOrderNo());
    	
    	if (orderEntity == null) {
    		returnVo.setCode(0);
    		returnVo.setMessage("订单不存在");
            return returnVo;
    	}
    	
    	//只有未支付订单可以取消
    	if (orderEntity.getOrderStatus() != 0) { 
    		returnVo.setCode(0);
    		returnVo.setMessage("该订单不可取消");
            return returnVo;
    	}
    	
    	orderEntity.setOrderStatus((byte) 4);
    	
    	orderRepository.saveAndFlush(orderEntity);
    	
    	return returnVo;
    	
    }
    
    /**
     * 提交订单
     * @param orderVo 订单消息
     * @return
     */
    @Transactional
    public JsonResult addOrder(ApiOrderVo orderVo) {
    	
        JsonResult jsonResult = new JsonResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        StringBuilder msg=new StringBuilder();
        try {
        	
        	//前台传送的商品详细信息
            List<ApiOrderDetailVo> detailVoList = orderVo.getOrderDetailList();
            
            //根据商家进行分类 key商家id  value商品详细
            Map<Integer, List<ApiOrderDetailVo>> map =new HashMap<Integer, List<ApiOrderDetailVo>>();
            //将相同商家的商品放在同一个key中
            for(ApiOrderDetailVo detailVo: detailVoList){
                if(map.get(detailVo.getSellerId())==null){  //map中不存在该商家 添加进map中
                    List<ApiOrderDetailVo> list = new ArrayList<ApiOrderDetailVo>();
                    list.add(detailVo);
                    map.put(detailVo.getSellerId(), list);
                }else{ //存在商家 添加map中的value
                    List<ApiOrderDetailVo> list =map.get(detailVo.getSellerId());
                    list.add(detailVo);          
                }
            }
            
            //收货地址信息
            YskUserAddressEntity address = addressRepository.findById(orderVo.getAddressID()).get();
            int uid = address.getUserId();
            //支付订单总额 
            BigDecimal orderPayTotal=new BigDecimal(0);
            //所有订单id
            StringBuilder orderNos = new StringBuilder();
            //通过商家id生成订单信息
            for(Integer key:map.keySet()){        
            	//商品订单总价
                BigDecimal orderPriceTotal=new BigDecimal(0);
                
                //计算商品订单总价
                for (ApiOrderDetailVo detailVo : map.get(key)) {
                    YskGoodEntity good = goodRepository.findById(detailVo.getGoodId()).get();
                    BigDecimal goodPrice = good.getGoodPrice();
                	if (detailVo.getPriceId() != null) {
                		YskGoodPriceEntity goodPriceEntity = goodPriceRepository.findById(detailVo.getPriceId()).get();
                		goodPrice = goodPriceEntity.getPrice(); // 指定规定价格
                	}
                    String goodNum = detailVo.getGoodNum();
                    BigDecimal num=new BigDecimal(goodNum);
                    //累加商品订单金额
                    orderPriceTotal=orderPriceTotal.add(num.multiply(goodPrice));
                }
                
                //保存订单信息
                YskOrderEntity order = new YskOrderEntity();
                order.setOrderNo(this.getOrderNo("NZ"));//设定订单号
                order.setOrderCreateTime(Integer.valueOf(DateUtils.getTime(dateFormat.format(date),dateFormat)));
                order.setDetailEntity(null);//不进行级联更新
                order.setOrderTotalPrice(orderPriceTotal);
                order.setUserCountry(address.getCountry());
                order.setUserProvince(address.getProvince());
                order.setUserCity(address.getCity());
                order.setUserDistrict(address.getDistrict());
                order.setUserAddress(address.getDetailAddress());
                order.setUserName(address.getUserName());
                order.setUserId(uid);
                order.setSellerId(key);
                order.setUserMobile(address.getUserMobile());
                YskOrderEntity yskOrderEntity = orderRepository.saveAndFlush(order);//订单表更新
                
                //拼接订单id
                orderNos.append(yskOrderEntity.getOrderNo()).append(",");
                
                //保存订单详细信息
                for (ApiOrderDetailVo detailVo : map.get(key)) {
                	
                	YskGoodEntity good = goodRepository.findById(detailVo.getGoodId()).get();
                	if(good==null) {
                		jsonResult.setCode(0);
                		jsonResult.setMessage("商品不存在");
                		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚
                		return jsonResult;
                	}
                	
                	//商品订单详细
                    YskOrderDetailEntity detailEntity = new YskOrderDetailEntity();
                	
                	//商品表商品库存
                	Integer goodStore = good.getGoodStore();
                	//商品表商品价格;
                	BigDecimal goodPrice = good.getGoodPrice();
                	//如果选择了商品规格
                	if (detailVo.getPriceId() != null) {
                		YskGoodPriceEntity goodPriceEntity = goodPriceRepository.findById(detailVo.getPriceId()).get();
                		goodStore = goodPriceEntity.getStore(); // 指定规格库存
                		goodPrice = goodPriceEntity.getPrice(); // 指定规定价格
                		detailEntity.setAttrValue(goodPriceEntity.getGoodAttrValue()); //商品规格项
                		detailEntity.setAttrText(goodPriceEntity.getGoodAttrText()); //规格对应的名字
                		detailEntity.setPriceId(goodPriceEntity.getId()); //商品规格id
                	}
                	
                    if(goodStore-Integer.valueOf(detailVo.getGoodNum())<0) {
                        msg.append(good.getGoodName()+"库存不足请重新下单");
                        jsonResult.setCode(0);
                        jsonResult.setMessage(msg.toString());
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚
                        return jsonResult;
                    }
                    
                    detailEntity.setSellerId(detailVo.getSellerId()); //商家id
                    detailEntity.setGoodId(detailVo.getGoodId()); //商品id
                    detailEntity.setGoodNum(Short.valueOf(detailVo.getGoodNum())); //购买数量
                    detailEntity.setGoodName(good.getGoodName());//商品名称
                    detailEntity.setGoodNo(good.getGoodNo()); //商品编号
                    detailEntity.setGoodCoverImg(good.getGoodCoverImg()); //商品图片
                    detailEntity.setMarketPrice(good.getMarketPrice());
                    detailEntity.setGoodPrice(goodPrice); //商品价格
                    detailEntity.setCostPrice(good.getCostPrice());
                    detailEntity.setGiveIntegral(good.getGoodIntegral()); //购买商品赠送积分
                    detailEntity.setOrderId(yskOrderEntity.getOrderId()); //订单id
                    detailRepository.saveAndFlush(detailEntity);//订单详情表更新
                }
                
                //累加支付订单金额
                orderPayTotal  = orderPayTotal.add(orderPriceTotal);
            } 
            
            YskOrderPayEntity orderPay = new YskOrderPayEntity();
            orderPay.setOrderNo(this.getOrderNo("TP"));
            orderPay.setOrderIdList(orderNos.toString());
            orderPay.setOrderTotalPrice(orderPayTotal);
            orderPay.setOrderStatus((byte) 0);
            orderPay.setUserId(uid);
            payRepository.saveAndFlush(orderPay);//保存支付方式
            
            HashMap<String, Object> returnMap = new HashMap<>();
            returnMap.put("orderNo",orderPay.getOrderNo());
            returnMap.put("orderTotalPrice",orderPay.getOrderTotalPrice());
            jsonResult.setData(returnMap);
            return jsonResult;
        }
        catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            jsonResult.setCode(0);
            jsonResult.setMessage("提交订单失败");
            return jsonResult;
        }
    }

    public YskOrderEntity getOrderEntity(String orderNo) {
    	YskOrderEntity orderEntity = orderRepository.findByOrderNo(orderNo);
    	return orderEntity;
    }
    
    public List<YskOrderEntity> getAutoConfirmOrder(){
    	Long nowDay = Long.valueOf(DateUtils.getTime() + "000");
 		Integer searchDate = Integer.valueOf(String.valueOf(nowDay - (14 * 24 * 60 * 60 * 1000)).substring(0, 10));
    	
 		List<YskOrderEntity> orderEntityList = orderRepository.findByOrderStatusAndMoneyToSellerAndOrderShipTimeLessThan((byte)2 ,(byte)0 ,searchDate);
    	
    	return orderEntityList;
    }
    
    // 确认收货
    @Transactional
    public JsonResult confirmReceipt(YskOrderEntity orderEntity) {
        JsonResult result = new JsonResult();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date nowDate = new Date();
        Integer time = Integer.valueOf(DateUtils.getTime(sdf.format(nowDate),sdf));
        try {
            // 判断是否已发货
            if((orderEntity.getOrderStatus() == (byte)2) && (orderEntity.getMoneyToSeller() == (byte)0)) {
                orderEntity.setOrderStatus((byte)3);
                orderEntity.setMoneyToSeller((byte)1);
                orderRepository.saveAndFlush(orderEntity);
//				Integer sellerId = orderEntity.getSellerId();
                List<YskOrderDetailEntity> orderDetailEntityList = detailRepository.findByOrderId(orderEntity.getOrderId());
                for(YskOrderDetailEntity orderDetailEntity : orderDetailEntityList) {
                    if( (orderDetailEntity.getIsSend() == (byte)1) || (orderDetailEntity.getIsSend() == (byte)2)) {
                    	YskOrderRejectedEntity orderRejectedEntity = orderRejectedRepository.findByOrderDetailId(orderDetailEntity.getId());
                    	if(orderRejectedEntity != null) {
                    		Integer rejectedOrderStatus = orderRejectedEntity.getOrderStatus();
                        	if(rejectedOrderStatus == 5) {
                        		continue;
                        	} else if((rejectedOrderStatus == 0) || (rejectedOrderStatus == 1) || (rejectedOrderStatus == 3) || (rejectedOrderStatus == 4)) {
                        		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        		result.setCode(0);
                                result.setMessage("订单中有商品处于退货中");
                                return result;
                        	}
                        }
                    	Integer sellerId = orderDetailEntity.getSellerId();
                        orderDetailEntity.setIsSend((byte)4);
                        detailRepository.saveAndFlush(orderDetailEntity);

                        BigDecimal goodPrice = orderDetailEntity.getGoodPrice().multiply(BigDecimal.valueOf(orderDetailEntity.getGoodNum()));
                        
                        // 给商户金额
                        YskUserWealthEntity userWealthEntity = wealthRepository.findByUid(sellerId);
                        userWealthEntity.setMoney(userWealthEntity.getMoney().add(goodPrice));
                        userWealthEntity.setTotalMoney(userWealthEntity.getTotalMoney().add(goodPrice));
                        wealthRepository.saveAndFlush(userWealthEntity);

                        YskMoneyDetailEntity moneyDetailEntity = new YskMoneyDetailEntity();
                        moneyDetailEntity.setOrderNo(orderEntity.getOrderNo());
                        moneyDetailEntity.setMoney(goodPrice);
                        moneyDetailEntity.setUid(sellerId);
                        moneyDetailEntity.setType("sellgood");
                        moneyDetailEntity.setTypeName("出售商品");
                        moneyDetailEntity.setStatus((byte)1);
                        moneyDetailEntity.setCreateTime(time);
                        moneyDetailEntity.setFromType((byte)1);
                        moneyDetailEntity.setContent("出售商品,订单号" + orderEntity.getOrderNo());
                        moneyDetailEntity.setMoneyRecord(userWealthEntity.getMoney());
                        moneyDetailRepository.saveAndFlush(moneyDetailEntity);
                    }
                }

                result.setCode(1);
                result.setMessage("确认收货成功");
                return result;
            } else {
                result.setCode(0);
                result.setMessage("订单已确认收货,请不要重复操作");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setCode(0);
            result.setMessage("系统异常，请稍后重试");
            return result;
        }

    }

    /**
     * 删除订单
     *
     * @param orderId
     * @return
     */
    @Transactional
    public JsonResult delOrder(String orderNo) {
        try {
            YskOrderEntity byOrderNo = orderRepository.findByOrderNo(orderNo);
            List<YskOrderDetailEntity> allByOrderId = detailRepository.findAllByOrderId(byOrderNo.getOrderId());
            detailRepository.deleteAll(allByOrderId);
            orderRepository.deleteById(byOrderNo.getOrderId());
            return new JsonResult(1, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new JsonResult(0, "服务器异常，删除失败");
        }
    }
}
