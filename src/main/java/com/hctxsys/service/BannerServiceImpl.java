package com.hctxsys.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskBannerEntity;
import com.hctxsys.repository.BannerRepository;
import com.hctxsys.util.TableResult;

@Service("bannerSerivce")
public class BannerServiceImpl {
	
	@Autowired
	private BannerRepository bannerRepository;
	
	/**
	 * 广告列表
	 * @param result
	 * @return
	 */
	public TableResult findlist(TableResult result) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskBannerEntity> bannerlist = null;
		if(result.getType()!=null&&result.getType()!="") {
			if(result.getTypeText()==null) {
				result.setTypeText("");
			}
			bannerlist = bannerRepository.findAllkeyT(result.getType(), result.getTypeText(), request);
		}else {
			if(result.getTypeText()==null) {
				result.setTypeText("");
			}
			bannerlist = bannerRepository.findAllkey(result.getTypeText(), request);
		}
		TableResult tableResult = new TableResult();
		BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
		tableResult.setTotal(bannerlist.getTotalElements());//设置总记录数
		tableResult.setRows(bannerlist.getContent());
		tableResult.setPageCount(Long.valueOf(bannerlist.getTotalPages()));//设置总页数
		return tableResult;
	}
	
	/**
	 * 隐藏，显示
	 * @param id
	 * @return
	 */
	public YskBannerEntity showhide(int id) {
		YskBannerEntity entity = bannerRepository.findById(id);
		int status = entity.getStatus();
		if (status==0) {
			entity.setStatus(1);
		}else {
			entity.setStatus(0);
		}
		bannerRepository.saveAndFlush(entity);
		return entity;
	}
	
	/**
	 * 删除广告
	 * @return
	 */
	@Transactional
	public int deleteById(int id) {
		try {
			bannerRepository.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * 单查广告
	 * @return
	 */
	public YskBannerEntity findById(int id) {
		YskBannerEntity entity = bannerRepository.findById(id);
		return entity;
	} 
	
	/**
	 * 新增、更新
	 * @param model
	 * @return
	 */
	@Transactional
	public YskBannerEntity updateAds(YskBannerEntity ads) {
		YskBannerEntity entity = bannerRepository.saveAndFlush(ads);
		return entity;
	}
}
