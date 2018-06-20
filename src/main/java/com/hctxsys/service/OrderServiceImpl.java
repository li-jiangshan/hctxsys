package com.hctxsys.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskOrderDetailEntity;
import com.hctxsys.entity.YskOrderEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.OrderDetailRepository;
import com.hctxsys.repository.OrderRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;

@Service("OrderyskService")
public class OrderServiceImpl {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	/**
	 * 订单首页查询
	 *
	 * @param page
	 * @param size
	 * @param dateStart
	 * @param dateEnd
	 * @param querytype
	 * @param keyword
	 * @return
	 */
	@Transactional
	public Page<YskOrderEntity> shouyefindAll(int page, int size, String dateStart, String dateEnd, String querytype,
			String keyword, byte zhuangtai, HttpServletRequest request, HttpSession session) {
		Sort sort = new Sort(Direction.DESC, "orderId");
		PageRequest pagerequest = PageRequest.of(page, size, sort);// 获取分页对象

		Integer sellerId = (Integer) request.getSession().getAttribute("sellerId");

		Page<YskOrderEntity> list = orderRepository.findAll(new Specification<YskOrderEntity>() {

			private static final long serialVersionUID = -5218541064065388291L;

			@Override
			public Predicate toPredicate(Root<YskOrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				// 订单状态
				predicates.add(cb.equal(root.get("orderStatus"), zhuangtai));
				// 用户假删除
				predicates.add(cb.equal(root.get("orderIsDelete"), 0));

				if (sellerId == null) {
					predicates.add(cb.equal(root.get("sellerId"), 0));
				} else {
					predicates.add(cb.equal(root.get("sellerId"), sellerId));
				}

				if (null != dateStart) {
					 String date = DateUtils.getTime(dateStart, new SimpleDateFormat("yyyy-MM-dd"));
					 predicates.add(cb.greaterThan(root.get("orderCreateTime"), Long.valueOf(date)));
				}
				if (null != dateEnd) {
					 String date = DateUtils.getTime(dateEnd, new SimpleDateFormat("yyyy-MM-dd"));
	                    predicates.add(cb.lessThan(root.get("orderCreateTime"), Long.valueOf(date) + 60 * 60 * 24));
				}
				if (null != querytype && null != keyword) {
					if ("orderNo".equals(querytype) || "userName".equals(querytype) || "userMobile".equals(querytype)) {
						predicates.add(cb.like(root.get(querytype), '%' +keyword + '%'));
					} else {
						predicates.add(cb.like(root.<YskUserEntity>get("userEntity").get(querytype), '%' +keyword + '%'));
					}
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pagerequest);
		return list;
	}

	/**
	 * 根据orderId查询详细页面
	 *
	 * @param orderId
	 * @return
	 */
	public YskOrderEntity findById(Integer orderId) {
		YskOrderEntity yskorderentity = orderRepository.findById(orderId).get();
		return yskorderentity;
	}

	/**
	 * 根据orderId查询
	 *
	 * @param orderId
	 * @return
	 */
	public List<YskOrderDetailEntity> findByIdgood(Integer orderId) {
		List<YskOrderDetailEntity> list = orderDetailRepository.findgoodById(orderId);
		// return null;
		return list;
	}

	/**
	 * 更新订单状态
	 *
	 * @param orderStatus
	 * @return
	 */
	public Result checkOrderStatus(Integer orderId, Byte orderStatus) {
		if (orderStatus == null || orderStatus == 0) {
			return new Result(0, "请选择状态", "", "");
		}
		YskOrderEntity yskorderentity = orderRepository.getOne(orderId);
		yskorderentity.setOrderStatus(orderStatus);
		YskOrderEntity orderEntity = orderRepository.saveAndFlush(yskorderentity);
		
		if(orderStatus == 2) {
			List<YskOrderDetailEntity> yskorderdetailentityList = orderDetailRepository.findByOrderId(orderId);
			for(YskOrderDetailEntity yskorderdetailentity : yskorderdetailentityList) {
				yskorderdetailentity.setIsSend((byte)1);
				orderDetailRepository.saveAndFlush(yskorderdetailentity);
			}
		}

		if (null != orderEntity) {
			return new Result(1, "操作成功", "/Adminmall/Order/index", "");
		} else {
			return new Result(0, "操作失败", "", "");
		}
	}

	/**
	 * 删除 修改用户假删除字段
	 *
	 * @param
	 * @return
	 */
	public Result checkOrderIsDelete(Integer orderId) {
		YskOrderEntity yskorderentity = orderRepository.getOne(orderId);
		byte i = 1;
		yskorderentity.setOrderIsDelete(i);
		YskOrderEntity orderEntity = orderRepository.saveAndFlush(yskorderentity);

		if (null != orderEntity) {
			return new Result(1, "操作成功", "/Adminmall/Order/index", "");
		} else {
			return new Result(0, "操作失败", "", "");
		}
	}

	/**
	 * 分页
	 *
	 * @param root
	 * @param page
	 * @param dateStart
	 * @param dateEnd
	 * @param querytype
	 * @param keyword
	 * @return
	 */
	public String setHref(String root, Integer page, String dateStart, String dateEnd, String querytype,
			String keyword) {
		StringBuffer sb = new StringBuffer(root);
		sb.append("index?");
		if (null != dateStart) {
			sb.append("dateStart=" + dateStart + "&");
		}
		if (null != dateEnd) {
			sb.append("dateEnd=" + dateEnd + "&");
		}
		if (null != querytype) {
			sb.append("querytype=" + querytype + "&");
		}
		if (null != keyword) {
			sb.append("keyword=" + keyword + "&");
		}
		if (null != page) {
			sb.append("page=" + page);
		}
		return sb.toString();
	}

	/**
	 * 查询用户已支付订单
	 * @param tableResult
	 * @return
	 */
	public TableResult userOrder(Integer id,TableResult result) {
	    //按订单创建时间降序
        Sort sort = Sort.by(new Sort.Order(Direction.DESC, "orderCreateTime"));
        PageRequest pageRequest = PageRequest.of(result.getPageNumber(), result.getPageSize(),sort);
        Page<YskOrderEntity> all = orderRepository.findAll(new Specification<YskOrderEntity>() {
			private static final long serialVersionUID = -7458886099948483395L;

			@Override
            public Predicate toPredicate(Root<YskOrderEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
               List<Predicate> predicates=new ArrayList<>();
               predicates.add(criteriaBuilder.equal(root.get("orderIsDelete"),0));
               predicates.add(criteriaBuilder.equal(root.get("orderStatus"),3));
               predicates.add(criteriaBuilder.equal(root.get("userId"),id));
                if (null != result.getBeginDate() && !("".equals(result.getBeginDate()))) {//开始时间查询
                    String date = DateUtils.getTime(result.getBeginDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.greaterThan(root.get("orderCreateTime"), Long.valueOf(date)));
                }
                if (null != result.getEndDate() && !("".equals(result.getEndDate()))) {//结束时间查询
                    String date = DateUtils.getTime(result.getEndDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.lessThan(root.get("orderCreateTime"), Long.valueOf(date) + 60 * 60 * 24));//将时间加到查询时间的后一天0点
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageRequest);
        TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result,tableResult);
        tableResult.setRows(all.getContent());
        tableResult.setTotal(all.getTotalElements());
        tableResult.setPageCount((long) all.getTotalPages());
        return tableResult;
    }
}
