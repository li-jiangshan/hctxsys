package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskFinanceEntity;
import com.hctxsys.entity.YskFinanceTypeEntity;
import com.hctxsys.entity.YskHouseEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.repository.FinanceRepository;
import com.hctxsys.repository.FinanceTypeRepository;
import com.hctxsys.repository.ModuleImgRepository;
import com.hctxsys.util.Result;

@Service
public class FinanceServiceImpl {
	@Autowired
	private FinanceRepository financeRepository;
	@Autowired
	private FinanceTypeRepository financeTypeRepository;
	@Autowired
	private ModuleImgRepository moduleImgRepository;
	
	
	public Page<YskFinanceEntity> selectFinance(Integer page,Integer pageSize,String contactsName,Integer financeType){
		Sort sort = new Sort(Direction.DESC, "moduleId");
		PageRequest pageable = PageRequest.of(page, pageSize, sort);
		Page<YskFinanceEntity> findAll = financeRepository.findAll(new Specification<YskFinanceEntity>() {
			
			@Override
			public Predicate toPredicate(Root<YskFinanceEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				if(!StringUtils.isEmpty(contactsName)) {
					predicates.add(cb.like(root.get("contact"), "%"+contactsName+"%"));
				}
				if(null!=financeType&&financeType>0) {
					predicates.add(cb.equal(root.get("typeId"), financeType));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pageable);
		return findAll;
	}
	
	
	
	/**金融类型列表
	 * @return
	 */
	public List<YskFinanceTypeEntity> financeTypeList(){
		List<YskFinanceTypeEntity> list = financeTypeRepository.findAll();
		return list;
	}
	
	
	
	/**
	 * @param finance
	 * @param imgUrl
	 * @return
	 */
	@Transactional
	public Result saveFinance(YskFinanceEntity finance,String[] imgUrl) {
		if(StringUtils.isEmpty(finance.getTitle())){
			return new Result(0, "请输入金融标题", "", "");
		}
		if(StringUtils.isEmpty(finance.getContact())) {
			return new Result(0, "请输入内容", "", "");
		}
		if(StringUtils.isEmpty(finance.getContact())) {
			return new Result(0, "请输入联系人姓名", "", "");
		}
		if(StringUtils.isEmpty(finance.getPhone())) {
			return new Result(0, "请输入联系人电话", "", "");
		}
		if(StringUtils.isEmpty(finance.getCoverimg())) {
			return new Result(0, "请上传封面图片", "", "");
		}
		YskFinanceEntity saveAndFlush = financeRepository.saveAndFlush(finance);
		if(null!=imgUrl) {
			for (String string : imgUrl) {
				if(StringUtils.isEmpty(string)) {
					return new Result(0, "请点击上传", "", "");
				}
				YskModuleImgEntity moduleImgEntity = new YskModuleImgEntity();
				moduleImgEntity.setImgUrl(string);
				moduleImgEntity.setModuleType(5);
				moduleImgEntity.setModuleId(saveAndFlush.getModuleId());
				moduleImgRepository.saveAndFlush(moduleImgEntity);
			}
		}
		return new Result(1, "操作成功", "/Adminmall/Module/financeindex", "");
	}
	
	
	/**
	 * @param id
	 * @return
	 */
	public YskFinanceEntity findFinance(Integer id) {
		YskFinanceEntity entity = financeRepository.findById(id).get();
		return entity;
	}
	
	
	/**查找图集
	 * @param moduleId
	 * @return
	 */
	public List<YskModuleImgEntity> findModuleImg(Integer moduleId){
		List<YskModuleImgEntity> list = moduleImgRepository.findAllByModuleIdAndModuleType(moduleId, 5);
		return list;
	}
	
	@Transactional
	public void deleteModuleImg(Integer id) {
		moduleImgRepository.deleteByModuleIdAndModuleType(id, 5);
	}
	
	@Transactional
	public Result deleteFinance(Integer id) {
		financeRepository.deleteById(id);
		return new Result(1, "操作成功", "/Adminmall/Module/financeindex", "");
	}
}
