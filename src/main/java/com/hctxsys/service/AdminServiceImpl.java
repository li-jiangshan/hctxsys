package com.hctxsys.service;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskAdminEntity;
import com.hctxsys.entity.YskGroupEntity;
import com.hctxsys.repository.AdminRepository;
import com.hctxsys.repository.GroupRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.AdminVo;

@Service("AdminService")
public class AdminServiceImpl {
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private GroupRepository groupRepository;

	private final static String AUTH_KEY = "kkVg{EyPWCy:iJ*A-ZW&B+N%WlM^xHEqZGrVG<{,}J)gk``.;u|qD~d!(?\"zj;@C";

	/**
	 * 管理员管理首页管理员查询
	 * 
	 * @param result
	 * @return
	 */
	public TableResult selectAdmin(TableResult result) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskAdminEntity> groupPage = adminRepository.findAll(new Specification<YskAdminEntity>() {
			private static final long serialVersionUID = 7543731165521326868L;

			@Override
			public Predicate toPredicate(Root<YskAdminEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (result.getKeyword() != null) {
					predicates.add(cb.like(root.get("id").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(cb.like(root.get("username").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(cb.like(root.get("nickname").as(String.class), '%' + result.getKeyword() + '%'));
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
	 * 查询角色下拉框
	 * 
	 * @return
	 */
	public List<YskGroupEntity> selectGroup() {
		List<YskGroupEntity> list = groupRepository.findAll();
		return list;
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public YskAdminEntity findById(Integer id) {
		YskAdminEntity yskadminentity = adminRepository.findById(id).get();
		return yskadminentity;
	}

	/**
	 * 新增管理员信息
	 * 
	 * @param groupvo
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@Transactional
	public Result saveAdmin(AdminVo adminvo) {
		List<YskAdminEntity> list = adminRepository.findAll();
		for (YskAdminEntity yskadminentity : list) {
			if (yskadminentity.getUsername().equals(adminvo.getUsername())) {
				return new Result(0, "用户已存在，请重新输入", "", "");
			}
		}
		// 时间戳
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String time = DateUtils.getTime(dateFormat.format(date), dateFormat);

		YskAdminEntity yskadminentity = new YskAdminEntity();
		yskadminentity.setAuthId(adminvo.getAuthId());
		yskadminentity.setNickname(adminvo.getNickname());
		yskadminentity.setUsername(adminvo.getUsername());
		yskadminentity.setPassword(user_md5(adminvo.getPassword()));

		yskadminentity.setMobile(adminvo.getMobile());
		yskadminentity.setCreateTime(Integer.valueOf(time));
		YskAdminEntity adminEntity = adminRepository.saveAndFlush(yskadminentity);
		if (null != adminEntity) {
			return new Result(1, "操作成功", "/Admin/Manage/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Manage/index", "");
		}
	}

	/**
	 * 编辑管理员信息
	 * 
	 * @param groupvo
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@Transactional
	public Result updateAdmin(AdminVo adminvo) {
		List<YskAdminEntity> list = adminRepository.findAll();

		for (YskAdminEntity yskadminentity : list) {
			if (yskadminentity.getId() == adminvo.getId()) {
				list.remove(yskadminentity);
				break;
			}
		}

		for (YskAdminEntity adminEntity : list) {
			if (adminEntity.getUsername().equals(adminvo.getUsername())) {
				return new Result(0, "用户已存在，请重新输入", "", "");
			}
		}
		// 时间戳
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String time = DateUtils.getTime(dateFormat.format(date), dateFormat);

		YskAdminEntity yskadminentity = adminRepository.getOne(adminvo.getId());
		yskadminentity.setAuthId(adminvo.getAuthId());
		yskadminentity.setNickname(adminvo.getNickname());
		yskadminentity.setUsername(adminvo.getUsername());
		if (adminvo.getPassword() != null && !("").equals(adminvo.getPassword())) {
			yskadminentity.setPassword(user_md5(adminvo.getPassword()));
		} else {
			yskadminentity.setPassword(yskadminentity.getPassword());
		}

		yskadminentity.setMobile(adminvo.getMobile());
		yskadminentity.setUpdateTime(Integer.valueOf(time));
		YskAdminEntity adminEntity = adminRepository.saveAndFlush(yskadminentity);
		if (null != adminEntity) {
			return new Result(1, "操作成功", "/Admin/Manage/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Manage/index", "");
		}
	}

	/**
	 * 用户登录密码加密
	 * 
	 * @param str
	 *            要加密的字符串
	 * @return 密文
	 */
	private String user_md5(String str) {
		return (str == "") ? "" : DigestUtils.md5Hex((DigestUtils.sha1Hex(str) + AUTH_KEY));
	}

	/**
	 * 点击启用修改状态
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	@Transactional
	public Result updateAdminStatusEnable(byte status, Integer id) {
		YskAdminEntity yskadminentity = new YskAdminEntity();
		yskadminentity = adminRepository.findById(id).get();
		yskadminentity.setStatus(status);
		// 时间戳
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
		yskadminentity.setUpdateTime(Integer.valueOf(time));
		YskAdminEntity adminEntity = adminRepository.saveAndFlush(yskadminentity);
		if (null != adminEntity) {
			return new Result(1, "操作成功", "/Admin/Manage/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Manage/index", "");
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
	public Result updateAdminStatusDisable(byte status, Integer id) {
		YskAdminEntity yskadminentity = new YskAdminEntity();
		yskadminentity = adminRepository.findById(id).get();
		yskadminentity.setStatus(status);
		// 时间戳
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
		yskadminentity.setUpdateTime(Integer.valueOf(time));
		YskAdminEntity adminEntity = adminRepository.saveAndFlush(yskadminentity);
		if (null != adminEntity) {
			return new Result(1, "操作成功", "/Admin/Manage/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Manage/index", "");
		}
	}

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public int deleteAdmin(Integer id) {
		try {
			adminRepository.deleteById(id);
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
	public Result updateAdminStatusEnableList(byte status, Integer[] ids) {

		YskAdminEntity adminEntity = null;

		if (ids == null || ids.length == 0) {
			return new Result(0, "请选择要操作的数据", "", "");
		}

		for (int i = 0; i < ids.length; i++) {
			YskAdminEntity yskadminentity = new YskAdminEntity();
			yskadminentity = adminRepository.findById(ids[i]).get();
			yskadminentity.setStatus(status);
			// 时间戳
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
			yskadminentity.setUpdateTime(Integer.valueOf(time));
			adminEntity = adminRepository.saveAndFlush(yskadminentity);
		}
		if (null != adminEntity) {
			return new Result(1, "操作成功", "/Admin/Manage/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Manage/index", "");
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
	public Result updateAdminStatusDisableList(byte status, Integer[] ids) {

		YskAdminEntity adminEntity = null;

		if (ids == null || ids.length == 0) {
			return new Result(0, "请选择要操作的数据", "", "");
		}

		for (int i = 0; i < ids.length; i++) {
			YskAdminEntity yskadminentity = new YskAdminEntity();
			yskadminentity = adminRepository.findById(ids[i]).get();
			yskadminentity.setStatus(status);
			// 时间戳
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
			yskadminentity.setUpdateTime(Integer.valueOf(time));
			adminEntity = adminRepository.saveAndFlush(yskadminentity);
		}
		if (null != adminEntity) {
			return new Result(1, "操作成功", "/Admin/Manage/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Manage/index", "");
		}
	}

	/**
	 * 批量删除角色
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	public int deleteAdminList(Integer[] ids) {
		if (ids == null || ids.length == 0) {
			return 2;
		}
		try {
			for (int i = 0; i < ids.length; i++) {
				adminRepository.deleteById(ids[i]);
			}

		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

}
