package com.hctxsys.service;

import com.hctxsys.entity.UserEntity;
import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.ShopInfoRepository;
import com.hctxsys.repository.UserEntityRepository;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.MemberVo;
import com.hctxsys.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SellerServiceImpl {
    @Autowired
    private ShopInfoRepository shopInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserEntityRepository entityRepository;
    /**
     *店铺列表及动态查询
     * @param result 查询及分页信息
     * @return
     */
    public TableResult indexTable(TableResult result) {
        PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());//获取分页对象
        Page<YskShopInfoEntity> all = shopInfoRepository.findAll(new Specification<YskShopInfoEntity>() {
            @Override
            public Predicate toPredicate(Root<YskShopInfoEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();//条件集合
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("uid")));
                if (null != result.getBeginDate() && !("".equals(result.getBeginDate()))) {//开始时间查询
                    String date = DateUtils.getTime(result.getBeginDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.greaterThan(root.get("createTime"), Long.valueOf(date)));
                }
                if (null != result.getEndDate() && !("".equals(result.getEndDate()))) {//结束时间查询
                    String date = DateUtils.getTime(result.getEndDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.lessThan(root.get("createTime"), Long.valueOf(date) + 60 * 60 * 24));//将时间加到查询时间的后一天0点
                }
                if (null != result.getTypeText() && !("".equals(result.getTypeText()))) {//商店名模糊查询
                    predicates.add(criteriaBuilder.like(root.get("shopName"), "%"+result.getTypeText() + '%'));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, request);
        TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
        tableResult.setTotal(all.getTotalElements());//设置总记录数
        tableResult.setRows(all.getContent());//设置分页后的集合
        tableResult.setPageCount(Long.valueOf(all.getTotalPages()));//设置总页数
        return tableResult;
    }

    /**
     *店铺详情
     * @param uid 店铺ID
     * @return
     */
    public YskShopInfoEntity getDeatil(Integer uid) {
        YskShopInfoEntity shop = shopInfoRepository.findByUid(uid);
        return shop;
    }
    public TableResult sellerList(TableResult result) {
        PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());//获取分页对象
        Page<UserEntity> usersPage = entityRepository.findAll(new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();//条件集合
                query.orderBy(criteriaBuilder.desc(root.get("userid")));
                if (null != result.getBeginDate() && !("".equals(result.getBeginDate()))) {//开始时间查询
                    String date = DateUtils.getTime(result.getBeginDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.greaterThan(root.get("regDate"), Long.valueOf(date)));
                }
                if (null != result.getEndDate() && !("".equals(result.getEndDate()))) {//结束时间查询
                    String date = DateUtils.getTime(result.getEndDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.lessThan(root.get("regDate"), Long.valueOf(date) + 60 * 60 * 24));//将时间加到查询时间的后一天0点
                }
                if (null != result.getTypeText() && !("".equals(result.getTypeText()))) {//按类型条件查询
                    if ("mobile".equals(result.getType())) {//按手机模糊查
                        predicates.add(criteriaBuilder.like(root.get("mobile"), '%' +result.getTypeText() + '%'));
                    }
                    else if ("account".equals(result.getType())) {//按账号模糊查
                        predicates.add(criteriaBuilder.like(root.get("account"), result.getTypeText() + '%'));
                    }
                    else {
                        predicates.add(criteriaBuilder.like(root.get("username"),  result.getTypeText() + '%'));
                    }
                }
                predicates.add(criteriaBuilder.equal(root.get("seller"),Byte.valueOf("1")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, request);
        List<UserEntity> all = entityRepository.selectAll();
        List<MemberVo> VoList = new ArrayList<>();//前台展示类
        Map<Integer, UserEntity> userMap = new HashMap<>();//根据主键存放User实体类
        for (UserEntity user : all) {//List转成Map
            userMap.put(user.getUserid(), user);
        }
        for (UserEntity user : usersPage) {
        	Integer isSelfShop = shopInfoRepository.findById(user.getUserid()).get().getIsSelfShop();
        	if(null!=isSelfShop) {
        		user.setIsSelfShop(isSelfShop);
        	}
            MemberVo userVo = new MemberVo();
            BeanUtils.copyProperties(user, userVo);//将Entity数据存入Vo中
            if (user.getPid() > 0) {//根据Pid判断是否有父节点，有则设置父节点账号名称
                UserEntity yskUserEntity = userMap.get(user.getPid());
                userVo.setParentName(yskUserEntity.getAccount());
            }
            VoList.add(userVo);
        }
        TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
        tableResult.setTotal(usersPage.getTotalElements());//设置总记录数
        tableResult.setRows(VoList);//设置分页后的集合
        tableResult.setPageCount(Long.valueOf(usersPage.getTotalPages()));//设置总页数
        return tableResult;
    }

    //设置为自营
    @Transient
    public Result setsupport(Integer uid) {
    	Result result = new Result();
    	//找到以前的自营店设置成非自营
    	YskShopInfoEntity shop = shopInfoRepository.findByisSelfShop(1);
    	if(null!=shop) {
    		shop.setIsSelfShop(0);
    		shopInfoRepository.saveAndFlush(shop);
    	}
    	
    	//设置自营
    	YskShopInfoEntity support = shopInfoRepository.findById(uid).get();
    	support.setIsSelfShop(1);
    	shopInfoRepository.saveAndFlush(support);
    	
    	result.setStatus(200);
    	result.setMessage("设置成功");
    	return result;
    }
    
}
