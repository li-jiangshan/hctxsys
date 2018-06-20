package com.hctxsys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskMessageEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.MessageRepository;
import com.hctxsys.repository.YsKUserRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.util.Result;
import com.hctxsys.vo.MessageVo;

@Service("messageService")
public class MessageServiceImpl {
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private YsKUserRepository ysKUserRepository;
	
	public Page<YskMessageEntity> findMessage(Integer page,Integer pageSize,String keyword) {
		Sort sort = new Sort(Direction.DESC,"id");
		PageRequest pageable = PageRequest.of(page, pageSize, sort);
		Page<YskMessageEntity> findAll = messageRepository.findAll(new Specification<YskMessageEntity>() {
			
			@Override
			public Predicate toPredicate(Root<YskMessageEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				if(!StringUtils.isEmpty(keyword)) {
					Predicate like = cb.equal(root.get("id").as(String.class), keyword);
					Predicate like2 = cb.like(root.get("title"), "%" + keyword + "%");
					predicates.add(cb.or(cb.and(like),cb.and(like2)));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pageable);
		return findAll;
	}
	
	/**查找指定会员
	 * @param uid 0为全体
	 * @return
	 */
	public String findMobile(Integer uid) {
		if(uid==0) {
			return "全体";
		}
		Optional<YskUserEntity> findById = ysKUserRepository.findById(uid);
		if(findById.isPresent()) {
			YskUserEntity yskUserEntity = findById.get();
			return yskUserEntity.getMobile();
		}else {
			return "";
		}
	}
	
	/**删除消息
	 * @param id
	 * @return
	 */
	@Transactional
	public Result deleteMessage(Integer id) {
		System.out.println(id);
		YskMessageEntity yskMessageEntity = messageRepository.findById(id).get();
		messageRepository.delete(yskMessageEntity);
		return new Result(1, "删除成功", "/Admin/Message/index", "");
	}
	
	/**查询具体的消息
	 * @param id
	 * @return
	 */
	public MessageVo getMessage(Integer id) {
		MessageVo messageVo = new MessageVo();
		YskMessageEntity message = messageRepository.findById(id).get();
		BeanUtils.copyProperties(message, messageVo);
		if(message.getUid()==0) {
			messageVo.setMobile("");
		}else {
			messageVo.setMobile(this.findMobile(message.getUid()));
		}
		return messageVo;
	}
	
	/**修改消息
	 * @param id
	 * @param type
	 * @param mobile
	 * @param title
	 * @param content
	 * @return
	 */
	public Result saveMessage(Integer id,Integer type,String mobile,String title,String content) {
		YskMessageEntity messageEntity=null;
		if(type==0) {
			return new Result(0, "分类不能为空", "", "");
		}
		if(StringUtils.isEmpty(title)) {
			return new Result(0, "标题不能为空", "", "");
		}
		if(null==id) {
			messageEntity = new YskMessageEntity();
		}else {
			messageEntity = messageRepository.findById(id).get();
		}
		messageEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
		messageEntity.setSend(1);
		messageEntity.setStatus((byte)0);
		if(!StringUtils.isEmpty(mobile)) {
			YskUserEntity userEntity = ysKUserRepository.findByMobile(mobile);
			if(null==userEntity) {
				return new Result(0, "指定用户不存在", "", "");
			}
			messageEntity.setUid(userEntity.getUserid());
		}
		messageEntity.setType(type);
		messageEntity.setTitle(title);
		messageEntity.setContent(content);
		messageRepository.save(messageEntity);
		return new Result(1, "保存成功", "/Admin/Message/index", "");
	}
}
