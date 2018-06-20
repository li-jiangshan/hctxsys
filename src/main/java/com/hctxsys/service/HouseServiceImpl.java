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

import com.hctxsys.entity.YskHouseEntity;
import com.hctxsys.entity.YskHouseTypeEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.repository.HouseRepository;
import com.hctxsys.repository.HouseTypeRepository;
import com.hctxsys.repository.ModuleImgRepository;
import com.hctxsys.util.Result;

@Service
public class HouseServiceImpl {
	@Autowired
	private HouseRepository houseRepository;
	@Autowired
	private HouseTypeRepository houseTypeRepository;
	@Autowired
	private ModuleImgRepository moduleImgRepository;
	/**
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<YskHouseEntity> selectHouse(int page,int pageSize,String houseProvince,String houseCity,String houseDistrict,Integer houseType,String contactsName) {
		Sort sort = new Sort(Direction.DESC, "houseId");
		PageRequest pageable = PageRequest.of(page, pageSize, sort);
		Page<YskHouseEntity> findAll = houseRepository.findAll(new Specification<YskHouseEntity>() {
			
			@Override
			public Predicate toPredicate(Root<YskHouseEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				if(!StringUtils.isEmpty(houseDistrict)) {
					predicates.add(cb.equal(root.get("houseProvince"), houseProvince));
				}
				if(!StringUtils.isEmpty(houseCity)) {
					predicates.add(cb.equal(root.get("houseCity"), houseCity));
				}
				if(!StringUtils.isEmpty(houseProvince)) {
					predicates.add(cb.equal(root.get("houseProvince"), houseProvince));
				}
				if(null!=houseType&&houseType>0) {
					predicates.add(cb.equal(root.get("houseType"), houseType));
				}
				if(!StringUtils.isEmpty(contactsName)) {
					predicates.add(cb.like(root.get("contactsName"), "%"+contactsName+"%"));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		},pageable);
		return findAll;
	}
	
	
	/**
	 * @return 房屋类型列表
	 */
	public List<YskHouseTypeEntity> houseTypeList(){
		List<YskHouseTypeEntity> list = houseTypeRepository.findAll();
		return list;
	}
	
	
	
	/**保存房屋信息
	 * @param house 房屋实体
	 * @param imgUrl 图片集``
	 * @return
	 */
	@Transactional
	public Result saveHouse(YskHouseEntity house,String[] imgUrl) {
		if(StringUtils.isEmpty(house.getHouseTitle())){
			return new Result(0, "请输入房产标题", "", "");
		}
		if(house.getHouseSize()==0) {
			return new Result(0, "请输入面积大小", "", "");
		}
		if(StringUtils.isEmpty(house.getContactsName())) {
			return new Result(0, "请输入联系人姓名", "", "");
		}
		if(StringUtils.isEmpty(house.getContactsPhone())) {
			return new Result(0, "请输入联系人电话", "", "");
		}
		if(StringUtils.isEmpty(house.getHouseCoverImg())) {
			return new Result(0, "请上传封面图片", "", "");
		}
		YskHouseEntity saveAndFlush = houseRepository.saveAndFlush(house);
		if(null!=imgUrl) {
			for (String string : imgUrl) {
				if(StringUtils.isEmpty(string)) {
					return new Result(0, "请点击上传", "", "");
				}
				YskModuleImgEntity moduleImgEntity = new YskModuleImgEntity();
				moduleImgEntity.setImgUrl(string);
				moduleImgEntity.setModuleType(0);
				moduleImgEntity.setModuleId(saveAndFlush.getHouseId());
				moduleImgRepository.saveAndFlush(moduleImgEntity);
			}
		}
		return new Result(1, "操作成功", "/Adminmall/Module/houseindex", "");
	}
	
	/**删除房屋信息
	 * @param houseId
	 * @return
	 */
	@Transactional
	public Result delete(Integer houseId) {
		houseRepository.deleteById(houseId);
		moduleImgRepository.deleteByModuleIdAndModuleType(houseId, 0);
		return new Result(1, "删除成功", "/Adminmall/Module/houseindex", "");
	}
	
	
	/**查找房屋信息
	 * @param houseId
	 * @return
	 */
	public YskHouseEntity findById(Integer houseId) {
		YskHouseEntity entity = houseRepository.findById(houseId).get();
		return entity;
	}
	
	
	/**查找图片集
	 * @param moduleId
	 * @return
	 */
	public List<YskModuleImgEntity> getModelImg(Integer moduleId) {
		List<YskModuleImgEntity> houseImgs = moduleImgRepository.findAllByModuleIdAndModuleType(moduleId, 0);
		return houseImgs;
	}
	
	/**删除图集
	 * @param moduleId
	 */
	@Transactional
	public void deleteModelImgs(Integer moduleId) {
		moduleImgRepository.deleteByModuleIdAndModuleType(moduleId, 0);
	}
}
