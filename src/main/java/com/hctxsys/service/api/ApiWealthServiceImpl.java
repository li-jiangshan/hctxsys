package com.hctxsys.service.api;

import com.hctxsys.entity.YskBuyKucunIntegralApplyEntity;
import com.hctxsys.entity.YskIntegralDetailEntity;
import com.hctxsys.entity.YskKucunIntegralDetailEntity;
import com.hctxsys.entity.YskMoneyDetailEntity;
import com.hctxsys.entity.YskReturnIntegralEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.entity.YskUserWealthEntity;
import com.hctxsys.repository.BuyKucunIntegralApplyRepository;
import com.hctxsys.repository.ConfigRepository;
import com.hctxsys.repository.IntegralDetailRepository;
import com.hctxsys.repository.KucunIntegralDetailRepository;
import com.hctxsys.repository.MoneyDetailRepository;
import com.hctxsys.repository.ReturnIntegralRepository;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.repository.UserWealthRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.MyUtils;
import com.hctxsys.vo.api.ApiKuncunIntegralDetailVo;
import com.hctxsys.vo.api.JsonResult;
import com.mysql.jdbc.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("wealthService")
public class ApiWealthServiceImpl {

    @Autowired
	private UserRepository userRepository;
    @Autowired
    private UserWealthRepository userWealthRepository;
    @Autowired
    private IntegralDetailRepository integralDetailRepository;
    @Autowired
    private ReturnIntegralRepository returnIntegralRepository;
    @Autowired
    private MoneyDetailRepository moneyDetailRepository;
    @Autowired
    private KucunIntegralDetailRepository kucunIntegralDetailRepository;
	@Autowired
	private ConfigRepository configRepository;
	@Autowired
	private BuyKucunIntegralApplyRepository buyKucunIntegralApplyRepository;

