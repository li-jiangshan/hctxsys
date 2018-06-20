package com.hctxsys.service.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.repository.GoodCommentRepository;
import com.hctxsys.repository.ShopInfoRepository;

@Service("apiAroundService")
public class ApiAroundServiceImpl {

	@Autowired
	private ShopInfoRepository shopInfoRepository;
	
	@Autowired
	private GoodCommentRepository goodCommentRepository;
	
	public YskShopInfoEntity findAround(int uid) {
		return shopInfoRepository.findByUid(uid);
	}
	
	public List<YskGoodCommentEntity> getGoodComment(int uid) {
		return goodCommentRepository.findGoodComment(uid);
	}
}
