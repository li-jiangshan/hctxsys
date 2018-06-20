package com.hctxsys.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.service.api.ApiDetailedServiceImpl;
import com.hctxsys.vo.api.ApiDetailVo;
import com.hctxsys.vo.api.JsonResult;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("/api/detail")
public class ApiDetailController {
	@Autowired
	private ApiDetailedServiceImpl apiDetailedServiceImpl;
	
	/**根据类型查找明细
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="/type",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult getByType(@RequestBody ApiDetailVo vo) {
		
		JsonResult returnVo = new JsonResult();

        if (vo.getUid() == null) {
            returnVo.setCode(0);
            returnVo.setMessage("用户ID不能为空");
            return returnVo;
        }
        if (vo.getType() == null) {
            returnVo.setCode(0);
            returnVo.setMessage("查询类型不能为空");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(vo.getFormType())) {
            returnVo.setCode(0);
            returnVo.setMessage("查询类型不能为空");
            return returnVo;
        }
		return apiDetailedServiceImpl.findByType(vo.getUid(), vo.getType(), vo.getFormType());
	}
}
