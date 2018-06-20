package com.hctxsys.service;

import com.hctxsys.entity.*;
import com.hctxsys.repository.*;
import com.hctxsys.util.MyUtils;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.IntegralAuditVo;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.BeanUtils;
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("BuyKucunIntegralApplyService")
public class BuyKucunIntegralApplyServiceImpl {
	@Autowired
	private UserWealthRepository userWealthRepository;
	@Autowired
	private BuyKucunIntegralApplyRepository buyKucunIntegralApplyRepository;
	@Autowired
	private YsKUserRepository userRepository;
	@Autowired
	private KucunIntegralDetailRepository KucunIntegralDetailRepository;
	@Autowired
	private IntegralDetailRepository integralDetailRepository;
	@Autowired
	private SystemCostRepository systemCostRepository;
	@Autowired
	private MoneyDetailRepository moneyDetailRepository;

	/**
	 * 搜索栏功能
	 */
	@Transactional
	// 按条件查找提现数据
	public TableResult findList(TableResult result,byte status) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		List<IntegralAuditVo> list = new ArrayList<IntegralAuditVo>();
        List<Integer> idList = new ArrayList<>();
        if (!StringUtils.isNullOrEmpty(result.getTypeText())) {
            List<YskUserEntity> allByAccountLike = userRepository.findAllByUsernameLike("%" + result.getTypeText() + "%");
            for (YskUserEntity userEntity : allByAccountLike) {
                idList.add(userEntity.getUserid());
            }
        }
		Page<YskBuyKucunIntegralApplyEntity> page = buyKucunIntegralApplyRepository.findAll(new Specification<YskBuyKucunIntegralApplyEntity>() {

			@Override
			public Predicate toPredicate(Root<YskBuyKucunIntegralApplyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.equal(root.get("status"), status));
				if (null != result.getBeginDate()) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(result.getBeginDate()))));
				}
				if (null != result.getEndDate()) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(result.getEndDate())) + 24 * 60 * 60));
				}
                if (null != result.getTypeText() && idList.size() != 0) {
                    CriteriaBuilder.In<Integer> uid = cb.in(root.get("uid"));
                    for (Integer integer : idList) {
                        uid.value(integer);
                    }
                    predicates.add(uid);
//					predicates.add(cb.equal(root.get("uid"), Integer.valueOf(result.getTypeText())));
                }

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, request);
		
		
		for (YskBuyKucunIntegralApplyEntity entity : page) {
			IntegralAuditVo vo = new IntegralAuditVo();
			vo.setId(entity.getId());
			vo.setUid(entity.getUid());
			String username = userRepository.findById(entity.getUid()).get().getUsername();
			vo.setUserName(username);
			vo.setContent("购买"+entity.getKucunIntegral()+"个库存积分");
			vo.setCreateTime(entity.getCreateTime());
			vo.setMoney(entity.getMoney());
			vo.setStatus(entity.getStatus());
			vo.setImg(entity.getVoucherImg());
			list.add(vo);
		}
		TableResult tableResult = new TableResult();
	    BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
	    tableResult.setTotal(page.getTotalElements());//设置总记录数
	    tableResult.setRows(list);
	    //tableResult.setRows(goodlist.getContent());
	    tableResult.setPageCount(Long.valueOf(page.getTotalPages()));//设置总页数
	    return tableResult;
	}

	/**
	 * 通过
	 * @param id
	 */
	@Transactional
	public YskBuyKucunIntegralApplyEntity pass(int id) {
		
		YskBuyKucunIntegralApplyEntity applyEntity = buyKucunIntegralApplyRepository.findById(id).get();
		applyEntity.setStatus((byte)1);
		YskBuyKucunIntegralApplyEntity entity = buyKucunIntegralApplyRepository.saveAndFlush(applyEntity);
		
		//取余额
    	BigDecimal oldMoney = userWealthRepository.findByUid(applyEntity.getUid()).getMoney();
    	//减后余额
    	BigDecimal newMoney = oldMoney.subtract(applyEntity.getMoney());
    	
		/**添加金额明细信息 */
    	YskMoneyDetailEntity moneyDetailEntity = new YskMoneyDetailEntity();
    	moneyDetailEntity.setContent("购买" + applyEntity.getKucunIntegral() + "个库存积分");
    	moneyDetailEntity.setFromType((byte) 2); //1-转入 2-转出
    	moneyDetailEntity.setType("buykucunintegral"); //明细类型 
    	moneyDetailEntity.setTypeName("购买库存积分");
    	moneyDetailEntity.setUid(applyEntity.getUid());
    	moneyDetailEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
    	moneyDetailEntity.setStatus((byte) 1); //0-未支付 1-已支付 2-不通过
    	moneyDetailEntity.setMoney(applyEntity.getMoney()); //金额
    	moneyDetailEntity.setMoneyRecord(newMoney);
    	moneyDetailEntity.setOrderNo("");
    	moneyDetailRepository.save(moneyDetailEntity);
		
		/**添加库存积分明细*/
		YskKucunIntegralDetailEntity kucunIntegralDetailEntity  = new YskKucunIntegralDetailEntity();
    	kucunIntegralDetailEntity.setContent("购买" + applyEntity.getKucunIntegral() + "个库存积分");
    	kucunIntegralDetailEntity.setFromType((byte) 1); //1-转入 2-转出
    	kucunIntegralDetailEntity.setType("buykucunintegral"); //明细类型 
    	kucunIntegralDetailEntity.setTypeName("购买库存积分");
    	kucunIntegralDetailEntity.setUid(applyEntity.getUid());
    	kucunIntegralDetailEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
    	kucunIntegralDetailEntity.setStatus((byte) 1); //0-未支付 1-已支付 2-不通过
    	kucunIntegralDetailEntity.setMoney(applyEntity.getKucunIntegral()); //金额
    	//原来的库存积分
    	List<YskKucunIntegralDetailEntity> kucunlist = KucunIntegralDetailRepository.findByUid(applyEntity.getUid());
    	BigDecimal oldKucunIntegral = BigDecimal.valueOf(0);
    	if(kucunlist!=null&&(!kucunlist.isEmpty())) {
    		oldKucunIntegral = kucunlist.get(0).getMoneyRecord();
    	}
    	kucunIntegralDetailEntity.setMoneyRecord(oldKucunIntegral.add(applyEntity.getKucunIntegral()));
    	KucunIntegralDetailRepository.save(kucunIntegralDetailEntity);
    	
    	/**添加正常积分明细*/
    	if(applyEntity.getHasIntegral()==1) {
    		YskIntegralDetailEntity integralDetailEntity = new YskIntegralDetailEntity();
        	integralDetailEntity.setContent("出售商品");
        	integralDetailEntity.setFromType((byte) 1);
        	integralDetailEntity.setType("sellgood");
        	integralDetailEntity.setTypeName("出售商品");
        	integralDetailEntity.setUid(applyEntity.getUid());
        	integralDetailEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
        	integralDetailEntity.setStatus((byte) 1);
        	integralDetailEntity.setMoney(applyEntity.getIntegral());
        	//原来的正常积分
        	List<YskIntegralDetailEntity> list = integralDetailRepository.findByUid(applyEntity.getUid());
        	BigDecimal oldIntegral = BigDecimal.valueOf(0);
        	if(list!=null&&(!list.isEmpty())) {
        		oldIntegral = list.get(0).getMoneyRecord();
        	}
        	integralDetailEntity.setMoneyRecord(oldIntegral.add(applyEntity.getIntegral()));
        	integralDetailRepository.save(integralDetailEntity);
    	}
		
    	/**保存用户财富信息*/
    	YskUserWealthEntity userWealthEntity = userWealthRepository.findByUid(applyEntity.getUid());
    	userWealthEntity.setMoney(newMoney);//金额
    	if(applyEntity.getHasIntegral()==1) {
    		userWealthEntity.setIntegral(userWealthEntity.getIntegral().add(applyEntity.getIntegral()));//积分
    	}
    	userWealthEntity.setKucunIntegral(userWealthEntity.getKucunIntegral().add(applyEntity.getKucunIntegral()));//库存积分
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	String day = sdf.format(date);
    	userWealthEntity.setUptime(day);
    	SimpleDateFormat sdfall = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String second = sdfall.format(date);  
        userWealthEntity.setUptimeing(Timestamp.valueOf(second)); 
        userWealthRepository.saveAndFlush(userWealthEntity);
        
    	/**添加平台运营 */
        YskSystemCostEntity systemCostEntity = new YskSystemCostEntity();
        systemCostEntity.setOperatingAmount(applyEntity.getOperatingAmount()); //平台运营费
    	systemCostEntity.setReturnAmount(applyEntity.getReturnAmount()); //返现额度
    	systemCostEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
    	systemCostRepository.save(systemCostEntity);
    	
    	
		return entity;
	}
	
	/**
	 * 不通过
	 * @param id
	 */
	@Transactional
	public YskBuyKucunIntegralApplyEntity notpass(int id) {
		
		YskBuyKucunIntegralApplyEntity applyEntity = buyKucunIntegralApplyRepository.findById(id).get();
		applyEntity.setStatus((byte)2);
		YskBuyKucunIntegralApplyEntity entity = buyKucunIntegralApplyRepository.saveAndFlush(applyEntity);
    	
		return entity;
	}
}
