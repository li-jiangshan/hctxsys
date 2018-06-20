package com.hctxsys.service.api;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskBankNameEntity;
import com.hctxsys.entity.YskUserBankEntity;
import com.hctxsys.repository.BankNameRepository;
import com.hctxsys.repository.UserBankRepository;
import com.hctxsys.vo.api.ApiAddBankVo;
import com.hctxsys.vo.api.ApiBankId;
import com.hctxsys.vo.api.JsonResult;

import io.netty.util.internal.StringUtil;

@Service("apiBankService")
public class ApiBankServiceImpl {
	@Autowired
	private BankNameRepository bankNameRepository;
	@Autowired
	private UserBankRepository userBankRepository;
	
	/**添加银行卡
	 * @param vo
	 * @return
	 */
	@Transactional
	public JsonResult addBank(ApiAddBankVo vo) {
		//String pattern ="^([1-9]{1})(\\d{14}|\\d{18})$";
		JsonResult result = new JsonResult();
		if(StringUtils.isEmpty(vo.getBankname())) {
			result.setCode(0);
			result.setMessage("请选择开户银行");
			return result;
		}
		if(StringUtils.isEmpty(vo.getUsername())) {
			result.setCode(0);
			result.setMessage("请填写开户名");
			return result;
		}
		if(StringUtils.isEmpty(vo.getBankno())) {
			result.setCode(0);
			result.setMessage("请填写银行卡号");
			return result;
		}
		/*if(!vo.getBankno().matches(pattern)) {
			result.setCode(0);
			result.setMessage("请填写银行卡号不符合");
			return result;
		}*/
		YskBankNameEntity bank = bankNameRepository.findByBankName(vo.getBankname());
		if(null==bank) {
			result.setCode(0);
			result.setMessage("开户银行不存在");
			result.setData("");
			return result;
		}

		YskUserBankEntity userBankEntity = userBankRepository.findByUidAndBankNo(vo.getUid(), vo.getBankno());
		if (userBankEntity != null) {
			result.setCode(0);
			result.setMessage("该银行卡已绑定");
			return result;
		}
		
		//如果选择了该银行为默认，将其它的银行卡修改为非默认
		if(vo.getIsdefault()==1) {
			List<YskUserBankEntity> findByUid = userBankRepository.findByUid(vo.getUid());
				for (YskUserBankEntity yskUserBankEntity : findByUid) {
					byte isDefault = yskUserBankEntity.getIsDefault();
					if(isDefault==1) {
						yskUserBankEntity.setIsDefault((byte)0);
						userBankRepository.saveAndFlush(yskUserBankEntity);
					}
				}
		}
		
		YskUserBankEntity yskUserBank = new YskUserBankEntity();
		yskUserBank.setUid(vo.getUid());
		yskUserBank.setBankName(bank.getBankName());
		yskUserBank.setBankImg(bank.getBankImg());
		yskUserBank.setBankNo(vo.getBankno());
		yskUserBank.setUserName(vo.getUsername());
		yskUserBank.setBankBranch(vo.getBankbranch());
		yskUserBank.setIsDefault(vo.getIsdefault());
		YskUserBankEntity saveAndFlush = userBankRepository.saveAndFlush(yskUserBank);
		System.out.println(saveAndFlush.getId());
		ApiBankId bankId = new ApiBankId();
		bankId.setBankId(saveAndFlush.getId());
		bankId.setBankNo(saveAndFlush.getBankNo());
		result.setCode(1);
		result.setMessage("保存成功");
		result.setData(bankId);
		return result;
	}
	
	/**查找用户添加的银行卡
	 * @param uid 用户id
	 * @return
	 */
	public JsonResult findBank(Integer uid) {
		JsonResult result = new JsonResult();
		if(uid<1||null==uid) {
			result.setCode(0);
			result.setMessage("用户id不能小于1");
			result.setData("");
		}
		List<YskUserBankEntity> list = userBankRepository.findByUid(uid);
		result.setCode(1);
		result.setMessage("查询银行卡成功");
		result.setData(list);
		return result;
	}
}
