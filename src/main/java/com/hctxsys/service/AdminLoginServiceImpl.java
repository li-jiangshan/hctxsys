package com.hctxsys.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskAdminEntity;
import com.hctxsys.entity.YskGroupEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.AdminRepository;
import com.hctxsys.repository.GroupRepository;
import com.hctxsys.repository.UserCheckInfoRepository;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.util.Result;

/**
 * Created by kipin on 2018-04-19
 */
@Service("adminLogin")
public class AdminLoginServiceImpl {
	/**
	 * php版里面规定的加密使用的key
	 */
	private final static String AUTH_KEY = "kkVg{EyPWCy:iJ*A-ZW&B+N%WlM^xHEqZGrVG<{,}J)gk``.;u|qD~d!(?\"zj;@C";
	
	/**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private GroupRepository groupRepository;
	/*
	 * 2018-04-26 add userRepository&userCheckInfoRepository
	 */
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserCheckInfoRepository userCheckInfoRepository;
	
	/**
	 * 登录的详细业务逻辑
	 * @param username 用户名
	 * @param password 密码
	 * @param identity 身份  2018-04-26 add 用于合并之前的小系统
	 * @param request session使用参数
	 * @return Result
	 */
	public Result checkLogin(String username, String password, HttpServletRequest request) {
		/*
		 * 2018-04-26 整理：处理管理员和处理商家登录业务逻辑
		 */
//		if (identity.equals("admin"))
			return processAdmin(username, password, request);
//		else //identity.equals("seller")
//			return processSeller(username, password, request);
	}
	
	/**
	 * 处理商家 2018-04-26
	 * @param username 用户名
	 * @param password 密码
	 * @param request session使用参数
	 * @return Return
	 */
//	private Result processSeller(String username, String password, HttpServletRequest request) {
//		if (username.trim().equals("")) return new Result(0, "账号不能为空", "", "");//0用于给view判断
//		if (password.equals("")) return new Result(0, "密码不能为空", "", "");
//		
//		YskUserEntity userEntity;//查找用户
//		//匹配登录方式
//		if (username.matches(REGEX_MOBILE))//手机号登录
//			userEntity = userRepository.findByMobileAndStatusGreaterThan(username, (byte)-1);
//		else//用户名登录
//			userEntity = userRepository.findByAccountAndStatusGreaterThan(username, (byte)-1);
//		
//		if (userEntity == null) 
//			return new Result(0, "用户不存在！", "" , "");
//		else if (userEntity.getStatus() == 0) 
//			return new Result(0, "您的账号已锁定，请联系管理员！", "" , "");
//		else if (!pwdMd5(password, userEntity.getLoginSalt()).equals(userEntity.getLoginPwd()))
//			return new Result(0, "商家密码错误！", "" , "");
//		else if (userEntity.getSeller() == (byte)0)
//			return new Result(0, "非商家用户！", "" , "");
//		else {
//			byte count;
//			//认证个人信息 1:企业用户  0:个人用户
//			if (userEntity.getUserType() == (byte)1)
//				//查表
//				count = userCheckInfoRepository.getCompanyCount((byte)2, userEntity.getUserid());//公司认证 1-审核中 2-审核通过
//			else
//				count = userCheckInfoRepository.getUserCount((byte)2, userEntity.getUserid());//个人认证 1-审核中 2-审核通过
//			if (count == (byte)0)
//				return new Result(0, "未认证个人信息，请先认证信息！", "" , "");
//			
//			/*
//			 * 信息匹配成功
//			 * 设session+页面跳转
//			 */
//			setSellerSession(request, userEntity);
//			
//			/************增加管理员登录 S  代码未复用，很low  kipin:add 2018-04-27******************/
////			if (username.trim().equals("")) return new Result(0, "用户名不能为空！", "", "");//0用于给view判断
////			if (password.equals("")) return new Result(0, "密码不能为空！", "", "");
////			YskAdminEntity adminEntity = adminRepository.findByUsernameAndStatus(username, (byte)1);//默认status为1
////			if (adminEntity == null)
////				return new Result(0, "用户不存在或被禁用！", "" , "");
////			else {//用户存在
////				if (!user_md5(password).equals(adminEntity.getPassword()))
////					return new Result(0, "admin密码错误！", "", "");
////				else {//密码正确，需要验证权限
////					YskGroupEntity groupEntity = groupRepository.findById(adminEntity.getAuthId());
////					if (groupEntity == null)
////						return new Result(0, "该用户没有管理员权限！", "", "");
////					else {
////						setAdminSession(request, adminEntity);
////					}
////				}
////			}
//			/************增加管理员登录 E******************/
//			
//			return new Result(1, "登录成功！", "Adminmall/Good/index", "");//跳转商城列表
//		}
//	}
	
