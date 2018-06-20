package com.hctxsys.service.api;

import com.hctxsys.entity.YskCarEntity;
import com.hctxsys.entity.YskHouseEntity;
import com.hctxsys.entity.YskModuleImgEntity;
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

@Service("apiHouseService")
public class ApiHouseServiceImpl {
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private ModuleImgRepository imgRepository;
    @Transactional
    public JsonResult getList(PageVo pageVo) {
        PageRequest pageRequest = PageRequest.of(pageVo.getPage(), pageVo.getPageSize());
        Page<YskHouseEntity> all = houseRepository.findAll(new Specification<YskHouseEntity>() {
            @Override
            public Predicate toPredicate(Root<YskHouseEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicates = new ArrayList<>();
                if(!"".equals(pageVo.getCity())&&null!=pageVo.getCity()) {
                    predicates.add(criteriaBuilder.equal(root.get("houseType"), Integer.valueOf(pageVo.getCity())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageRequest);
        List<YskHouseEntity> content = all.getContent();
        if(content.size()==0) return new JsonResult(0,"暂无数据");
        List<HashMap<String,Object>> list=new ArrayList<>();
        for (YskHouseEntity house : content) {
//            List<YskModuleImgEntity> imgs = imgRepository.findAllByModuleIdAndModuleType(car.getModuleId(), 1);
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",house.getHouseId());//id
            map.put("title",house.getHouseTitle());//标题
            map.put("houseType",house.getYskHouseType().getTypeName());//房屋类型
            map.put("houseSize",house.getHouseSize());//房屋大小
            map.put("houseDescription",house.getHouseDescription());//房屋描述
            map.put("address",house.getHouseProvince()+"-"+house.getHouseCity()+"-"+house.getHouseDistrict()+"-"+house.getHouseAddress());//区域地址
            map.put("contact",house.getContactsName());//联系人
            map.put("phone",house.getContactsPhone());//联系人电话
            map.put("coverImg",house.getHouseCoverImg());//封面图片
//            map.put("imgs",imgs);
            list.add(map);
        }

        return new JsonResult(1,"查询成功",list);
    }
    @Transactional
    public JsonResult getDetail(Integer id) {
        try {
            YskHouseEntity house = houseRepository.findById(id).get();
            List<YskModuleImgEntity> imgs = imgRepository.findAllByModuleIdAndModuleType(house.getHouseId(), 0);
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",house.getHouseId());//id
            map.put("title",house.getHouseTitle());//标题
            map.put("houseType",house.getYskHouseType().getTypeName());//房屋类型
            map.put("houseSize",house.getHouseSize());//房屋大小
            map.put("houseDescription",house.getHouseDescription());//房屋描述
            map.put("address",house.getHouseProvince()+"-"+house.getHouseCity()+"-"+house.getHouseDistrict()+"-"+house.getHouseAddress());//区域地址
            map.put("contact",house.getContactsName());//联系人
            map.put("phone",house.getContactsPhone());//联系人电话
            map.put("imgs",imgs);
            return new JsonResult(1,"查询成功",map);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"查询失败,失败原因:"+e.getMessage());
        }
    }
}
