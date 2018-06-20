package com.hctxsys.service.api;

import com.hctxsys.entity.YskCarEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.entity.YskTravelEntity;
import com.hctxsys.repository.ModuleImgRepository;
import com.hctxsys.repository.TravelRepository;
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

@Service("apiTravelService")
public class ApiTravelServiceImpl {
    @Autowired
    private TravelRepository travelRepository;
    @Autowired
    private ModuleImgRepository imgRepository;
    @Transactional
    public JsonResult getList(PageVo pageVo) {
        PageRequest pageRequest = PageRequest.of(pageVo.getPage(), pageVo.getPageSize());
        Page<YskTravelEntity> all = travelRepository.findAll(new Specification<YskTravelEntity>() {
            @Override
            public Predicate toPredicate(Root<YskTravelEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicates = new ArrayList<>();
                if(!"".equals(pageVo.getCity())&&null!=pageVo.getCity()) {
                    predicates.add(criteriaBuilder.equal(root.get("typeId"),Integer.valueOf(pageVo.getCity())));//类型ID
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageRequest);
        List<YskTravelEntity> content = all.getContent();
        if(content.size()==0) return new JsonResult(0,"暂无数据");
        List<HashMap<String,Object>> list=new ArrayList<>();
        for (YskTravelEntity travel : content) {
//            List<YskModuleImgEntity> imgs = imgRepository.findAllByModuleIdAndModuleType(car.getModuleId(), 1);
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",travel.getModuleId());//id
            map.put("title",travel.getTitle());//标题
            map.put("content",travel.getContent());//简介内容
            map.put("contact",travel.getContact());//联系人
            map.put("phone",travel.getPhone());//联系人电话
            map.put("area",travel.getProvince()+"-"+travel.getCity()+"-"+travel.getDistrict());//区域地址
            map.put("type",travel.getYsktraveltypeentity().getTypeName());//旅游类型
            map.put("address",travel.getAddress());//旅游地址
            map.put("coverImg",travel.getCoverImg());
//            map.put("imgs",imgs);
            list.add(map);
        }

        return new JsonResult(1,"查询成功",list);
    }
    @Transactional
    public JsonResult getDetail(Integer id) {
        try {
            YskTravelEntity travel = travelRepository.findById(id).get();
            List<YskModuleImgEntity> imgs = imgRepository.findAllByModuleIdAndModuleType(travel.getModuleId(), 3);
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",travel.getModuleId());//id
            map.put("title",travel.getTitle());//标题
            map.put("content",travel.getContent());//简介内容
            map.put("contact",travel.getContact());//联系人
            map.put("phone",travel.getPhone());//联系人电话
            map.put("detailContent",travel.getDetailContent());//详细内容
            map.put("area",travel.getProvince()+"-"+travel.getCity()+"-"+travel.getDistrict());//区域地址
            map.put("type",travel.getYsktraveltypeentity().getTypeName());
            map.put("address",travel.getAddress());//旅游地址
            map.put("imgs",imgs);
            return new JsonResult(1,"查询成功",map);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"查询失败,失败原因:"+e.getMessage());
        }
    }
}
