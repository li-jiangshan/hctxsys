package com.hctxsys.service;

import com.hctxsys.entity.YskCarEntity;
import com.hctxsys.entity.YskCarTypeEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.repository.CarRepository;
import com.hctxsys.repository.CarTypeRepository;
import com.hctxsys.repository.ModuleImgRepository;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.CarVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarTypeRepository typeRepository;
    @Autowired
    private ModuleImgRepository imgRepository;
    public TableResult index(TableResult tableResult) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "moduleId"));
        PageRequest pageRequest = PageRequest.of(tableResult.getPageNumber(), tableResult.getPageSize(),sort);
        Page<YskCarEntity> all = carRepository.findAll(new Specification<YskCarEntity>() {
            @Override
            public Predicate toPredicate(Root<YskCarEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicates = new ArrayList<>();
                if (null != tableResult.getLevel()) {//等级查询
                    predicates.add(criteriaBuilder.equal(root.get("carBrand").get("typeId"), Byte.valueOf(String.valueOf(tableResult.getLevel()))));
                }
                if (null != tableResult.getTypeText() && !("".equals(tableResult.getTypeText()))) {
                    predicates.add(criteriaBuilder.like(root.get("contact"), '%' + tableResult.getTypeText() + '%'));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageRequest);
        TableResult result = new TableResult();
        BeanUtils.copyProperties(tableResult, result);
        result.setTotal(all.getTotalElements());
        result.setPageCount((long) all.getTotalPages());
        result.setRows(all.getContent());
        return result;
    }

    public List<YskCarTypeEntity> getAllType() {
        return typeRepository.findAll();
    }
    @Transactional
    public Result carInsert(CarVo carVo) {
        YskCarEntity car = new YskCarEntity();
        BeanUtils.copyProperties(carVo,car);
        try {
            YskCarEntity newCar = carRepository.saveAndFlush(car);
            for (String s : carVo.getImgList()) {
                YskModuleImgEntity moduleImg = new YskModuleImgEntity();
                moduleImg.setImgUrl(s);
                moduleImg.setModuleId(newCar.getModuleId());
                moduleImg.setModuleType(1);
                imgRepository.saveAndFlush(moduleImg);
            }
            return new Result(200,"保存成功，即将跳转页面","/Adminmall/Module/carIndex");
        }
        catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(500,"服务器异常，保存失败");
        }
    }
    public YskCarEntity findById(Integer id) {
        return carRepository.findById(id).get();
    }
    @Transactional
    public Result carUpdate(CarVo carVo) {
        try {
            YskCarEntity car = new YskCarEntity();
            BeanUtils.copyProperties(carVo,car);
            YskCarEntity newCar = carRepository.saveAndFlush(car);
            for (String s : carVo.getImgList()) {
                YskModuleImgEntity moduleImg = new YskModuleImgEntity();
                moduleImg.setImgUrl(s);
                moduleImg.setModuleId(newCar.getModuleId());
                moduleImg.setModuleType(1);
                imgRepository.saveAndFlush(moduleImg);
            }
            return new Result(200,"修改成功即将跳转","/Adminmall/Module/carIndex");
        }
        catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(500,"服务器异常");
        }
    }

    public Result deleteById(Integer id) {
        try {
            carRepository.deleteById(id);
            return new Result(200,"删除成功","/adminmall/module/carIndex");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(500,"删除失败，服务器异常");
        }
    }

    public List<YskModuleImgEntity> getImg(Integer moduleId) {
        List<YskModuleImgEntity> imglist = imgRepository.findAllByModuleIdAndModuleType(moduleId, 1);
        return imglist;
    }
    @Transactional
    public int deleteImg(Integer id) {
        try {
            imgRepository.deleteById(id);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }
}
