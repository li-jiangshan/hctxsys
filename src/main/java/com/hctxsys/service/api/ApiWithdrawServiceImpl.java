package com.hctxsys.service.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskMoneyDetailEntity;
import com.hctxsys.entity.YskMoneyGetEntity;
import com.hctxsys.entity.YskUserBankEntity;
import com.hctxsys.entity.YskUserCheckinfoEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.entity.YskUserWealthEntity;
import com.hctxsys.repository.ConfigRepository;
import com.hctxsys.repository.MoneyDetailRepository;
import com.hctxsys.repository.MoneyGetRepository;
import com.hctxsys.repository.UserBankRepository;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.repository.UserWealthRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.util.PswUtils;
import com.hctxsys.vo.api.ApiWithdrawVo;
import com.hctxsys.vo.api.JsonResult;

@Service("apiWithdrawService")
public class ApiWithdrawServiceImpl {

	@Autowired
	private MoneyGetRepository moneyGetRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ConfigRepository configRepository;
	@Autowired
	private UserBankRepository userBankRepository;
	@Autowired
	private UserWealthRepository userWealthRepository;
	@Autowired
	private MoneyDetailRepository moneyDetailRepository;
	
	
	
    //获取用户提现信息
	@Transactional
	public JsonResult getUserWithdrawInfo(ApiWithdrawVo vo) {
		JsonResult returnVo = new JsonResult();
		
		//查询用户信息
		Optional<YskUserEntity> opUserEntity = userRepository.findById(vo.getUid());
		if (!opUserEntity.isPresent()) {
  			returnVo.setCode(0);
  			returnVo.setMessage("用户不存在");
  	        return returnVo;
		}
		YskUserEntity userEntity = opUserEntity.get();
		//查询用户财富信息
		YskUserWealthEntity userWealthEntity = userEntity.getWealthEntity();
		
		vo.setBalance(userWealthEntity.getMoney()); //余额

		List<Integer> ids = new ArrayList<Integer>();
		ids.add(24); //单笔提现额度
		ids.add(25); //提现倍数
		List<YskConfigEntity> configList = configRepository.findByIdIn(ids);  //通过用户id查询
		vo.setMoneyMax(configList.get(0).getValue());  //单笔提现额度
		vo.setMoneyMultiple(configList.get(1).getValue()); //提现倍数
		
		YskUserBankEntity userBank = userBankRepository.findByUidAndIsDefault(vo.getUid(), (byte) 1);
		if (userBank != null) {
			vo.setUserBankId(userBank.getId()); //我的银行卡id
			vo.setCardNo(userBank.getBankNo()); //我的银行卡号
		}
		
		returnVo.setData(vo);
		
		return returnVo;
	}
	
    //查询提现记录列表
	@Transactional
	public JsonResult getWithdrawList(ApiWithdrawVo vo) {
		JsonResult returnVo = new JsonResult();
		PageRequest pageable = PageRequest.of(vo.getPage(), vo.getPageSize());
		Page<YskMoneyGetEntity> withdrawList = moneyGetRepository.findByUidOrderByCreateTimeDesc(vo.getUid(), pageable);  //通过用户id查询
		returnVo.setData(withdrawList.getContent());
		return returnVo;
	}
	
