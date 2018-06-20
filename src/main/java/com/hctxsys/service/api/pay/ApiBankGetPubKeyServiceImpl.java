package com.hctxsys.service.api.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hctxsys.entity.YskYwtPubKeyEntity;
import com.hctxsys.repository.YsKYwtPubKeyRepository;
import com.hctxsys.util.payUtil.BankPayUtils;

@Service("apiBankGetPubKeyService")
public class ApiBankGetPubKeyServiceImpl {

	@Autowired
	YsKYwtPubKeyRepository ywtPubKeyRepository;
	
	public String getPubKey() {
		YskYwtPubKeyEntity ywtPubKeyEntity = ywtPubKeyRepository.findById(1).get();
		return ywtPubKeyEntity.getPubKey();
	}
}