	/**
	 * 保存“商家”登录session
	 * @param request
	 * @param userEntity
	 * @return sellerId & account & mobile & username & in_time 修改需谨慎，清除的session是根据该处设定的。
	 */
//	private void setSellerSession(HttpServletRequest request, YskUserEntity userEntity) {
//		HttpSession session = request.getSession();
//		session.setMaxInactiveInterval(30*60);//30min
//		session.setAttribute("sellerId", userEntity.getUserid());
//		session.setAttribute("account", userEntity.getAccount());
//		session.setAttribute("mobile", userEntity.getMobile());
//		session.setAttribute("username", userEntity.getUsername());
//		session.setAttribute("in_time", System.currentTimeMillis());//当前时间戳
//		session.setAttribute("flag", 2);//用于网站menu、header布局
//		userRepository.updateSession(session.getId(), userEntity.getUserid());//更新用户session
//	}
	
	/**
	 * pwdMd5 用户密码加密
	 * @param password 登录明文密码
	 * @param loginSalt user表的数据
	 * @return 密文
	 */
	private String pwdMd5(String password, String loginSalt) {
		return DigestUtils.md5Hex(user_md5(password).concat(loginSalt));
	}

	/**
	 * 处理管理员
	 * @param username 用户名
	 * @param password 密码
	 * @param request session使用参数
	 * @return Result
	 */
	private Result processAdmin(String username, String password, HttpServletRequest request) {
		if (username.trim().equals("")) return new Result(0, "用户名不能为空！", "", "");//0用于给view判断
		if (password.equals("")) return new Result(0, "密码不能为空！", "", "");
		
		/*
		 * 原php里此处有匹配验证方式（邮箱/手机/用户名），有bug，故省略。
		 */
		
		//根据admin表的username和status两字段，查找用户
		YskAdminEntity adminEntity = adminRepository.findByUsernameAndStatus(username, (byte)1);//默认status为1
		if (adminEntity == null)
			return new Result(0, "用户不存在或被禁用！", "" , "");
		else {//用户存在
			/*
			 * 1.如果密码不正确，提示“密码错误”；
			 * 2.否则密码正确。==>再根据当前用户auth_id值到group表里查询id字段是否匹配，检验权限
			 * 		2.1 如果查无数据，提示“该用户没有管理员权限”；
			 * 		2.2否则拥有权限。==>再设置登录状态，然后页面跳转
			 */
			if (!user_md5(password).equals(adminEntity.getPassword()))
				return new Result(0, "密码错误！", "", "");
			else {//密码正确，需要验证权限
				YskGroupEntity groupEntity = groupRepository.findById(adminEntity.getAuthId());
				if (groupEntity == null)
					return new Result(0, "该用户没有管理员权限！", "", "");
				else {
					
					/*
					 * 原php里此处有设置登录状态，研究半天发现毫无作用(┬＿┬)
					 * auto_login(adminEntity);//TODO 
					 */
					
					//设session+页面跳转
					setAdminSession(request, adminEntity);
					return new Result(1, "登录成功！", "admin/index/index", "");
				}
			}
		}
	}
	
	/**
	 * 保存“管理员”登录session
	 * @param request
	 * @param adminEntity
	 * @return uid & username & nickname & flag & islogin 修改需谨慎，清除的session是根据该处设定的。
	 */
	private void setAdminSession(HttpServletRequest request, YskAdminEntity adminEntity) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(30*60);//30min
		session.setAttribute("uid", adminEntity.getId());
		session.setAttribute("username",adminEntity.getUsername());
		session.setAttribute("nickname", adminEntity.getNickname());
		session.setAttribute("flag", 0);//用于网站menu、header布局
		session.setAttribute("islogin", 1);
		session.setAttribute("authID",adminEntity.getAuthId());
	}
	
	/**
	 * 用户登录密码加密
	 * @param str 要加密的字符串
	 * @return 密文 
	 */
	private String user_md5(String str) {
		return (str == "") ? "" : DigestUtils.md5Hex((DigestUtils.sha1Hex(str) + AUTH_KEY));
	}
}
