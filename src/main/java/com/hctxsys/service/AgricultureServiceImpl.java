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

import com.hctxsys.entity.YskAgricultureEntity;
import com.hctxsys.entity.YskAgricultureTypeEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.repository.AgricultureRepository;
import com.hctxsys.repository.AgricultureTypeRepository;
import com.hctxsys.repository.ModuleImgRepository;
import com.hctxsys.util.Result;
@Service
public class AgricultureServiceImpl {
	@Autowired
	private AgricultureRepository agricultureRepository;
	@Autowired
	private AgricultureTypeRepository agricultureTypeRepository;
	@Autowired
	private ModuleImgRepository moduleImgRepository;
	
	public Page<YskAgricultureEntity> findAgriculture(Integer page,Integer pageSize,String contactsName,Integer agricultureType){
		Sort sort = new Sort(Direction.DESC, "agricultureId");
		PageRequest pageable = PageRequest.of(page, pageSize, sort);
		Page<YskAgricultureEntity> findAll = agricultureRepository.findAll(new Specification<YskAgricultureEntity>() {
			
			@Override
			public Predicate toPredicate(Root<YskAgricultureEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				if(!StringUtils.isEmpty(contactsName)) {
					predicates.add(cb.like(root.get("contactsName"), "%"+contactsName+"%"));
				}
				if(null!=agricultureType&&agricultureType>0) {
					predicates.add(cb.equal(root.get("agricultureType"), agricultureType));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pageable);
		return findAll;
	}
	
	
	
	
	/**查找农业类型
	 * @return
	 */
	public List<YskAgricultureTypeEntity> findAgricultureType(){
		List<YskAgricultureTypeEntity> list = agricultureTypeRepository.findAll();
		return list;
	}
	
	
	
	/**保存农产品信息
	 * @param yskAgricultureEntity
	 * @param imgUrl 图片集
	 * @return
	 */
	@Transactional
	public Result saveAgriculture(YskAgricultureEntity Agriculture,String[] imgUrl) {
		if(StringUtils.isEmpty(Agriculture.getAgricultureTitle())) {
			return new Result(0, "农产品标题不能为空", "", "");
		}
		if(StringUtils.isEmpty(Agriculture.getAgricultureName())) {
			return new Result(0,"农产品名称不能为空" , "", "");
		}
		if(StringUtils.isEmpty(Agriculture.getContactsName())) {
			return new Result(0, "请输入联系人姓名", "", "");
		}
		if(StringUtils.isEmpty(Agriculture.getContactsPhone())) {
			return new Result(0, "请输入联系人电话", "", "");
		}
		if(StringUtils.isEmpty(Agriculture.getAgricultureCoverImg())) {
			return new Result(0, "请上传封面图片", "", "");
		}
		YskAgricultureEntity saveAndFlush = agricultureRepository.saveAndFlush(Agriculture);
		if(null!=imgUrl) {
			for (String string : imgUrl) {
				if(StringUtils.isEmpty(string)) {
					return new Result(0, "请点击上传", "", "");
				}
				YskModuleImgEntity moduleImgEntity = new YskModuleImgEntity();
				moduleImgEntity.setImgUrl(string);
				moduleImgEntity.setModuleType(2);
				moduleImgEntity.setModuleId(saveAndFlush.getAgricultureId());
				moduleImgRepository.saveAndFlush(moduleImgEntity);
			}
		}
		return new Result(1, "操作成功", "/Adminmall/Module/agricultureindex", "");
	}
	
	/**查找农产品
	 * @param id
	 * @return
	 */
	public YskAgricultureEntity findById(Integer id) {
		YskAgricultureEntity entity = agricultureRepository.findById(id).get();
		return entity;
	}
	
	/**删除图片集
	 * @param id
	 */
	@Transactional
	public void deleteModelImgs(Integer id) {
		moduleImgRepository.deleteByModuleIdAndModuleType(id, 2);
	}
	
	
	/**查找图片集
	 * @param moduleid
	 * @return
	 */
	public List<YskModuleImgEntity> findModuleImg(Integer moduleid){
		List<YskModuleImgEntity> list = moduleImgRepository.findAllByModuleIdAndModuleType(moduleid, 2);
		return list;
	}
	
	/**删除农产品
	 * @param id
	 */
	@Transactional
	public Result deleteAgriculture(Integer id) {
		agricultureRepository.deleteById(id);
		return new Result(1, "操作成功", "/Adminmall/Module/agricultureindex", "");
	}
}
