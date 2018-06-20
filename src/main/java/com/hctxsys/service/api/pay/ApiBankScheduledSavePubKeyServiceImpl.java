package com.hctxsys.service.api.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hctxsys.entity.YskYwtPubKeyEntity;
import com.hctxsys.repository.YsKYwtPubKeyRepository;
import com.hctxsys.util.payUtil.BankPayUtils;

@Service("apiBankScheduledSavePubKeyService")
public class ApiBankScheduledSavePubKeyServiceImpl {

	@Autowired
	YsKYwtPubKeyRepository ywtPubKeyRepository;
	
	@Transactional
	public void SavePubKey() {
		try {
			String pubKey = BankPayUtils.GetFbPubKey();
			if(pubKey != null) {
				YskYwtPubKeyEntity ywtPubKeyEntity = ywtPubKeyRepository.findById(1).get();
				ywtPubKeyEntity.setPubKey(pubKey);
				ywtPubKeyRepository.saveAndFlush(ywtPubKeyEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
	}
}
