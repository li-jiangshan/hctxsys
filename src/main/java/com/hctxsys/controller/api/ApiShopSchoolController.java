package com.hctxsys.controller.api;

import com.hctxsys.entity.YskSchoolDetailsEntity;
import com.hctxsys.service.api.ApiSchoolServerImpl;
import com.hctxsys.util.CheckUtils;
import com.hctxsys.vo.api.JsonResult;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("api/school")
public class ApiShopSchoolController {
    @Autowired
    private ApiSchoolServerImpl schoolServer;

    /**
     * 商学院列表
     * @return
     */
    @PostMapping("showList")
    @ResponseBody
    public JsonResult showList() {
        JsonResult jsonResult = schoolServer.showList();
        return jsonResult;
    }

    /**
     * 商学院报名
     * @return
     */
    @PostMapping("signUp")
    @ResponseBody
    public JsonResult signUp(@RequestBody YskSchoolDetailsEntity detailsEntity) {
        if(StringUtils.isNullOrEmpty(detailsEntity.getName())) {
            return new JsonResult(0,"姓名不能为空");
        }if(StringUtils.isNullOrEmpty(detailsEntity.getMobile())) {
            return new JsonResult(0,"手机号不能为空");
        }if(!CheckUtils.isCheckMobile(detailsEntity.getMobile())) {
            return new JsonResult(0,"手机号格式错误");
        }
        return schoolServer.signUp(detailsEntity);

    }
    
    /**
     * 商学院详细
     * @return
     */
    @RequestMapping(value = "/detail/pid/{pid}/uid/{uid}", method = RequestMethod.GET)
	public String schoolDetail(@PathVariable Integer pid, @PathVariable Integer uid, Model model) {
    	model.addAttribute("pid",pid);
    	model.addAttribute("uid",uid);
    	schoolServer.schoolDetail(pid, model);
    	return "/home/news/shaoschooldetail";
    }
    
}
