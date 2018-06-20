package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskAdminEntity;
import com.hctxsys.entity.YskGroupEntity;
import com.hctxsys.entity.YskMenuEntity;
import com.hctxsys.repository.AdminRepository;
import com.hctxsys.repository.GroupRepository;
import com.hctxsys.repository.MenuRepository;

@Service("MenuService")
public class MenuServiceImpl {
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private GroupRepository groupRepository;

	/**
	 * 获取用户可操作控制器
	 * 
	 * @param id
	 * @param request
	 * @param session
	 * @return
	 */
	public List<YskMenuEntity> getCol(HttpServletRequest request, HttpSession session) {

		Integer uid = (Integer) request.getSession().getAttribute("uid");

//		Integer sellerId = (Integer) request.getSession().getAttribute("sellerId");

		List<YskMenuEntity> list = new ArrayList<YskMenuEntity>();

		if (uid == null) {
		} else {
			YskAdminEntity yskadminentity = adminRepository.findById(uid).get();

			YskGroupEntity yskgroupentity = groupRepository.findById(yskadminentity.getAuthId());

			String menuAuth = yskgroupentity.getMenuAuth();

			String[] menuId = menuAuth.split(",");

//			if (sellerId == null) {
				for (int i = 0; i < menuId.length; i++) {
					YskMenuEntity yskmenuentity = menuRepository.findById(Integer.valueOf(menuId[i])).get();
					list.add(yskmenuentity);
				}
//			} else {
//				YskMenuEntity yskmenuentity = menuRepository.findById(10);
//				list.add(yskmenuentity);
//			}
		}
		return list;
	}

	/**
	 * 查询顶部菜单
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	public List<YskMenuEntity> getTopMenu(HttpServletRequest request, HttpSession session) {

		Integer uid = (Integer) request.getSession().getAttribute("uid");

		List<YskMenuEntity> toplist = new ArrayList<YskMenuEntity>();

//		Integer sellerId = (Integer) request.getSession().getAttribute("sellerId");

		if (uid == null || uid != 1) {
			List<YskMenuEntity> quanxinalist = this.getCol(request, session);
//			if (sellerId == null) {
//			} else {
//				Sort sort = new Sort(Sort.Direction.ASC, "sort");
//				YskMenuEntity yskmenuentity = menuRepository.findByIdAndStatusAndLevel(10, (byte) 1, 0, sort);
//				toplist.add(yskmenuentity);
//			}
			for (YskMenuEntity quanxian : quanxinalist) {
				Sort sort = new Sort(Sort.Direction.ASC, "sort");
				YskMenuEntity yskmenuentity = menuRepository.findByIdAndStatusAndLevel(quanxian.getId(), (byte) 1, 0,
						sort);
				if (yskmenuentity != null) {
					toplist.add(yskmenuentity);
				}
			}
		} else {
			toplist = menuRepository.findByStatusAndLevel((byte) 1, 0);
		}

		return toplist;
	}

	/**
	 * 查询第一级菜单
	 *
	 * @param request
	 * @param session
	 * @return
	 */
	public List<YskMenuEntity> getFirstMenu(HttpServletRequest request, HttpSession session) {

		Integer uid = (Integer) request.getSession().getAttribute("uid");

//		Integer sellerId = (Integer) request.getSession().getAttribute("sellerId");

		List<YskMenuEntity> firstmenulist = new ArrayList<YskMenuEntity>();
		Integer flag = (Integer) request.getSession().getAttribute("flag");
		if (uid == null || uid != 1) {
			List<YskMenuEntity> quanxinalist = this.getCol(request, session);
//			if (sellerId == null) {
//			} else {
//				if (flag == null || flag == 1 || flag == 0) {
//					Sort sort = new Sort(Sort.Direction.ASC, "sort");
//					YskMenuEntity yskmenuentity = menuRepository.findByIdAndStatusAndLevel(1, (byte) 1, 1, sort);
//					firstmenulist.add(yskmenuentity);
//				} else {
//					Sort sort = new Sort(Sort.Direction.ASC, "sort");
//					YskMenuEntity yskmenuentity = menuRepository.findByIdAndStatusAndLevel(10, (byte) 1, 1, sort);
//					firstmenulist.add(yskmenuentity);
//					firstmenulist = menuRepository.findByStatusAndLevelAndGid((byte) 1, 1, 10, sort);
//				}
//			}
			for (YskMenuEntity quanxian : quanxinalist) {
				if (flag == null || flag == 1 || flag == 0) {
					Sort sort = new Sort(Sort.Direction.ASC, "sort");
					YskMenuEntity yskmenuentity = menuRepository.findByIdAndStatusAndLevelAndPid(quanxian.getId(),
							(byte) 1, 1,1, sort);
					if (yskmenuentity != null) {
						firstmenulist.add(yskmenuentity);
					}
				} else {
					Sort sort = new Sort(Sort.Direction.ASC, "sort");
					YskMenuEntity yskmenuentity = menuRepository.findByIdAndStatusAndLevelAndPid(quanxian.getId(),
							(byte) 1, 1,10, sort);
					if (yskmenuentity != null) {
						firstmenulist.add(yskmenuentity);
					}
				}
			}
		} else {
			if (flag == null || flag == 1 || flag == 0) {
				Sort sort = new Sort(Sort.Direction.ASC, "sort");
				firstmenulist = menuRepository.findByStatusAndLevelAndPid((byte) 1, 1, 1, sort);
			} else {
				Sort sort = new Sort(Sort.Direction.ASC, "sort");
				firstmenulist = menuRepository.findByStatusAndLevelAndPid((byte) 1, 1, 10, sort);
			}
			// firstmenulist = menuRepository.findByStatusAndLevel((byte) 1, 1);
		}
		return firstmenulist;
	}

