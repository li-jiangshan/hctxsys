package com.hctxsys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskMessageEntity;
import com.hctxsys.entity.YskUserAdviceEntity;
import com.hctxsys.repository.MessageRepository;
import com.hctxsys.repository.UseradviceRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.util.Result;

@Service("UseradviceService")
public class UseradviceServiceImpl {
	@Autowired
	private UseradviceRepository useradviceRepository;
	@Autowired
	private MessageRepository messageRepository;
	
	/**查找未读和已读
	 * @param page
	 * @param pageSize
	 * @param status
	 * @return
	 */
	public Page<YskUserAdviceEntity> findAdviceNotEqual(Integer page,Integer pageSize,byte status,String keyword){
		PageRequest pageable = PageRequest.of(page, pageSize);
		Page<YskUserAdviceEntity> findAll = useradviceRepository.findAll(new Specification<YskUserAdviceEntity>() {
			
			@Override
			public Predicate toPredicate(Root<YskUserAdviceEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				if(!StringUtils.isEmpty(keyword)) {
					Predicate like = cb.like(root.get("account"), "%" + keyword + "%");
					Predicate like2 = cb.like(root.get("username"), "%" + keyword + "%");
					predicates.add(cb.or(cb.and(like),cb.and(like2)));
				}
				predicates.add(cb.notEqual(root.get("status"),status));
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pageable);
		return findAll;
	}
	
	/**查找已回复的
	 * @param page
	 * @param pageSize
	 * @param status
	 * @return
	 */
	public Page<YskUserAdviceEntity> findAdviceEqual(Integer page,Integer pageSize,byte status,String keyword){
		PageRequest pageable = PageRequest.of(page, pageSize);
		Page<YskUserAdviceEntity> findAll = useradviceRepository.findAll(new Specification<YskUserAdviceEntity>() {
			
			@Override
			public Predicate toPredicate(Root<YskUserAdviceEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				if(!StringUtils.isEmpty(keyword)) {
					Predicate like = cb.like(root.get("account"), "%" + keyword + "%");
					Predicate like2 = cb.like(root.get("username"), "%" + keyword + "%");
					predicates.add(cb.or(cb.and(like),cb.and(like2)));
				}
				predicates.add(cb.equal(root.get("status"),status));
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pageable);
		return findAll;
	}
	
	/**查看反馈详情
	 * @param id
	 * @return
	 */
	public YskUserAdviceEntity findById(Integer id) {
		YskUserAdviceEntity yskUserAdviceEntity = useradviceRepository.findById(id).get();
		if(yskUserAdviceEntity.getStatus()==0) {
			yskUserAdviceEntity.setStatus((byte)1);
			useradviceRepository.saveAndFlush(yskUserAdviceEntity);
		}
		return yskUserAdviceEntity;
	}
	
	/**删除
	 * @param id
	 * @return
	 */
	@Transactional
	public Result deleteById(Integer id) {
		useradviceRepository.deleteById(id);
		return new Result(1, "删除成功", "", "");
	} 
	
	
	/**保存回复
	 * @param id
	 * @param reply
	 * @return
	 */
	@Transactional
	public Result saveById(Integer id,String reply) {
		YskMessageEntity message = null;
		if(StringUtils.isEmpty(reply)) {
			return new Result(0, "请填写回复内容", "", "");
		}
		YskUserAdviceEntity yskUserAdviceEntity = useradviceRepository.findById(id).get();
		int uid = yskUserAdviceEntity.getUserid();
		yskUserAdviceEntity.setStatus((byte)2);
		useradviceRepository.saveAndFlush(yskUserAdviceEntity);
		if(null!=yskUserAdviceEntity) {
			YskMessageEntity messageEntity = new YskMessageEntity();
			messageEntity.setContent(reply);
			messageEntity.setTitle("反馈回复");
			messageEntity.setUid(uid);
			messageEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
			messageEntity.setStatus((byte)0);
			messageEntity.setType(1);
			message = messageRepository.saveAndFlush(messageEntity);
		}
		if(null!=message) {
			return new Result(1, "操作成功", "/Admin/Useradvice/index", "");
		}else {
			return new Result(0, "操作失败", "", "");
		}
	}
}
