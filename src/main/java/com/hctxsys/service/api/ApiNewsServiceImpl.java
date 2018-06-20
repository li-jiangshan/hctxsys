package com.hctxsys.service.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskNewsEntity;
import com.hctxsys.repository.NewsRepository;
import com.hctxsys.vo.api.ApiNewsVo;
import com.hctxsys.vo.api.JsonResult;

@Service("apiNewsService")
public class ApiNewsServiceImpl {
	@Autowired
	private NewsRepository newsRepository;
	
	public JsonResult getNews(String type,Integer page,Integer pageSize) {
		JsonResult result = new JsonResult();
		PageRequest pageable=PageRequest.of(page, pageSize, new Sort(Direction.DESC,"addtime"));
		List<YskNewsEntity> list = newsRepository.findByTypeAndStatus(type, 1, pageable);
		if(list.size()==0) {
			result.setCode(1);
			result.setMessage("没有数据");
			result.setData(new ArrayList<ApiNewsVo>());
			return result;
		}
		List<ApiNewsVo> apiNewsVos = new ArrayList<ApiNewsVo>();
		for (YskNewsEntity yskNewsEntity : list) {
			ApiNewsVo apiNewsVo = new ApiNewsVo();
			apiNewsVo.setNewsId(yskNewsEntity.getNewsId());
			apiNewsVo.setNewtop(yskNewsEntity.getNewtop());
			apiNewsVo.setAddtime(yskNewsEntity.getAddtime());
			apiNewsVo.setTitle(yskNewsEntity.getTitle());
			apiNewsVos.add(apiNewsVo);
		}
		result.setCode(1);
		result.setMessage("查找成功");
		result.setData(apiNewsVos);
		return result;
	}
}
