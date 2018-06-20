package com.hctxsys.service.api;

import com.hctxsys.entity.YskCarEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.repository.CarRepository;
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

@Service("apiCarService")
public class ApiCarServiceImpl {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ModuleImgRepository imgRepository;
    @Transactional
    public JsonResult getList(PageVo pageVo) {
        PageRequest pageRequest = PageRequest.of(pageVo.getPage(), pageVo.getPageSize());
        Page<YskCarEntity> all = carRepository.findAll(new Specification<YskCarEntity>() {
            @Override
            public Predicate toPredicate(Root<YskCarEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicates = new ArrayList<>();
                if(!"".equals(pageVo.getCity())&&null!=pageVo.getCity()) {
                    predicates.add(criteriaBuilder.equal(root.get("typeId"),Integer.valueOf(pageVo.getCity())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        },pageRequest);
        List<YskCarEntity> content = all.getContent();
        if(content.size()==0) return new JsonResult(0,"暂无数据");
        List<HashMap<String,Object>> list=new ArrayList<>();
        for (YskCarEntity car : content) {
//            List<YskModuleImgEntity> imgs = imgRepository.findAllByModuleIdAndModuleType(car.getModuleId(), 1);
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",car.getModuleId());//id
            map.put("title",car.getTitle());//标题
            map.put("content",car.getContent());//简介内容
            map.put("contact",car.getContact());//联系人
            map.put("phone",car.getPhone());//联系人电话
            if(car.getCarType()==0)
                map.put("carType","新车");//车类型
            else
                map.put("carType","二手车");
            map.put("address",car.getProvince()+"-"+car.getCity()+"-"+car.getDistrict());//区域地址
            map.put("coverImg",car.getCoverImg());
            map.put("brand",car.getCarBrand().getTypeName());
//            map.put("imgs",imgs);
            list.add(map);
        }

        return new JsonResult(1,"查询成功",list);
    }
    @Transactional
    public JsonResult getDetail(Integer id) {
        try {
            YskCarEntity car = carRepository.findById(id).get();
            List<YskModuleImgEntity> imgs = imgRepository.findAllByModuleIdAndModuleType(car.getModuleId(), 1);
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",car.getModuleId());//id
            map.put("title",car.getTitle());//标题
            map.put("content",car.getContent());//简介内容
            map.put("contact",car.getContact());//联系人
            map.put("phone",car.getPhone());//联系人电话
            map.put("detailContent",car.getDetailContent());//详细内容
            if(car.getCarType()==0)
                map.put("carType","新车");//车类型
            else
                map.put("carType","二手车");
            map.put("address",car.getProvince()+"-"+car.getCity()+"-"+car.getDistrict());//区域地址
            map.put("brand",car.getCarBrand().getTypeName());
            map.put("imgs",imgs);
            return new JsonResult(1,"查询成功",map);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"查询失败,失败原因:"+e.getMessage());
        }
    }
}