    @Transactional
    public JsonResult getWealth(YskUserEntity user) {
        JsonResult jsonResult=new JsonResult();
        try {
            YskUserWealthEntity wealthInfo = userWealthRepository.findByUid(user.getUserid());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar calendar=Calendar.getInstance();
            Date date = new Date();
            String newTime = DateUtils.getTime(dateFormat.format(date), dateFormat);
            calendar.setTime(date);
            calendar.add(Calendar.DATE,-1);
            String oldTime=DateUtils.getTime(dateFormat.format(calendar.getTime()),dateFormat);
            List<YskIntegralDetailEntity> all = integralDetailRepository.findByFromTypeAndCreateTimeBetween((byte) 1, Integer.valueOf(oldTime), Integer.valueOf(newTime));
            BigDecimal bigDecimal=new BigDecimal(0);
            for (YskIntegralDetailEntity integralDetailEntity : all) {
                bigDecimal=bigDecimal.add(integralDetailEntity.getMoney());
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("wealth",wealthInfo);
            map.put("bankNum",wealthInfo.getBankList().size());
            map.put("integralAdd",bigDecimal);
            jsonResult.setCode(1);
            jsonResult.setMessage("获取成功");
            jsonResult.setData(map);
        }
        catch (Exception e) {
            e.printStackTrace();
            jsonResult.setCode(0);
            jsonResult.setMessage("获取失败");
        }
        return jsonResult;
    }
    
    //获取购买用户余额与库存积分
    @Transactional
    public JsonResult getKucunInfo(ApiKuncunIntegralDetailVo vo) {

        JsonResult jsonResult=new JsonResult();


    	//查询登录用户信息
    	Optional<YskUserEntity> opUserEntity = userRepository.findById(vo.getUid());
    	
    	if (!opUserEntity.isPresent()) {
    		jsonResult.setCode(0);
    		jsonResult.setMessage("用户不存在");
	        return jsonResult;
    	}
    	
    	YskUserEntity userEntity = opUserEntity.get();
    	
    	String maxDistributionIntegral = "5000000";
    	String countDistributionIntegral = "10";
    	
    	if (!StringUtils.isNullOrEmpty(userEntity.getMaxDistributionIntegral())) {
    		maxDistributionIntegral = userEntity.getMaxDistributionIntegral();
    	}

    	if (!StringUtils.isNullOrEmpty(userEntity.getCountDistributionIntegral())) {
    		countDistributionIntegral = userEntity.getCountDistributionIntegral();
    	}
    	
    	YskUserWealthEntity userWealthEntity = userEntity.getWealthEntity();
        
        Map<String, String> map = new HashMap<>();
        map.put("maxDistributionIntegral", maxDistributionIntegral);
        map.put("countDistributionIntegral", countDistributionIntegral);
        map.put("money", String.valueOf(userWealthEntity.getMoney()));
        map.put("kucunIntegral", String.valueOf(userWealthEntity.getKucunIntegral()));
        jsonResult.setData(map);
    	
    	return jsonResult;
    }
    
    //获取可以购买的库存积分
    @Transactional
    public JsonResult getKucunInfoByMoney(ApiKuncunIntegralDetailVo vo) {
        JsonResult jsonResult=new JsonResult();
    	//计算应该返还商家库存积分
    	BigDecimal kucunIntegral = this.getKunCunByMoney(vo.getMoney());
        Map<String, String> map = new HashMap<>();
        map.put("kucunIntegral", String.valueOf(kucunIntegral));
        jsonResult.setData(map);
    	return jsonResult;
    }
    
    //计算可购买积分
    private BigDecimal getKunCunByMoney(BigDecimal money) {
    	//库存积分比例数据
    	YskReturnIntegralEntity returnIntegralEntity = returnIntegralRepository.getOne(1);
    	BigDecimal stockIntegralRate = new BigDecimal(returnIntegralEntity.getStockIntegral()).divide(new BigDecimal(100));
    	//反推消费者消费金额
    	BigDecimal consumeMoney = money.divide(stockIntegralRate,0);
    	//计算应该返还商家库存积分
    	BigDecimal kucunIntegral = consumeMoney.multiply(new BigDecimal(returnIntegralEntity.getReturnIntegral()));
    	return kucunIntegral;
    }
    
    //获取发放库存积分
    @Transactional
    public JsonResult getSellKucunInfoByMoney(ApiKuncunIntegralDetailVo vo) {
        JsonResult jsonResult=new JsonResult();
    	//库存积分比例数据
    	YskReturnIntegralEntity returnIntegralEntity = returnIntegralRepository.getOne(1);
    	//计算应该返还商家库存积分
    	BigDecimal kucunIntegral = vo.getMoney().multiply(new BigDecimal(returnIntegralEntity.getReturnIntegral()));
        Map<String, String> map = new HashMap<>();
        map.put("kucunIntegral", String.valueOf(kucunIntegral));
        jsonResult.setData(map);
    	return jsonResult;
    }
    
    //购买库存积分
    @Transactional
    public JsonResult saveKucunIntegral(ApiKuncunIntegralDetailVo vo) {

        JsonResult returnVo=new JsonResult();

    	//查询登录用户信息
    	Optional<YskUserEntity> opUserEntity = userRepository.findById(vo.getUid());
    	
    	if (!opUserEntity.isPresent()) {
			returnVo.setCode(0);
			returnVo.setMessage("用户不存在");
	        return returnVo;
    	}
    	
    	YskUserEntity userEntity = opUserEntity.get();
    	
    	YskUserWealthEntity userWealthEntity = userEntity.getWealthEntity();

    	if (userEntity.getSeller() == 0) {
			returnVo.setCode(0);
			returnVo.setMessage("非商家用户不可购买库存积分");
	        return returnVo;
    	}
    	
    	if (userWealthEntity.getMoney().compareTo(vo.getMoney()) == -1) {
			returnVo.setCode(0);
			returnVo.setMessage("金额不足'");
	        return returnVo;
    	}
    	
    	//计算应该返还商家库存积分
    	BigDecimal kucunIntegral = this.getKunCunByMoney(vo.getMoney());
    	
    	YskBuyKucunIntegralApplyEntity kucunApply = new YskBuyKucunIntegralApplyEntity();
    	kucunApply.setUid(vo.getUid()); //用户id
    	kucunApply.setMoney(vo.getMoney());//购买金额
    	kucunApply.setKucunIntegral(kucunIntegral);//购买库存积分
    	
    	YskReturnIntegralEntity returnIntegralEntity = returnIntegralRepository.getOne(1);
    	if (returnIntegralEntity.getToSeller() == 1) {
    		kucunApply.setHasIntegral((byte) 1);//发放正常积分
			kucunApply.setIntegral(vo.getMoney().multiply(new BigDecimal(100))); // 发放积分
			//userWealthEntity.setIntegral(userWealthEntity.getIntegral().add(vo.getMoney()));
		} else {
    		kucunApply.setHasIntegral((byte) 0);//不发放正常积分
		}
    	kucunApply.setVoucherImg(vo.getImg()); //凭证

    	//运营比例
    	BigDecimal runcost = new BigDecimal(returnIntegralEntity.getRuncost()).divide(new BigDecimal(100));
    	//平台运营费
    	BigDecimal operatingAmount = vo.getMoney().multiply(runcost);
    	//返现额度
    	BigDecimal returnAmount = vo.getMoney().subtract(operatingAmount);
    	kucunApply.setOperatingAmount(operatingAmount); //平台运营费
    	kucunApply.setReturnAmount(returnAmount); //返现额度
    	kucunApply.setStatus((byte) 0); //状态 0-申请中 1-已通过 2-已拒绝
    	kucunApply.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
    	buyKucunIntegralApplyRepository.save(kucunApply);
    	
//    	BigDecimal oldMoney = userWealthEntity.getMoney();
//    	BigDecimal oldKucunIntegral = userWealthEntity.getKucunIntegral();
//    	//库存积分比例数据
//    	YskReturnIntegralEntity returnIntegralEntity = returnIntegralRepository.getOne(1);
//    	/** 1. 保存用户财富信息 */
//    	//购买库存积分 金额*比例
//    	BigDecimal kucunIntegral = vo.getMoney().multiply(new BigDecimal(100));
//    	//减少余额
//    	userWealthEntity.setMoney(userWealthEntity.getMoney().subtract(vo.getMoney()));
//    	//增加库存积分
//    	userWealthEntity.setKucunIntegral(userWealthEntity.getKucunIntegral().add(kucunIntegral));
//    	//如果平台跟商家返现积分开关 为打开则保存正常积分
//    	if (returnIntegralEntity.getToSeller() == 1) {
//    		//增加正常积分
//    		userWealthEntity.setIntegral(userWealthEntity.getIntegral().add(vo.getMoney()));
//    	}
//    	//保存用户财富信息
//    	userWealthRepository.saveAndFlush(userWealthEntity);
    	
    	/** 1. 添加金额明细信息 */
//    	YskMoneyDetailEntity moneyDetailEntity = new YskMoneyDetailEntity();
//    	moneyDetailEntity.setContent("购买" + vo.getKucunIntegral() + "个库存积分");
//    	moneyDetailEntity.setFromType((byte) 2); //1-转入 2-转出
//    	moneyDetailEntity.setType("buykucunintegral"); //明细类型 
//    	moneyDetailEntity.setTypeName("购买库存积分");
//    	moneyDetailEntity.setUid(vo.getUid());
//    	moneyDetailEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
//    	moneyDetailEntity.setStatus((byte) 0); //0-未支付 1-已支付 2-不通过
//    	moneyDetailEntity.setMoney(vo.getMoney()); //金额
//    	moneyDetailEntity.setMoneyRecord(oldMoney.subtract(vo.getMoney()));
//    	moneyDetailEntity.setOrderNo("");
//    	moneyDetailRepository.save(moneyDetailEntity);
    	
//    	/** 3. 添加库存积分明细 */
//    	YskKucunIntegralDetailEntity kucunIntegralDetailEntity  = new YskKucunIntegralDetailEntity();
//    	kucunIntegralDetailEntity.setContent("购买" + kucunIntegral + "个库存积分");
//    	kucunIntegralDetailEntity.setFromType((byte) 1); //1-转入 2-转出
//    	kucunIntegralDetailEntity.setType("buykucunintegral"); //明细类型 
//    	kucunIntegralDetailEntity.setTypeName("购买库存积分");
//    	kucunIntegralDetailEntity.setUid(vo.getUid());
//    	kucunIntegralDetailEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
//    	kucunIntegralDetailEntity.setStatus((byte) 1); //0-未支付 1-已支付 2-不通过
//    	kucunIntegralDetailEntity.setMoney(kucunIntegral); //金额
//    	kucunIntegralDetailEntity.setMoneyRecord(oldKucunIntegral.add(kucunIntegral));
//    	KucunIntegralDetailRepository.save(kucunIntegralDetailEntity);
//    	
//    	/** 4. 添加平台运营 */
//    	YskSystemCostEntity systemCostEntity = new YskSystemCostEntity();
//    	//运营比例
//    	BigDecimal runcost = new BigDecimal(returnIntegralEntity.getRuncost()).divide(new BigDecimal(100));
//    	//平台运营费
//    	BigDecimal operatingAmount = vo.getMoney().multiply(runcost);
//    	//返现额度
//    	BigDecimal returnAmount = vo.getMoney().subtract(operatingAmount);
//    	systemCostEntity.setOperatingAmount(operatingAmount); //平台运营费
//    	systemCostEntity.setReturnAmount(returnAmount); //返现额度
//    	systemCostEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
//    	systemCostRepository.save(systemCostEntity);
        	
		returnVo.setMessage("提交审批成功,请耐心等待审核");
    	return returnVo;
    }
    
    //发放库存积分
    @Transactional
    public JsonResult sellKucunintegral(ApiKuncunIntegralDetailVo vo) {
        JsonResult returnVo=new JsonResult();
        
    	//查询登录用户信息
    	Optional<YskUserEntity> opUserEntity = userRepository.findById(vo.getUid());
    	
    	if (!opUserEntity.isPresent()) {
			returnVo.setCode(0);
			returnVo.setMessage("用户不存在");
	        return returnVo;
    	}
    	//用户个人信息
    	YskUserEntity userEntity = opUserEntity.get();
    	//用户个人财富信息
    	YskUserWealthEntity userWealthEntity = userEntity.getWealthEntity();
    	//用户个人库存积分
    	BigDecimal oldKucunIntegral = userWealthEntity.getKucunIntegral();
    	
    	String maxDistributionIntegral = "5000000";
    	String countDistributionIntegral = "10";
    	
    	if (!StringUtils.isNullOrEmpty(userEntity.getMaxDistributionIntegral())) {
    		maxDistributionIntegral = userEntity.getMaxDistributionIntegral();
    	}

    	if (!StringUtils.isNullOrEmpty(userEntity.getCountDistributionIntegral())) {
    		countDistributionIntegral = userEntity.getCountDistributionIntegral();
    	}
        
		//校验手机号是否注册
		List<YskUserEntity> listByMobile = userRepository.findByMobile(vo.getMobile());
		if (listByMobile.size() == 0) {
        	returnVo.setCode(0);
        	returnVo.setMessage("买家不存在");
	        return returnVo;
		} 
    	//买家个人信息
    	YskUserEntity buyUser = listByMobile.get(0);
    	//买家个人财富信息
    	YskUserWealthEntity buyUserWealth = buyUser.getWealthEntity();
    	BigDecimal buyUserOldIntegral = buyUserWealth.getIntegral();
		
		if (userEntity.getSeller() != 1) {
        	returnVo.setCode(0);
        	returnVo.setMessage("非商家用户,不能分发");
	        return returnVo;
		}
        if(oldKucunIntegral.compareTo(vo.getKucunIntegral()) == -1){
        	returnVo.setCode(0);
        	returnVo.setMessage("库存积分不足");
	        return returnVo;
        }
		
        //单笔限额5W，每天限10单
        if(vo.getKucunIntegral().compareTo(new BigDecimal(maxDistributionIntegral)) == 1){
        	returnVo.setCode(0);
        	returnVo.setMessage("单笔发放积分不能大于" + maxDistributionIntegral);
	        return returnVo;
        }
        //查询当日发放几次库存积分
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar=Calendar.getInstance();
        Date date = new Date();
        String newTime = DateUtils.getTime(dateFormat.format(date), dateFormat);
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1);
        String oldTime=DateUtils.getTime(dateFormat.format(calendar.getTime()),dateFormat);
        List<YskMoneyDetailEntity> kuncunList = moneyDetailRepository.findAllByCreateTimeBetweenAndUidAndFromTypeAndType(
        		Integer.valueOf(oldTime), Integer.valueOf(newTime),vo.getUid(), (byte) 2, "sellkucunintegral");
        if (kuncunList.size() > Integer.valueOf(countDistributionIntegral)) {
        	returnVo.setCode(0);
        	returnVo.setMessage("每天最多可分发" + countDistributionIntegral + "单");
	        return returnVo;
        }
        
        /** 处理业务开始  Start */
        /** 1. 扣除自己的库存积分 */
        userWealthEntity.setKucunIntegral(oldKucunIntegral.subtract(vo.getKucunIntegral()));
        userWealthRepository.saveAndFlush(userWealthEntity);
        /** 2. 添加自己的库存积分明细 */
    	YskKucunIntegralDetailEntity kucunIntegralDetailEntity  = new YskKucunIntegralDetailEntity();
    	kucunIntegralDetailEntity.setContent(vo.getContent());
    	kucunIntegralDetailEntity.setFromType((byte) 2); //1-转入 2-转出
    	kucunIntegralDetailEntity.setType("sellkucunintegral"); //明细类型 
    	kucunIntegralDetailEntity.setTypeName("分发库存积分");
    	kucunIntegralDetailEntity.setUid(vo.getUid());
    	kucunIntegralDetailEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
    	kucunIntegralDetailEntity.setStatus((byte) 1); //0-未支付 1-已支付 2-不通过
    	kucunIntegralDetailEntity.setMoney(vo.getKucunIntegral()); //金额
    	kucunIntegralDetailEntity.setMoneyRecord(oldKucunIntegral.subtract(vo.getKucunIntegral()));
    	kucunIntegralDetailRepository.save(kucunIntegralDetailEntity);
        /** 3. 给消费者添加积分 */
    	this.addUserIntegral(buyUserWealth, vo.getKucunIntegral());
        /** 4. 给消费者添加积分明细 */
    	this.addIntegralDetail(buyUserWealth.getUid(), buyUserOldIntegral, vo.getKucunIntegral(), "buygood", "购买商品");
        /** 4. 给商家上级、消费者上级奖励积分 */
    	//一级消费奖励
    	if (buyUser.getPid() != 0) {
    		this.addUserLevelIntegral(buyUser.getPid(), vo.getKucunIntegral(), 15, "一级消费奖励");
    	}
    	//二级消费奖励
    	if (buyUser.getGid() != 0) {
    		this.addUserLevelIntegral(buyUser.getGid(), vo.getKucunIntegral(), 42, "二级消费奖励");
    	}
    	//一级销售奖励
    	if (userEntity.getPid() != 0) {
    		this.addUserLevelIntegral(userEntity.getPid(), vo.getKucunIntegral(), 16, "一级销售奖励");
    	}
    	//二级销售奖励
    	if (userEntity.getGid() != 0) {
    		this.addUserLevelIntegral(userEntity.getGid(), vo.getKucunIntegral(), 43, "二级销售奖励");
    	}
        /** 处理业务开始  End */
    	return returnVo;
    }
    
