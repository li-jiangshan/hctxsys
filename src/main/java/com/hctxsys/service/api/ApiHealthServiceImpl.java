package com.hctxsys.service.api;

import com.hctxsys.entity.YskHealthyEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.entity.YskTravelEntity;
import com.hctxsys.repository.AgricultureRepository;
import com.hctxsys.repository.HealthyRepositry;
import com.hctxsys.repository.ModuleImgRepository;
import com.hctxsys.vo.api.JsonResult;
import com.hctxsys.vo.api.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("apiHealthService")
public class ApiHealthServiceImpl {
    @Autowired
    private HealthyRepositry healthyRepositry;
    @Autowired
    private ModuleImgRepository imgRepository;
    @Transactional
    public JsonResult getList(PageVo pageVo) {
        PageRequest pageRequest = PageRequest.of(pageVo.getPage(), pageVo.getPageSize());
        Page<YskHealthyEntity> all = healthyRepositry.findAll(new Specification<YskHealthyEntity>() {
            @Override
            public Predicate toPredicate(Root<YskHealthyEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicates = new ArrayList<>();
                if(!"".equals(pageVo.getCity())&&null!=pageVo.getCity()) {
                    predicates.add(criteriaBuilder.equal(root.get("healthyType"), Integer.valueOf(pageVo.getCity())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageRequest);
        List<YskHealthyEntity> content = all.getContent();
        if(content.size()==0) return new JsonResult(0,"暂无数据");
        List<HashMap<String,Object>> list=new ArrayList<>();
        for (YskHealthyEntity health : content) {
//            List<YskModuleImgEntity> imgs = imgRepository.findAllByModuleIdAndModuleType(car.getModuleId(), 1);
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",health.getId());//id
            map.put("title",health.getTitle());//标题
            map.put("content",health.getContent());//简介内容
            map.put("contact",health.getContacts());//联系人
            map.put("phone",health.getPhone());//联系人电话
            map.put("type",health.getType().getTypeName());//健康类型
            map.put("coverImg",health.getCoverimg());
            map.put("address",health.getProvince()+"-"+health.getCity()+"-"+health.getDistrict());//区域地址
//            map.put("imgs",imgs);
            list.add(map);
        }

        return new JsonResult(1,"查询成功",list);
    }
    @Transactional
    public JsonResult getDetail(Integer id) {
        try {
            YskHealthyEntity health = healthyRepositry.findById(id).get();
            List<YskModuleImgEntity> imgs = imgRepository.findAllByModuleIdAndModuleType(health.getId(), 4);
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",health.getId());//id
            map.put("title",health.getTitle());//标题
            map.put("content",health.getContent());//简介内容
            map.put("contact",health.getContacts());//联系人
            map.put("phone",health.getPhone());//联系人电话
            map.put("type",health.getType().getTypeName());//健康类型
            map.put("address",health.getProvince()+"-"+health.getCity()+"-"+health.getDistrict());//区域地址
            map.put("imgs",imgs);
            return new JsonResult(1,"查询成功",map);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"查询失败,失败原因:"+e.getMessage());
        }
    }
}
