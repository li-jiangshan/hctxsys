package com.hctxsys.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.hctxsys.entity.YskGroupEntity;
import com.hctxsys.entity.YskMenuEntity;
import com.hctxsys.repository.GroupRepository;
import com.hctxsys.repository.MenuRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.GroupVo;

@Service("GroupService")
public class GroupServiceImpl {
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private MenuRepository menuRepository;

	/**
	 * 角色管理首页查询
	 * 
	 * @param result
	 * @return
	 */
	public TableResult selectGroup(TableResult result) {
		Sort sort = new Sort(Direction.DESC, "sort");
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize(), sort);
		Page<YskGroupEntity> groupPage = groupRepository.findAll(new Specification<YskGroupEntity>() {
			private static final long serialVersionUID = -923643907115590810L;

			@Override
			public Predicate toPredicate(Root<YskGroupEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (result.getKeyword() != null) {
					predicates.add(cb.like(root.get("title").as(String.class), '%' + result.getKeyword() + '%'));
					query.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
				}
				return query.getRestriction();
			}
		}, request);
		result.setRows(groupPage.getContent());
		result.setTotal(groupPage.getTotalElements());
		result.setPageCount(Long.valueOf(groupPage.getTotalPages()));
		return result;
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public YskGroupEntity findById(Integer id) {
		YskGroupEntity yskgroupentity = groupRepository.findById(id).get();
		return yskgroupentity;
	}

	/**
	 * 查询顶部菜单
	 * 
	 * @return
	 */
	public List<YskMenuEntity> getTopMenu() {

		List<YskMenuEntity> toplist = new ArrayList<YskMenuEntity>();

		toplist = menuRepository.findByStatusAndLevel((byte) 1, 0);

		return toplist;
	}

	/**
	 * 查询第一级菜单
	 * 
	 * @return
	 */
	public List<YskMenuEntity> getFirstMenu() {

		List<YskMenuEntity> firstmenulist = new ArrayList<YskMenuEntity>();
		Sort sort = new Sort(Sort.Direction.ASC, "sort");
		firstmenulist = menuRepository.findByStatusAndLevel((byte) 1, 1, sort);
		return firstmenulist;
	}

	/**
	 * 查询第二级菜单
	 * 
	 * @return
	 */
	public List<YskMenuEntity> getSecondMenu() {
		List<YskMenuEntity> secondmenulist = new ArrayList<YskMenuEntity>();
		Sort sort = new Sort(Sort.Direction.ASC, "sort");
		secondmenulist = menuRepository.findByStatusAndLevel((byte) 1, 2, sort);
		return secondmenulist;
	}

	/**
	 * 新增角色信息以及权限
	 * 
	 * @param groupvo
	 * @return
	 */
	@Transactional
	public Result saveGroup(GroupVo groupvo) {
		String[] menuId = groupvo.getMenuAuthId();
		if (menuId == null || menuId.length == 0) {
			return new Result(0, "请选择权限", "", "");
		}
		// 时间戳
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String time = DateUtils.getTime(dateFormat.format(date), dateFormat);

		YskGroupEntity yskgroupentity = new YskGroupEntity();
		yskgroupentity.setTitle(groupvo.getTitle());
		yskgroupentity.setSort(groupvo.getSort());
		yskgroupentity.setIcon("");
		yskgroupentity.setMenuAuth(StringUtils.join(menuId, ","));
		yskgroupentity.setCreateTime(Integer.valueOf(time));
		YskGroupEntity groupEntity = groupRepository.saveAndFlush(yskgroupentity);
		if (null != groupEntity) {
			return new Result(1, "操作成功", "/Admin/Group/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Group/index", "");
		}
	}