    //提现
	@Transactional
	public JsonResult withdrawApply(ApiWithdrawVo vo) {
		
		JsonResult returnVo = new JsonResult();
		//查询用户信息
		Optional<YskUserEntity> opUserEntity = userRepository.findById(vo.getUid());
		if (!opUserEntity.isPresent()) {
  			returnVo.setCode(0);
  			returnVo.setMessage("用户不存在");
  	        return returnVo;
		}
		
		Optional<YskUserBankEntity> opUserBank = userBankRepository.findById(vo.getUserBankId());
		if (!opUserBank.isPresent()) {
  			returnVo.setCode(0);
  			returnVo.setMessage("银行卡信息不存在");
  	        return returnVo;
		}
		
		YskUserBankEntity userBank = opUserBank.get();
				
		YskUserEntity userEntity = opUserEntity.get();
		//查询用户校验信息
		YskUserCheckinfoEntity userCheckinfoEntity = userEntity.getCheckinfoEntity();
		//查询用户财富信息
		YskUserWealthEntity userWealthEntity = userEntity.getWealthEntity();
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(24); //单笔提现额度
		ids.add(25); //提现倍数
		List<YskConfigEntity> configList = configRepository.findByIdIn(ids);  //通过用户id查询
		String moneyMax = configList.get(0).getValue();  //单笔提现额度
		String moneyMultiple = configList.get(1).getValue(); //提现倍数
		
  		String password = PswUtils.getCipher(vo.getSafetyPwd(),userEntity.getSafetySalt()); //加密密码
  		
  		if (!password.equals(userEntity.getSafetyPwd())) {
			returnVo.setCode(0);
			returnVo.setMessage("安全密码不正确");
		    return returnVo;
  		}
		if (new BigDecimal(moneyMax).compareTo(vo.getMoney()) == -1) {
			returnVo.setCode(0);
			returnVo.setMessage("单笔提现额度最高为" + moneyMax);
		    return returnVo;
		}
		
		BigDecimal multiple = vo.getMoney().divide(new BigDecimal(moneyMultiple));
		
		if (new BigDecimal(multiple.intValue()).compareTo(multiple) != 0){
			returnVo.setCode(0);
			returnVo.setMessage("提现额度需要为" + moneyMultiple + "的倍数");
		    return returnVo;
		}
  		
  		
		if (userEntity.getUserType() == 0) { //个人用户
			if (userCheckinfoEntity.getIsCheckUser() != 2 ) {
	  			returnVo.setCode(0);
	  			returnVo.setMessage("请先认证身份");
	  	        return returnVo;
			}
		}
		if (userEntity.getUserType() == 1) { //机构用户
			if (userCheckinfoEntity.getIsCheckCompany() != 2 ) {
	  			returnVo.setCode(0);
	  			returnVo.setMessage("请先认证身份");
	  	        return returnVo;
			}
		}
		//手续费
		BigDecimal fee = vo.getMoney().multiply(new BigDecimal(0.002));
		//账户余额不足
		if (userWealthEntity.getMoney().compareTo(vo.getMoney()) == -1) {
  			returnVo.setCode(0);
  			returnVo.setMessage("余额不足");
  	        return returnVo;
		}
		
		BigDecimal oldMoney = userWealthEntity.getMoney();
		
		userWealthEntity.setMoney(userWealthEntity.getMoney().subtract(vo.getMoney()));
		userWealthRepository.saveAndFlush(userWealthEntity);
		
    	/** 添加提现审核记录 */
		YskMoneyGetEntity moneyGetEntity = new YskMoneyGetEntity();
		BeanUtils.copyProperties(vo, moneyGetEntity);
		moneyGetEntity.setMoney(vo.getMoney());
		moneyGetEntity.setType((byte) 1);
		moneyGetEntity.setBankName(userBank.getBankName());
		moneyGetEntity.setUserName(userBank.getUserName());
		moneyGetEntity.setBankBranch(userBank.getBankBranch());
		moneyGetEntity.setTypeName("现金提现");
		moneyGetEntity.setContent("提现" + vo.getMoney());
		moneyGetEntity.setFee(fee);
		moneyGetEntity.setFeeTime(1);
		moneyGetEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
		moneyGetEntity.setStatus((byte) 0); //'0-审核中 1-在途 2-已到账',
		moneyGetEntity.setUsername(userEntity.getUsername());//用户名
		moneyGetEntity.setAccount(userEntity.getAccount()); //账号
		moneyGetRepository.save(moneyGetEntity);
		
		//提现记录
    	YskMoneyDetailEntity moneyDetailEntity = new YskMoneyDetailEntity();
    	moneyDetailEntity.setContent("提现" + vo.getMoney());
    	moneyDetailEntity.setFromType((byte) 2); //1-转入 2-转出
    	moneyDetailEntity.setType("getmoney"); //明细类型 
    	moneyDetailEntity.setTypeName("提现");
    	moneyDetailEntity.setUid(vo.getUid());
    	moneyDetailEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
    	moneyDetailEntity.setStatus((byte) 1); //0-未支付 1-已支付 2-不通过
    	moneyDetailEntity.setMoney(vo.getMoney()); //金额
    	moneyDetailEntity.setMoneyRecord(oldMoney.subtract(vo.getMoney()));
    	moneyDetailEntity.setOrderNo("");
    	moneyDetailRepository.save(moneyDetailEntity);

		return returnVo;
	}
	
}
