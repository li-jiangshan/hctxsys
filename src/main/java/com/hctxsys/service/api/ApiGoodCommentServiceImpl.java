package com.hctxsys.service.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskOrderDetailEntity;
import com.hctxsys.entity.YskOrderEntity;
import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.repository.GoodCommentRepository;
import com.hctxsys.repository.GoodRepository;
import com.hctxsys.repository.OrderDetailRepository;
import com.hctxsys.repository.OrderRepository;
import com.hctxsys.repository.ShopInfoRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.vo.api.ApiCommentVo;
import com.hctxsys.vo.api.ApiOrderCommentVo;
import com.hctxsys.vo.api.JsonResult;

@Service("apiGoodCommentService")
public class ApiGoodCommentServiceImpl {
	@Autowired
	private GoodCommentRepository goodCommentRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private GoodRepository goodRepository;
	@Autowired
	private ShopInfoRepository shopInfoRepository;
	@Autowired
	private OrderRepository orderRepository;
	
	/**根据商品id查找评价
	 * @param goodid 商品id
	 * @param page 页码
	 * @param pageSize 每页显示几个数据
	 * @return 分完页的数据
	 */
	@Transactional
	public Page<YskGoodCommentEntity> findByGoodId(Integer goodid,Integer page,Integer pageSize){
		PageRequest pageable = PageRequest.of(page, pageSize);
		Page<YskGoodCommentEntity> list = goodCommentRepository.findByGoodId(goodid,pageable);
		return list;
	}
	
	/**保存商品的评价
	 * @param goodComment 商品实体
	 * @return 结果json数据
	 */
	@Transactional
	public JsonResult saveGoodComment(ApiCommentVo vo) {
		JsonResult result = new JsonResult();
		if(vo.getUid()<1) {
			result.setCode(0);
			result.setMessage("用户id不能小于1");
			return result;
		}
		if(StringUtils.isEmpty(vo.getStarAbility())||StringUtils.isEmpty(vo.getStarAttitude())||StringUtils.isEmpty(vo.getStarPrice())||StringUtils.isEmpty(vo.getContent())) {
			result.setCode(0);
			result.setMessage("请填写所有评价");
			return result;
		}
		/*	if(goodComment.getSellerId()==0) {
			result.setCode(0);
			result.setMessage("商家id不能为空");
			return result;
		}*/
		YskGoodCommentEntity goodComment = new YskGoodCommentEntity();
		YskOrderDetailEntity orderDetail = orderDetailRepository.findByOrderIdAndIsCommentAndGoodId(vo.getOrderId(),(byte)0,vo.getGoodid());
		if(null==orderDetail) {
			result.setCode(0);
			result.setMessage("该商品已经评论");
			return result;
		}
		Double sum=Double.valueOf(vo.getStarAbility())+Double.valueOf(vo.getStarAttitude())+Double.valueOf(vo.getStarPrice());
		if(sum<5){
            goodComment.setLevel(0);
          }else if (sum>=5 && sum<=10) {
        	 goodComment.setLevel(1);
          }else if (sum>10) {
        	 goodComment.setLevel(2);
          }
		goodComment.setUid(vo.getUid());
		if(!StringUtils.isEmpty(vo.getUserName())) {
			goodComment.setUsername(vo.getUserName());
		}
		if(StringUtils.isEmpty(vo.getMobile())) {
			goodComment.setMobile(vo.getMobile());
		}
		goodComment.setGoodId(orderDetail.getGoodId());
		goodComment.setGoodName(orderDetail.getGoodName());
		goodComment.setGoodItem(orderDetail.getAttrText());
		goodComment.setGoodCoverImg(orderDetail.getGoodCoverImg());
		goodComment.setSellerId(orderDetail.getSellerId());
		goodComment.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
		goodComment.setOrderId(vo.getOrderId());
		goodComment.setContent(vo.getContent());
		goodCommentRepository.saveAndFlush(goodComment);
		orderDetail.setIsComment((byte)1);
		orderDetailRepository.saveAndFlush(orderDetail);
		YskGoodEntity goodEntity = goodRepository.findById(orderDetail.getGoodId()).get();
		goodEntity.setGoodComment(goodEntity.getGoodComment()+1);
		YskShopInfoEntity shopInfoEntity = shopInfoRepository.findById(orderDetail.getSellerId()).get();
		shopInfoEntity.setShopComment(shopInfoEntity.getShopComment()+1);
		result.setCode(1);
		result.setMessage("评论成功");
		return result;
	}
	
