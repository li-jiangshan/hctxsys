package com.hctxsys.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskShopnewEntity;
import com.hctxsys.repository.ShopNewRepository;
import com.hctxsys.util.TableResult;

@Service("shopNew")
public class ShopNewServiceImpl {
	@Autowired
	private ShopNewRepository shopNewRepository;
	
	/**
	 * 公告列表   （全查）
	 * @param result
	 * @return
	 */
	public TableResult findAll(TableResult result) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskShopnewEntity> alllist = shopNewRepository.findAll(request);
		TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
        tableResult.setTotal(alllist.getTotalElements());//设置总记录数
        tableResult.setRows(alllist.getContent());
        tableResult.setPageCount(Long.valueOf(alllist.getTotalPages()));//设置总页数
        return tableResult;
	}
	
	/**
	 * 公告列表   （关键字）
	 * @param result
	 * @return
	 */
	public TableResult findBykeyword(TableResult result) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		String keyword = result.getTypeText();
		boolean kk = keyword.matches("[0-9]+");
		Page<YskShopnewEntity> alllist = null;
		if(kk==true) {
			alllist = shopNewRepository.findBykeyword(Integer.valueOf(keyword), keyword, request);
		}else {
			alllist = shopNewRepository.findBykeyword(0, keyword, request);
		}
		TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
        tableResult.setTotal(alllist.getTotalElements());//设置总记录数
        tableResult.setRows(alllist.getContent());
        tableResult.setPageCount(Long.valueOf(alllist.getTotalPages()));//设置总页数
        return tableResult;
	}
	
	/**
	 * 查公告信息
	 * @param id
	 * @return
	 */
	public YskShopnewEntity findById(int id) {
		YskShopnewEntity entity = shopNewRepository.findById(id);
		return entity;
	}
	
	/**
	 * 新增、更新
	 * @param model
	 * @return
	 */
	@Transactional
	public YskShopnewEntity updateNews(YskShopnewEntity news) {
		YskShopnewEntity entity = shopNewRepository.saveAndFlush(news);
		return entity;
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@Transactional
	public int deleteNews(int id) {
		try {
			shopNewRepository.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
}