    /**
     * 更新用户财富积分信息
     * @param userWealth 用户财富实体
     * @param addIntegral 增加积分
     */
    private void addUserIntegral(YskUserWealthEntity userWealth, BigDecimal addIntegral) {
    	userWealth.setIntegral(userWealth.getIntegral().add(addIntegral));
    	userWealth.setTotalIntegral(userWealth.getTotalIntegral().add(addIntegral));
    	userWealth.setUptimeing(new Timestamp(System.currentTimeMillis()));
        userWealthRepository.saveAndFlush(userWealth);
    }
    
    /**
     *  添加用户积分明细
     * @param uid   用户id
     * @param oldIntegral 原积分
     * @param addIntegral 增加积分
     * @param type 类型
     * @param typeName 类型名称
     */
    private void addIntegralDetail(int uid, BigDecimal oldIntegral, BigDecimal addIntegral, String type, String typeName) {
    	YskIntegralDetailEntity buyIntegralDetailEntity  = new YskIntegralDetailEntity();
    	buyIntegralDetailEntity.setContent(typeName + addIntegral);
    	buyIntegralDetailEntity.setFromType((byte) 1); //1-转入 2-转出
    	buyIntegralDetailEntity.setType(type); //明细类型 
    	buyIntegralDetailEntity.setTypeName(typeName);
    	buyIntegralDetailEntity.setUid(uid);
    	buyIntegralDetailEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
    	buyIntegralDetailEntity.setStatus((byte) 1); //0-未支付 1-已支付 2-不通过
    	buyIntegralDetailEntity.setMoney(addIntegral); //金额
    	buyIntegralDetailEntity.setMoneyRecord(oldIntegral.add(addIntegral));
    	integralDetailRepository.save(buyIntegralDetailEntity);
    }
    
