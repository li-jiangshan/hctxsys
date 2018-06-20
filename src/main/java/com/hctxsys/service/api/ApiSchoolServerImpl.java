package com.hctxsys.service.api;

import com.hctxsys.entity.YskSchoolDetailsEntity;
import com.hctxsys.entity.YskSchoolPeopleEntity;
import com.hctxsys.repository.SchoolDetailRepository;
import com.hctxsys.repository.SchoolPeopleRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.vo.api.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("apiSchoolService")
public class ApiSchoolServerImpl {
    @Autowired
    private SchoolPeopleRepository peopleRepository;
    @Autowired
    private SchoolDetailRepository detailRepository;
    @Transactional
    public JsonResult showList() {
        List<YskSchoolPeopleEntity> all = peopleRepository.findAll();
        if(all.size()==0) {
            return new JsonResult(0,"暂无数据");
        }
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (YskSchoolPeopleEntity yskSchoolPeopleEntity : all) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",yskSchoolPeopleEntity.getId());//ID
            map.put("name",yskSchoolPeopleEntity.getName());//课程名
            map.put("time",yskSchoolPeopleEntity.getAge());//课程时间
            map.put("content",yskSchoolPeopleEntity.getContent());//内容介绍html
            map.put("address",yskSchoolPeopleEntity.getAddres());//地址
            map.put("image",yskSchoolPeopleEntity.getImage());//图片
            list.add(map);
        }
        return new JsonResult(1,"查询成功",list);
    }
    @Transactional
    public JsonResult signUp(YskSchoolDetailsEntity schoolDetails) {
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String user_time = sdf.format(date);
            schoolDetails.setCreateTime(Integer.parseInt(DateUtils.getTime(user_time,sdf)));
            detailRepository.saveAndFlush(schoolDetails);
            return new JsonResult(1,"报名成功");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"报名失败");
        }
    }
    @Transactional
    public void schoolDetail(Integer pid, Model model) {
    	YskSchoolPeopleEntity schoolPeopleEntity = peopleRepository.findById(pid).get();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(Long.valueOf(schoolPeopleEntity.getAddtime()+"000"));
    	model.addAttribute("name", schoolPeopleEntity.getName());
    	model.addAttribute("age",sdf.format(date));
    	model.addAttribute("addres",schoolPeopleEntity.getAddres());
    	model.addAttribute("content",schoolPeopleEntity.getContent());
    	model.addAttribute("image", schoolPeopleEntity.getImage());
    }
}
