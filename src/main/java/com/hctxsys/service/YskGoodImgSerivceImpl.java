package com.hctxsys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskGoodImgEntity;
import com.hctxsys.repository.YskGoodImgRepository;


@Service("yskGoodImgSerivce")
public class YskGoodImgSerivceImpl {
	
	@Autowired
	private YskGoodImgRepository yskGoodImgRepository;
	
	/**
	 * 通过id查图片
	 * @param id
	 * @return
	 */
	public YskGoodImgEntity getImgById(int id) {
		YskGoodImgEntity img = yskGoodImgRepository.findById(id);
		return img;
	}
	
	/**
	 * 通过商品id查图片list
	 * @param goodId
	 * @return
	 */
	public List<YskGoodImgEntity> getImgByGoodId(int goodId) {
		List<YskGoodImgEntity> imglist = yskGoodImgRepository.findByGoodId(goodId);
		return imglist;
		
	}
	
	/**
	 * 插入图片
	 * @param goodImgEntity
	 * @return
	 */
	@Transactional
	public YskGoodImgEntity insertImg(YskGoodImgEntity goodImgEntity) {
		YskGoodImgEntity goodImg = null;
		try {
			goodImg = yskGoodImgRepository.save(goodImgEntity);
		} catch (Exception e) {
			
		}
		return goodImg;
	}
	
	/**
	 * 更新图片
	 * @param goodImgEntity
	 * @return
	 */
	@Transactional
	public YskGoodImgEntity updateImg(YskGoodImgEntity goodImgEntity) {
		YskGoodImgEntity goodImg = null;
		try {
			goodImg = yskGoodImgRepository.saveAndFlush(goodImgEntity);
		} catch (Exception e) {
			
		}
		return goodImg;
	}
	
	/**
	 * 删除图片
	 * @param goodImgEntity
	 * @return
	 */
	@Transactional
	public int deleteImg(int id) {
		try {
			yskGoodImgRepository.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除商品所有图片
	 * @param goodId
	 * @return
	 */
	@Transactional
	public int deleteImgMany(int goodId) {
		try {
			List<YskGoodImgEntity> list = yskGoodImgRepository.findByGoodId(goodId);
			for (YskGoodImgEntity yskGoodImgEntity : list) {
				yskGoodImgRepository.deleteById(yskGoodImgEntity.getId());
			}
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
}