	/**根据用户id查找评论
	 * @param uid 用户id
	 * @param isComment 是否已评价
	 */
	public List<ApiOrderCommentVo> getGoodCommentByUid(Integer uid,Byte isComment) {
		
		List<ApiOrderCommentVo> list = new ArrayList<ApiOrderCommentVo>();
		
		if (isComment == 0) {  //查询未评价商品
			// 查询已完成订单
			List<YskOrderEntity> orderList = orderRepository.findByUserIdAndOrderStatus(uid, (byte) 3);
			List<Integer> orderIds = new ArrayList<Integer>();
			for (int i = 0; i < orderList.size(); i++) {
				orderIds.add(orderList.get(i).getOrderId());
			}
			// 查询已完成详细订单  未评价 状态为已发货 
			List<YskOrderDetailEntity> orderDetailList = orderDetailRepository.findByIsCommentAndIsSendAndOrderIdIn((byte)0, (byte)1,orderIds);
			
			for (int i = 0; i < orderDetailList.size(); i++) {
				YskOrderDetailEntity orderDetail = orderDetailList.get(i);
				//找到订单
				YskOrderEntity orderEntity = orderRepository.findByOrderId(orderDetail.getOrderId());
				ApiOrderCommentVo apiOrderCommentVo=new ApiOrderCommentVo();
				apiOrderCommentVo.setGoodName(orderDetail.getGoodName());
				apiOrderCommentVo.setGoodCoverImg(orderDetail.getGoodCoverImg());
				apiOrderCommentVo.setOrderNo(orderEntity.getOrderNo());
				apiOrderCommentVo.setOrderId(orderEntity.getOrderId());
				apiOrderCommentVo.setGoodid(orderDetail.getGoodId());
				list.add(apiOrderCommentVo);
			}
			
		} else { //查询已评价商品
			List<YskGoodCommentEntity> findByUid = goodCommentRepository.findByUid(uid);
			for (int i = 0; i < findByUid.size(); i++) {
				
				YskGoodCommentEntity yskGoodCommentEntity = findByUid.get(i);
				//找到订单
				YskOrderEntity orderEntity = orderRepository.findByOrderId(yskGoodCommentEntity.getOrderId());
				
				ApiOrderCommentVo apiOrderCommentVo=new ApiOrderCommentVo();
				apiOrderCommentVo.setId(yskGoodCommentEntity.getId());
				apiOrderCommentVo.setGoodName(yskGoodCommentEntity.getGoodName());
				apiOrderCommentVo.setGoodCoverImg(yskGoodCommentEntity.getGoodCoverImg());
				apiOrderCommentVo.setLevel(this.CalculationSeller(yskGoodCommentEntity.getSellerId()).getLevel());
				apiOrderCommentVo.setContent(yskGoodCommentEntity.getContent());
				apiOrderCommentVo.setOrderNo(orderEntity.getOrderNo());
				apiOrderCommentVo.setOrderId(orderEntity.getOrderId());
				apiOrderCommentVo.setGoodid(yskGoodCommentEntity.getGoodId());
				list.add(apiOrderCommentVo);
			}
			
		}
		
		return list;
	}
	
	//计算店铺的平局等级
	public YskGoodCommentEntity CalculationSeller(Integer sellerId) {
		Page<YskGoodCommentEntity> findBySellerId = goodCommentRepository.findBySellerId(sellerId, null);
		YskGoodCommentEntity yskGoodCommentEntity = new YskGoodCommentEntity();
		Integer levelSum=0;
		Integer starAbilitySum=0;
		Integer starAttitudeSum=0;
		Integer starPriceSum=0;
		for (YskGoodCommentEntity yskGoodComment : findBySellerId.getContent()) {
			starAbilitySum+=yskGoodComment.getStarAbility();
			starAttitudeSum+=yskGoodComment.getStarAttitude();
			starPriceSum+=yskGoodComment.getStarPrice();
			levelSum+=yskGoodComment.getLevel();
		}
		yskGoodCommentEntity.setLevel(levelSum/findBySellerId.getContent().size());
		yskGoodCommentEntity.setStarAbility(starAbilitySum/findBySellerId.getContent().size());
		yskGoodCommentEntity.setStarAttitude(starAttitudeSum/findBySellerId.getContent().size());
		yskGoodCommentEntity.setStarPrice(starPriceSum/findBySellerId.getContent().size());
		return yskGoodCommentEntity;
	}
}