	/**
	 * 查询第二级菜单
	 *
	 * @param request
	 * @param session
	 * @return
	 */
	public List<YskMenuEntity> getSecondMenu(HttpServletRequest request, HttpSession session) {

		Integer uid = (Integer) request.getSession().getAttribute("uid");

//		Integer sellerId = (Integer) request.getSession().getAttribute("sellerId");

		List<YskMenuEntity> secondmenulist = new ArrayList<YskMenuEntity>();

		if (uid == null || uid != 1) {
			List<YskMenuEntity> quanxinalist = this.getCol(request, session);
//			if (sellerId == null) {
//			} else {
//				Sort sort = new Sort(Sort.Direction.ASC, "sort");
////				YskMenuEntity yskmenuentity = menuRepository.findByIdAndStatusAndLevel(10, (byte) 1, 2, sort);
//				secondmenulist = menuRepository.findByStatusAndLevelAndGid((byte) 1, 2, 10, sort);
//			}
			for (YskMenuEntity quanxian : quanxinalist) {
				Sort sort = new Sort(Sort.Direction.ASC, "sort");
				YskMenuEntity yskmenuentity = menuRepository.findByIdAndStatusAndLevel(quanxian.getId(), (byte) 1, 2,
						sort);
				if (yskmenuentity != null) {
					secondmenulist.add(yskmenuentity);
				}
			}
		} else {
			secondmenulist = menuRepository.findByStatusAndLevel((byte) 1, 2);
		}
		return secondmenulist;
	}

	public List<YskMenuEntity> getUrl(HttpServletRequest request, HttpSession session) {

		Integer uid = (Integer) request.getSession().getAttribute("uid");

		List<YskMenuEntity> urlList = new ArrayList<YskMenuEntity>();

		if (uid == null) {
			List<YskMenuEntity> secondMenuList = this.getSecondMenu(request, session);
			List<YskMenuEntity> oneList = new ArrayList<YskMenuEntity>();
			for (YskMenuEntity yskmenuentity : secondMenuList) {
				if (yskmenuentity.getGid() == 10) {
					oneList.add(yskmenuentity);
				}
			}
			urlList.add(oneList.get(0));
		} else {
			List<YskMenuEntity> secondMenuList = this.getSecondMenu(request, session);
			List<YskMenuEntity> oneList = new ArrayList<YskMenuEntity>();
			List<YskMenuEntity> twoList = new ArrayList<YskMenuEntity>();
			for (YskMenuEntity yskmenuentity : secondMenuList) {
				if (yskmenuentity.getGid() == 1) {
					oneList.add(yskmenuentity);
				} else {
					twoList.add(yskmenuentity);
				}
			}
			if(oneList.size() != 0) {
				urlList.add(oneList.get(0));
			}
			if(twoList.size() != 0) {
				urlList.add(twoList.get(0));
			}
			
		}
		return urlList;
	}

}