	/**
	 * 更新角色信息以及权限
	 * 
	 * @param groupvo
	 * @return
	 */
	@Transactional
	public Result updateGroup(GroupVo groupvo) {
		String[] menuId = groupvo.getMenuAuthId();
		if (menuId == null || menuId.length == 0) {
			return new Result(0, "请选择权限", "", "");
		}
		YskGroupEntity yskgroupentity = new YskGroupEntity();
		yskgroupentity = groupRepository.findById(groupvo.getId());
		yskgroupentity.setTitle(groupvo.getTitle());
		yskgroupentity.setSort(groupvo.getSort());
		yskgroupentity.setMenuAuth(StringUtils.join(menuId, ","));

		// 时间戳
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
		yskgroupentity.setUpdateTime(Integer.valueOf(time));
		YskGroupEntity groupEntity = groupRepository.saveAndFlush(yskgroupentity);
		if (null != groupEntity) {
			return new Result(1, "操作成功", "/Admin/Group/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Group/index", "");
		}
	}

	/**
	 * 点击启用修改状态
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	@Transactional
	public Result updateGroupStatusEnable(byte status, Integer id) {
		YskGroupEntity yskgroupentity = new YskGroupEntity();
		yskgroupentity = groupRepository.findById(id).get();
		yskgroupentity.setStatus(status);
		// 时间戳
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
		yskgroupentity.setUpdateTime(Integer.valueOf(time));
		YskGroupEntity groupEntity = groupRepository.saveAndFlush(yskgroupentity);
		if (null != groupEntity) {
			return new Result(1, "操作成功", "/Admin/Group/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Group/index", "");
		}
	}

	/**
	 * 点击禁用修改状态
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	@Transactional
	public Result updateGroupStatusDisable(byte status, Integer id) {
		YskGroupEntity yskgroupentity = new YskGroupEntity();
		yskgroupentity = groupRepository.findById(id).get();
		yskgroupentity.setStatus(status);
		// 时间戳
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
		yskgroupentity.setUpdateTime(Integer.valueOf(time));
		YskGroupEntity groupEntity = groupRepository.saveAndFlush(yskgroupentity);
		if (null != groupEntity) {
			return new Result(1, "操作成功", "/Admin/Group/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Group/index", "");
		}
	}

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public int deleteGroup(Integer id) {
		try {
			groupRepository.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	/**
	 * 点击启用批量修改状态
	 * 
	 * @param status
	 * @param ids
	 * @return
	 */
	@Transactional
	public Result updateGroupStatusEnableList(byte status, Integer[] ids) {

		YskGroupEntity groupEntity = null;

		if (ids == null || ids.length == 0) {
			return new Result(0, "请选择要操作的数据", "", "");
		}

		for (int i = 0; i < ids.length; i++) {
			YskGroupEntity yskgroupentity = new YskGroupEntity();
			yskgroupentity = groupRepository.findById(ids[i]).get();
			yskgroupentity.setStatus(status);
			// 时间戳
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
			yskgroupentity.setUpdateTime(Integer.valueOf(time));
			groupEntity = groupRepository.saveAndFlush(yskgroupentity);
		}
		if (null != groupEntity) {
			return new Result(1, "操作成功", "/Admin/Group/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Group/index", "");
		}
	}

	/**
	 * 点击禁用批量修改状态
	 * 
	 * @param status
	 * @param ids
	 * @return
	 */
	@Transactional
	public Result updateGroupStatusDisableList(byte status, Integer[] ids) {

		YskGroupEntity groupEntity = null;

		if (ids == null || ids.length == 0) {
			return new Result(0, "请选择要操作的数据", "", "");
		}

		for (int i = 0; i < ids.length; i++) {
			YskGroupEntity yskgroupentity = new YskGroupEntity();
			yskgroupentity = groupRepository.findById(ids[i]).get();
			yskgroupentity.setStatus(status);
			// 时间戳
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
			yskgroupentity.setUpdateTime(Integer.valueOf(time));
			groupEntity = groupRepository.saveAndFlush(yskgroupentity);
		}
		if (null != groupEntity) {
			return new Result(1, "操作成功", "/Admin/Group/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Group/index", "");
		}
	}

	/**
	 * 批量删除角色
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	public int deleteGroupList(Integer[] ids) {
		if (ids == null || ids.length == 0) {
			return 2;
		}
		try {
			for (int i = 0; i < ids.length; i++) {
				groupRepository.deleteById(ids[i]);
			}

		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

}