    /**
     * 增加一级二级消费销售奖励
     * @param uid 用户id
     * @param kucunIntegral  积分
     * @param configId 一级二级消费销售奖励比例Id
     * @param typeName 明细说明
     */
    private void addUserLevelIntegral(int uid, BigDecimal kucunIntegral, int configId, String typeName) {
    	//查询登录用户信息
    	Optional<YskUserEntity> buyPidUserEntity = userRepository.findById(uid);
    	if (buyPidUserEntity.isPresent()) { //如果存在
        	//库存积分比例数据
        	YskReturnIntegralEntity returnIntegralEntity = returnIntegralRepository.getOne(1);
     		String configValue = configRepository.findById(configId).get().getValue(); //一级二级消费销售奖励(积分)
     		BigDecimal returnIntegral = new BigDecimal(returnIntegralEntity.getReturnIntegral()).divide(new BigDecimal(100));
    		BigDecimal ratio = new BigDecimal(configValue).divide(new BigDecimal(100)).multiply(returnIntegral);//一级二级消费销售奖励比例
    		
    		YskUserWealthEntity userWealthEntity = buyPidUserEntity.get().getWealthEntity();
    		//用户原积分
    		BigDecimal userOldIntegral = userWealthEntity.getIntegral();
    		//一级二级消费销售增加积分
    		BigDecimal userAddIntegral = kucunIntegral.multiply(ratio);
    		//更新一级二级消费销售财富信息
    		this.addUserIntegral(userWealthEntity, userAddIntegral);
    		//更新一级二级消费销售积分明细
    		this.addIntegralDetail(userWealthEntity.getUid(), userOldIntegral, userAddIntegral, "buy", typeName);
    	}
    }
    
}
