package com.hctxsys.service.api;

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

import com.hctxsys.entity.YskBankNameEntity;
import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskMoneyRechargeEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.entity.YskUserWealthEntity;
import com.hctxsys.repository.BankNameRepository;
import com.hctxsys.repository.ConfigRepository;
import com.hctxsys.repository.MoneyRechargeRepository;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.repository.UserWealthRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.util.OrderUtils;
import com.hctxsys.vo.api.ApiRechargeVo;
import com.hctxsys.vo.api.JsonResult;

@Service("apiRechargeService")
public class ApiRechargeServiceImpl {

	@Autowired
	private BankNameRepository bankNameRepository;
	@Autowired
	private MoneyRechargeRepository moneyRechargeRepository;
	@Autowired
	private ConfigRepository configRepository;
	@Autowired
	private UserWealthRepository userWealthRepository;
	@Autowired
	private UserRepository userRepository;
	
    //查询银行列表
	@Transactional
	public JsonResult getBankInfo() {
		JsonResult returnVo = new JsonResult();
		List<YskBankNameEntity> bankList = bankNameRepository.findAll();  //查询全部
		returnVo.setData(bankList);
		return returnVo;
	}
	
    //查询充值记录列表
	@Transactional
	public JsonResult getRechargeList(ApiRechargeVo vo) {
		JsonResult returnVo = new JsonResult();
		PageRequest pageable = PageRequest.of(vo.getPage(), vo.getPageSize());
		Page<YskMoneyRechargeEntity> rechargeList = moneyRechargeRepository.findByUidOrderByCreateTimeDesc(vo.getUid(), pageable);  //通过用户id查询
		returnVo.setData(rechargeList.getContent());
		return returnVo;
	}
	
    //查询公司信息
	@Transactional
	public JsonResult getCompanyInfo() {
		JsonResult returnVo = new JsonResult();
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(39); //账户名称id
		ids.add(40); //开户行id
		ids.add(41); //账号id
		List<YskConfigEntity> configList = configRepository.findByIdIn(ids);  //通过用户id查询
		ApiRechargeVo data = new ApiRechargeVo();
		data.setCompanyAccountName(configList.get(0).getValue());  //账户名称
		data.setCompanyAccountBank(configList.get(1).getValue()); //开户行
		data.setCompanyAccountNo(configList.get(2).getValue()); //账号
		returnVo.setData(data);
		return returnVo;
	}
	
    //充值
	@Transactional
	public JsonResult rechargeOrder(ApiRechargeVo vo) {
		
		JsonResult returnVo = new JsonResult();
		
		//查询用户信息
		Optional<YskUserEntity> userEntity = userRepository.findById(vo.getUid());
		
		if (!userEntity.isPresent()) {
  			returnVo.setCode(0);
  			returnVo.setMessage("用户不存在");
  	        return returnVo;
		}
		
		//查询用户财富信息
		YskUserWealthEntity userWealthEntity = userWealthRepository.getOne(vo.getUid());

		YskMoneyRechargeEntity moneyRechargeEntity = new YskMoneyRechargeEntity();
        BeanUtils.copyProperties(vo, moneyRechargeEntity);//将Vo数据存入Entity中
        moneyRechargeEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
        moneyRechargeEntity.setStatus((byte) 0); //'0-未支付 1-已支付 2-不通过',
        moneyRechargeEntity.setMoneyRecord(userWealthEntity.getMoney());  //充值记录 (充值前余额)
        moneyRechargeEntity.setContent("充值" + vo.getMoney()); //说明
        moneyRechargeEntity.setFromType((byte) 1); //1-转入 2-转出
        //生成订单号
    	String orderNo = this.getOrderNo();
    	moneyRechargeEntity.setOrderNo(orderNo);
        if ("1".equals(vo.getRechargeType())) {
        	moneyRechargeEntity.setType("online"); //操作类型
        	moneyRechargeEntity.setTypeName("在线充值"); //操作名称
        }
        if ("2".equals(vo.getRechargeType())) {
        	moneyRechargeEntity.setType("underline"); //操作类型
        	moneyRechargeEntity.setTypeName("线下充值"); //操作名称
        }
        moneyRechargeEntity.setUsername(userEntity.get().getUsername()); //用户名
        moneyRechargeEntity.setAccount(userEntity.get().getAccount());  //用户账号
        moneyRechargeEntity.setMobile(userEntity.get().getMobile());  //用户手机
        
        moneyRechargeRepository.save(moneyRechargeEntity);

    	//返回订单号
    	ApiRechargeVo rechargeVo = new ApiRechargeVo();
    	rechargeVo.setOrderNo(orderNo);
    	rechargeVo.setMoney(vo.getMoney());
    	returnVo.setData(rechargeVo);
        
		return returnVo;
	}
	
	//获取订单id
	private String getOrderNo() {
		String orderNo = OrderUtils.getOrderNo("CZ");
		YskMoneyRechargeEntity moneyRecharge = moneyRechargeRepository.findByOrderNo(orderNo);
		if (moneyRecharge != null) {
			this.getOrderNo();
		}
		return orderNo;
	}

}
