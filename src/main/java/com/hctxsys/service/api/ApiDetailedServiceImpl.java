package com.hctxsys.service.api;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskHuabaoDetailEntity;
import com.hctxsys.entity.YskIntegralDetailEntity;
import com.hctxsys.entity.YskKucunIntegralDetailEntity;
import com.hctxsys.entity.YskMoneyDetailEntity;
import com.hctxsys.repository.HuabaoDetailRepository;
import com.hctxsys.repository.IntegralDetailRepository;
import com.hctxsys.repository.KucunIntegralDetailRepository;
import com.hctxsys.repository.MoneyDetailRepository;
import com.hctxsys.vo.api.JsonResult;

@Service("apiDetailedService")
public class ApiDetailedServiceImpl {
	@Autowired
	private MoneyDetailRepository moneyDetailRepository;
	@Autowired
	private HuabaoDetailRepository huabaoDetailRepository;
	@Autowired
	private IntegralDetailRepository integralDetailRepository;
	@Autowired
	private KucunIntegralDetailRepository kucunIntegralDetailRepository;
	
	/**查询金额明细
	 * @param uid
	 * @param formType 1转入 2转出 
	 * @return
	 */
	public Page<YskMoneyDetailEntity> moneyDetail(Integer uid,String formType){
		Sort sort = new Sort(Direction.DESC,"id");
		PageRequest pageable = PageRequest.of(0, 500,sort);
		Page<YskMoneyDetailEntity> findAll = moneyDetailRepository.findAll(new Specification<YskMoneyDetailEntity>() {
			@Override
			public Predicate toPredicate(Root<YskMoneyDetailEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.equal(root.get("uid"), uid));
				if(!"0".equals(formType)) {
					predicates.add(cb.equal(root.get("fromType"), formType));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		},pageable);
		return findAll;
	}
	
	/**查询华宝明细
	 * @param uid
	 * @param formType 1转入 2转出
	 * @return
	 */
	public Page<YskHuabaoDetailEntity> huabaoDetail(Integer uid,String formType){
		Sort sort = new Sort(Direction.DESC,"id");
		PageRequest pageable = PageRequest.of(0, 500,sort);
		Page<YskHuabaoDetailEntity> findAll = huabaoDetailRepository.findAll(new Specification<YskHuabaoDetailEntity>() {
			@Override
			public Predicate toPredicate(Root<YskHuabaoDetailEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.equal(root.get("uid"), uid));
				if(!"0".equals(formType)) {
					predicates.add(cb.equal(root.get("fromType"), formType));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		},pageable);
		return findAll;
	}
	
	/**查询积分明细
	 * @param uid
	 * @param formType 1转入 2转出
	 * @return
	 */
	public Page<YskIntegralDetailEntity> integralDetail(Integer uid,String formType){
		Sort sort = new Sort(Direction.DESC,"id");
		PageRequest pageable = PageRequest.of(0, 500,sort);
		Page<YskIntegralDetailEntity> findAll = integralDetailRepository.findAll(new Specification<YskIntegralDetailEntity>() {
			@Override
			public Predicate toPredicate(Root<YskIntegralDetailEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.equal(root.get("uid"), uid));
				if(!"0".equals(formType)) {
					predicates.add(cb.equal(root.get("fromType"), formType));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		},pageable);
		return findAll;
	}
	
	/**查询库存积分明细
	 * @param uid
	 * @param formType 1转入 2转出
	 * @return
	 */
	public Page<YskKucunIntegralDetailEntity> kuCunIntegralDetail(Integer uid,String formType){
		Sort sort = new Sort(Direction.DESC,"id");
		PageRequest pageable = PageRequest.of(0, 500,sort);
		Page<YskKucunIntegralDetailEntity> findAll = kucunIntegralDetailRepository.findAll(new Specification<YskKucunIntegralDetailEntity>() {
			@Override
			public Predicate toPredicate(Root<YskKucunIntegralDetailEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.equal(root.get("uid"), uid));
				if(!"0".equals(formType)) {
					predicates.add(cb.equal(root.get("fromType"), formType));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		},pageable);
		return findAll;
	}
	
	
	/**查询各项明细明细
	 * @param uid
	 * @param type 0金额 1积分 2华宝 3库存
	 * @param formType 1转入 2转出
	 * @return
	 */
	public JsonResult findByType(Integer uid,Integer type,String formType) {
		JsonResult result = new JsonResult();
		if(type==0) {
			Page<YskMoneyDetailEntity> moneyDetail = this.moneyDetail(uid, formType);
			for (YskMoneyDetailEntity yskMoneyDetailEntity : moneyDetail.getContent()) {
				yskMoneyDetailEntity.setUserEntity(null);
			}
			result.setCode(1);
			result.setMessage("查找金额明细成功");
			result.setData(moneyDetail.getContent());
			return result;
		}
		if(type==1) {
			Page<YskIntegralDetailEntity> integralDetail = this.integralDetail(uid, formType);
			result.setCode(1);
			result.setMessage("查找积分明细成功");
			result.setData(integralDetail.getContent());
			return result;
		}
		if(type==2) {
			Page<YskHuabaoDetailEntity> huabaoDetail = this.huabaoDetail(uid, formType);
			result.setCode(1);
			result.setMessage("查找华宝明细成功");
			result.setData(huabaoDetail.getContent());
			return result;
		}
		if(type==3) {
			Page<YskKucunIntegralDetailEntity> kuCunIntegralDetail = this.kuCunIntegralDetail(uid, formType);
			result.setCode(1);
			result.setMessage("查找库存明细成功");
			result.setData(kuCunIntegralDetail.getContent());
			return result;
		}
		result.setCode(0);
		result.setMessage("没有输入正确的类型");
		return result;
	}
	
	
	
}
