package com.hctxsys.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskAppEditionEntity;
import com.hctxsys.repository.AppEditionRepository;
import com.hctxsys.util.Result;
import com.hctxsys.vo.AppVo;

@Service("AppEditionService")
public class AppEditionServiceImpl {
	@Autowired
	private AppEditionRepository appEditionRepository;

	/**
	 * 查询app版本
	 * 
	 * @return
	 */
	public YskAppEditionEntity findApp() {
		List<YskAppEditionEntity> list = appEditionRepository.findAll();
		YskAppEditionEntity appEditionEntity = new YskAppEditionEntity();
		for (YskAppEditionEntity yskappeditionentity : list) {
			appEditionEntity.setId(yskappeditionentity.getId());
			appEditionEntity.setAppEdition(yskappeditionentity.getAppEdition());
			appEditionEntity.setDownloadingPath(yskappeditionentity.getDownloadingPath());
			appEditionEntity.setWhetherUpdate(yskappeditionentity.getWhetherUpdate());
		}
		return appEditionEntity;
	}

	/**
	 * 新增App版本信息
	 * 
	 * @param appvo
	 * @return
	 */
	@Transactional
	public Result saveApp(AppVo appvo) {

		YskAppEditionEntity yskappeditionentity = new YskAppEditionEntity();
		yskappeditionentity.setAppEdition(appvo.getAppEdition());
		yskappeditionentity.setDownloadingPath(appvo.getDownloadingPath());
		yskappeditionentity.setWhetherUpdate(appvo.getWhetherUpdate());

		// 获取当前日期
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 将String转为Timestamp类型
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = Timestamp.valueOf(df.format(day));
		// 为更新日期(时分秒)赋值
		yskappeditionentity.setUptimeing(ts);

		YskAppEditionEntity appEditionEntity = appEditionRepository.saveAndFlush(yskappeditionentity);
		if (null != appEditionEntity) {
			return new Result(1, "操作成功", "/Admin/App/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/App/index", "");
		}
	}

	/**
	 * 编辑App版本信息
	 * 
	 * @param appvo
	 * @return
	 */
	@Transactional
	public Result updateApp(AppVo appvo) {
		YskAppEditionEntity yskappeditionentity = appEditionRepository.getOne(appvo.getId());
		yskappeditionentity.setAppEdition(appvo.getAppEdition());
		yskappeditionentity.setDownloadingPath(appvo.getDownloadingPath());
		yskappeditionentity.setWhetherUpdate(appvo.getWhetherUpdate());
		// 获取当前日期
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 将String转为Timestamp类型
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = Timestamp.valueOf(df.format(day));
		// 为更新日期(时分秒)赋值
		yskappeditionentity.setUptimeing(ts);
		YskAppEditionEntity appEditionEntity = appEditionRepository.saveAndFlush(yskappeditionentity);
		if (null != appEditionEntity) {
			return new Result(1, "操作成功", "/Admin/App/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/App/index", "");
		}
	}

}
