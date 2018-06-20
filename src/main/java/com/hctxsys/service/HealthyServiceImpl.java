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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hctxsys.entity.YskCarEntity;
import com.hctxsys.entity.YskHealthyEntity;
import com.hctxsys.entity.YskHealthyTypeEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.repository.HealthyRepositry;
import com.hctxsys.repository.HealthyTypeRepository;
import com.hctxsys.repository.ModuleImgRepository;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.CarVo;
import com.hctxsys.vo.HealthyVo;

@Service

public class HealthyServiceImpl {
	@Autowired
	private HealthyRepositry healthyRepositry;
	@Autowired
	private HealthyTypeRepository healthyTypeRepository;
	@Autowired
	private ModuleImgRepository imgRepository;

	/*
	 * 大健康首页
	 */
	public TableResult indexTable(TableResult result) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());// 获取分页对象
		Page<YskHealthyEntity> page = healthyRepositry.findAll(new Specification<YskHealthyEntity>() {
			@Override
			public Predicate toPredicate(Root<YskHealthyEntity> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
				if (null != result.getLevel()) {// 类型查询
					predicates.add(criteriaBuilder.equal(root.get("healthyType"),
							Byte.valueOf(String.valueOf(result.getLevel()))));
				}
				if ((null != result.getKeyword() && !("".equals(result.getKeyword())))) {
					predicates.add(
							criteriaBuilder.like(root.get("contacts").as(String.class), '%' + result.getKeyword() + '%'));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, request);
		TableResult tableResult = new TableResult();
		BeanUtils.copyProperties(result, tableResult);// 将条件信息复制给tableResult
		tableResult.setTotal(page.getTotalElements());// 设置总记录数
		tableResult.setRows(page.getContent());// 设置分页后的集合
		tableResult.setPageCount(Long.valueOf(page.getTotalPages()));// 设置总页数
		return tableResult;
	}

	public List<YskHealthyTypeEntity> getAllType() {
		return healthyTypeRepository.findAll();
	}

	@Transactional
	public Result healthyInsert(HealthyVo healthyVo) {
		YskHealthyEntity healthy = new YskHealthyEntity();
		BeanUtils.copyProperties(healthyVo, healthy);
		try {
			YskHealthyEntity newhealthy = healthyRepositry.saveAndFlush(healthy);
			for (String s : healthyVo.getImgList()) {
				YskModuleImgEntity moduleImg = new YskModuleImgEntity();
				moduleImg.setImgUrl(s);
				moduleImg.setModuleId(newhealthy.getId());
				moduleImg.setModuleType(4);
				imgRepository.saveAndFlush(moduleImg);
			}
			return new Result(200, "保存成功，即将跳转页面", "/Adminmall/Module/Healthy");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new Result(500, "服务器异常，保存失败");
		}
	}

	public YskHealthyEntity findById(Integer id) {
		return healthyRepositry.findById(id).get();
	}

	@Transactional
	public Result healthyUpdate(HealthyVo healthyVo) {
		try {
            YskHealthyEntity healthy = new YskHealthyEntity();
            BeanUtils.copyProperties(healthyVo,healthy);
            YskHealthyEntity saveAndFlush = healthyRepositry.saveAndFlush(healthy);
            for (String s : healthyVo.getImgList()) {
                YskModuleImgEntity moduleImg = new YskModuleImgEntity();
                moduleImg.setImgUrl(s);
                moduleImg.setModuleId(saveAndFlush.getId());
                moduleImg.setModuleType(4);
                imgRepository.saveAndFlush(moduleImg);
            }
            return new Result(200,"修改成功即将跳转","/Adminmall/Module/Healthy");
        
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new Result(500, "服务器异常");
		}

	}

	public Result deleteById(Integer id) {
		try {
			healthyRepositry.deleteById(id);
			return new Result(200, "删除成功", "/adminmall/module/healthyIndex");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(500, "删除失败，服务器异常");
		}
	}

	 public List<YskModuleImgEntity> getImg(Integer moduleId) {
	        List<YskModuleImgEntity> imglist = imgRepository.findAllByModuleIdAndModuleType(moduleId, 4);
	        return imglist;
	    }
	 
	 @Transactional
	    public int deleteImg(Integer id) {
	        try {
	            imgRepository.deleteById(id);
	        } catch (Exception e) {
	            return 0;
	        }
	        return 1;
	    }
}
