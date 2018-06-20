package com.hctxsys.service.api;

import com.hctxsys.entity.YskAgricultureEntity;
import com.hctxsys.entity.YskHouseEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.repository.AgricultureRepository;
import com.hctxsys.repository.HouseRepository;
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

@Service("apiAgricultureService")
public class ApiAgricultureServiceImpl {
    @Autowired
    private AgricultureRepository agricultureRepository;
    @Autowired
    private ModuleImgRepository imgRepository;
    @Transactional
    public JsonResult getList(PageVo pageVo) {
        PageRequest pageRequest = PageRequest.of(pageVo.getPage(), pageVo.getPageSize());
        Page<YskAgricultureEntity> all = agricultureRepository.findAll(new Specification<YskAgricultureEntity>() {
            @Override
            public Predicate toPredicate(Root<YskAgricultureEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicates = new ArrayList<>();
                if(!"".equals(pageVo.getCity())&&null!=pageVo.getCity()) {
                    predicates.add(criteriaBuilder.equal(root.get("agricultureType"), Integer.valueOf(pageVo.getCity())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageRequest);
        List<YskAgricultureEntity> content = all.getContent();
        if(content.size()==0) return new JsonResult(0,"暂无数据");
        List<HashMap<String,Object>> list=new ArrayList<>();
        for (YskAgricultureEntity agricultureEntity : content) {
//            List<YskModuleImgEntity> imgs = imgRepository.findAllByModuleIdAndModuleType(car.getModuleId(), 1);
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",agricultureEntity.getAgricultureId());//id
            map.put("title",agricultureEntity.getAgricultureTitle());//标题
            map.put("agriculatureType",agricultureEntity.getType().getAgricultureName());//房屋类型
            map.put("coverImg",agricultureEntity.getAgricultureCoverImg());//封面图片
            map.put("agriculatureDescription",agricultureEntity.getAgricultureDescription());//具体描述
            map.put("address",agricultureEntity.getAgricultureProvince()+"-"+agricultureEntity.getAgricultureCity()+"-"+agricultureEntity.getAgricultureDistrict());//区域地址
            map.put("contact",agricultureEntity.getContactsName());//联系人
            map.put("phone",agricultureEntity.getContactsPhone());//联系人电话
            map.put("name",agricultureEntity.getAgricultureName());//农产品名字
            map.put("coverImg",agricultureEntity.getAgricultureCoverImg());
//            map.put("imgs",imgs);
            list.add(map);
        }

        return new JsonResult(1,"查询成功",list);
    }
    @Transactional
    public JsonResult getDetail(Integer id) {
        try {
            YskAgricultureEntity agricultureEntity = agricultureRepository.findById(id).get();
            List<YskModuleImgEntity> imgs = imgRepository.findAllByModuleIdAndModuleType(agricultureEntity.getAgricultureId(), 2);
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",agricultureEntity.getAgricultureId());//id
            map.put("title",agricultureEntity.getAgricultureTitle());//标题
            map.put("agriculatureType",agricultureEntity.getType().getAgricultureName());//房屋类型
            map.put("coverImg",agricultureEntity.getAgricultureCoverImg());//封面图片
            map.put("agriculatureDescription",agricultureEntity.getAgricultureDescription());//具体描述
            map.put("address",agricultureEntity.getAgricultureProvince()+"-"+agricultureEntity.getAgricultureCity()+"-"+agricultureEntity.getAgricultureDistrict());//区域地址
            map.put("contact",agricultureEntity.getContactsName());//联系人
            map.put("phone",agricultureEntity.getContactsPhone());//联系人电话
            map.put("name",agricultureEntity.getAgricultureName());//农产品名字
            map.put("imgs",imgs);
            return new JsonResult(1,"查询成功",map);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"查询失败,失败原因:"+e.getMessage());
        }
    }
}
