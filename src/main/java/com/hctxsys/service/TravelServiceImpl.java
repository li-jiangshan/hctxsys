package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.entity.YskTravelEntity;
import com.hctxsys.entity.YskTravelTypeEntity;
import com.hctxsys.repository.ModuleImgRepository;
import com.hctxsys.repository.TravelRepository;
import com.hctxsys.repository.TravelTypeRepository;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.TravelVo;

@Service
public class TravelServiceImpl {
	@Autowired
	private TravelRepository travelRepository;
	@Autowired
	private TravelTypeRepository travelTypeRepository;
	@Autowired
	private ModuleImgRepository imgRepository;

	/**
	 * 旅游首页查询列表
	 * 
	 * @param tableResult
	 * @return
	 */
	public TableResult index(TableResult tableResult) {
		Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "moduleId"));
		PageRequest pageRequest = PageRequest.of(tableResult.getPageNumber(), tableResult.getPageSize(),sort);
		Page<YskTravelEntity> all = travelRepository.findAll(new Specification<YskTravelEntity>() {
			private static final long serialVersionUID = -8182108956348905747L;

			@Override
			public Predicate toPredicate(Root<YskTravelEntity> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				ArrayList<Predicate> predicates = new ArrayList<>();
				if (null != tableResult.getLevel()) {// 等级查询
					predicates.add(criteriaBuilder.equal(root.get("ysktraveltypeentity").get("typeId"),
							Byte.valueOf(String.valueOf(tableResult.getLevel()))));
				}
				if (null != tableResult.getTypeText() && !("".equals(tableResult.getTypeText()))) {
					predicates.add(criteriaBuilder.like(root.get("contact"), '%' + tableResult.getTypeText() + '%'));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageRequest);
		TableResult result = new TableResult();
		BeanUtils.copyProperties(tableResult, result);
		result.setTotal(all.getTotalElements());
		result.setPageCount((long) all.getTotalPages());
		result.setRows(all.getContent());
		return result;
	}

	/**
	 * 查询所有旅游类型
	 * 
	 * @return
	 */
	public List<YskTravelTypeEntity> getAllType() {
		return travelTypeRepository.findAll();
	}

	/**
	 * 录入旅游信息
	 * 
	 * @param travelvo
	 * @return
	 */
	@Transactional
	public Result travelInsert(TravelVo travelvo) {
		YskTravelEntity ysktravelentity = new YskTravelEntity();
		BeanUtils.copyProperties(travelvo, ysktravelentity);
		try {
			YskTravelEntity travelEntity = travelRepository.saveAndFlush(ysktravelentity);
			for (String s : travelvo.getImgList()) {
				YskModuleImgEntity moduleImg = new YskModuleImgEntity();
				moduleImg.setImgUrl(s);
				moduleImg.setModuleId(travelEntity.getModuleId());
				moduleImg.setModuleType(3);
				imgRepository.saveAndFlush(moduleImg);
			}
			return new Result(200, "保存成功，即将跳转页面", "/Adminmall/Module/travelIndex");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new Result(200, "服务器异常，保存失败");
		}
	}

	/**
	 * 根据id查询旅游详细信息
	 * 
	 * @param id
	 * @return
	 */
	public YskTravelEntity findById(Integer id) {
		return travelRepository.findById(id).get();
	}

	/**
	 * 根据moduleId和ModuleType=3查询旅游图片
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<YskModuleImgEntity> getImg(Integer moduleId) {
		List<YskModuleImgEntity> imglist = imgRepository.findAllByModuleIdAndModuleType(moduleId, 3);
		return imglist;
	}

	/**
	 * 删除图片
	 * 
	 * @return
	 */
	@Transactional
	public int deleteImg(Integer id) {
		try {
			imgRepository.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	/**
	 * 修改旅游信息
	 * 
	 * @param travelvo
	 * @return
	 */
	@Transactional
	public Result travelUpdate(TravelVo travelvo) {
		try {
			YskTravelEntity ysktravelentity = new YskTravelEntity();
			BeanUtils.copyProperties(travelvo, ysktravelentity);
			YskTravelEntity travelEntity = travelRepository.saveAndFlush(ysktravelentity);
			for (String s : travelvo.getImgList()) {
				YskModuleImgEntity moduleImg = new YskModuleImgEntity();
				moduleImg.setImgUrl(s);
				moduleImg.setModuleId(travelEntity.getModuleId());
				moduleImg.setModuleType(3);
				imgRepository.saveAndFlush(moduleImg);
			}
			return new Result(200, "修改成功即将跳转", "/Adminmall/Module/travelIndex");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new Result(500, "服务器异常");
		}
	}
	
	/**
	 * 删除旅游信息
	 * 
	 * @param moduleId
	 * @return
	 */
	@Transactional
	public Result deleteById(Integer moduleId) {
		try {
			travelRepository.deleteById(moduleId);
			imgRepository.deleteByModuleIdAndModuleType(moduleId, 3);
			return new Result(1, "删除成功", "/Adminmall/Module/travelIndex", "");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(500, "删除失败，服务器异常");
		}
	}
}
