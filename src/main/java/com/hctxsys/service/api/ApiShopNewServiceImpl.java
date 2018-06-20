package com.hctxsys.service.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskShopnewEntity;
import com.hctxsys.repository.ShopNewRepository;

@Service("apiShopNew")
public class ApiShopNewServiceImpl {
	@Autowired
	private ShopNewRepository shopNewRepository;
	
	public List<YskShopnewEntity> getShopNew(){
		Page<YskShopnewEntity> findAll = shopNewRepository.findAll(PageRequest.of(0, 1));
		return findAll.getContent();
	}
}
