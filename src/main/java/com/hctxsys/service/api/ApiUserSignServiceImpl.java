package com.hctxsys.service.api;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;

import com.hctxsys.entity.YskDaydetailEntity;
import com.hctxsys.entity.YskDaysignEntity;
import com.hctxsys.entity.YskIntegralDetailEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.entity.YskUserWealthEntity;
import com.hctxsys.repository.DaydetailRepository;
import com.hctxsys.repository.DaysignRepository;
import com.hctxsys.repository.IntegralDetailRepository;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.repository.UserWealthRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.vo.api.JsonResult;

@Service("apiUserSignService")
public class ApiUserSignServiceImpl {

	@Autowired
	private DaydetailRepository daydetailRepository;
	@Autowired
	private DaysignRepository daysignRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserWealthRepository userWealthRepository;
	@Autowired
	private IntegralDetailRepository integralDetailRepository;
	
	
	public void getQdData(Integer uid, Model model) {
		Calendar now = Calendar.getInstance();
		String day = String.valueOf(now.get(Calendar.DATE));
		if(day.length() == 1) {
			day = "0" + day;
		}
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		if(month.length() == 1) {
			month = "0" + month;
		}
		String year = String.valueOf(now.get(Calendar.YEAR));
		YskDaysignEntity daySignEntity = daysignRepository.findByUidAndYearAndMoth(uid, year, month);
		YskUserEntity userEntity = userRepository.findById(uid).get();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String searchTimeStart = DateUtils.getTime(year + month + "01" + "00" + "00" + "00",sdf);
		String maxDay = String.valueOf(now.getActualMaximum(Calendar.DATE));
		String searchTimeEnd = DateUtils.getTime(year + month + maxDay + "23" + "59" + "59",sdf);
		List<YskDaydetailEntity> dayDetailEntityList = daydetailRepository.findByDUidAndDAddtimeBetween(uid, searchTimeStart, searchTimeEnd);
		List<HashMap<String,String>> dayDetailList = new ArrayList<HashMap<String,String>>();
		
		SimpleDateFormat dayDetailFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		for(YskDaydetailEntity dayDetailEntity : dayDetailEntityList) {
			HashMap<String,String> dayDetail = new HashMap<String,String>();
			dayDetail.put("addtime", dayDetailFormat.format(Long.valueOf(dayDetailEntity.getDAddtime() + "000")));
//			dayDetail.put("addtime", dayDetailEntity.getDAddtime());
			dayDetail.put("Integral", String.valueOf(dayDetailEntity.getDMoney()));
			dayDetailList.add(dayDetail);
		}
		
		
		if(daySignEntity == null) {
			model.addAttribute("nowDate",year + "年" + month + "月" + day + "日");
			model.addAttribute("lianDay", "0");
			model.addAttribute("Integral", "0");
			model.addAttribute("totalDay", "0");
			model.addAttribute("day", null);
		} else {
			model.addAttribute("nowDate",year + "年" + month + "月" + day + "日");
			model.addAttribute("lianDay", daySignEntity.getLianDay());
			model.addAttribute("Integral", daySignEntity.getTotalJifen());
			model.addAttribute("totalDay", daySignEntity.getTotalday());
			model.addAttribute("day", daySignEntity.getDay());
		}
		model.addAttribute("uid",uid);
		model.addAttribute("signTotal", userEntity.getSignTotal());
		model.addAttribute("JfDaysign", userEntity.getJfDaysign());
		model.addAttribute("dayDetailList", dayDetailList);
	}
	
	@Transactional
	public JsonResult signadd(Integer uid) {
		JsonResult result = new JsonResult();
		
		try {
			Calendar now = Calendar.getInstance();
			String day = String.valueOf(now.get(Calendar.DATE));
			if(day.length() == 1) {
				day = "0" + day;
			}
			String month = String.valueOf(now.get(Calendar.MONTH) + 1);
			if(month.length() == 1) {
				month = "0" + month;
			}
			String year = String.valueOf(now.get(Calendar.YEAR));
			YskDaysignEntity daySignEntity = daysignRepository.findByUidAndYearAndMoth(uid, year, month);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat dayDetailFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Integer Integral = 10;
			YskUserEntity userEntity = userRepository.findById(uid).get();
			Date nowDate = new Date();
			String time = DateUtils.getTime(sdf.format(nowDate),sdf);
			
			// 当月有签到记录时
			if(daySignEntity != null) {
				long todayStartDate = Long.valueOf(DateUtils.getTime(year + month + day + "00" + "00" + "00",sdf) + "000");
				long todaySignDate = Long.valueOf(daySignEntity.getSavetime() + "000");
				if(todaySignDate > todayStartDate) {
					result.setCode(1);
					result.setMessage("今日已签到过了");
					return result;
				} else {
					daySignEntity.setSavetime(time);
					daySignEntity.setTotalday(daySignEntity.getTotalday() + 1);
					String[] dayArray = daySignEntity.getDay().split(",");
					// 判断上次签到是否是前一天
					if (dayArray[dayArray.length - 1].equals(String.valueOf(now.get(Calendar.DATE) - 1))) {
						daySignEntity.setLianDay(daySignEntity.getLianDay() + 1);
					} else {
						daySignEntity.setLianDay(1);
					}
					if(daySignEntity.getLianDay() % 7 == 0) {
						Integral = 40;
					}
					daySignEntity.setDay(daySignEntity.getDay() + "," + day);
					daySignEntity.setTotalJifen(daySignEntity.getTotalJifen() + Integral);
					
				}
			} else {
				// 当月没有记录时，新建当月记录
				daySignEntity = new YskDaysignEntity();
				daySignEntity.setUid(uid);
				daySignEntity.setYear(year);
				daySignEntity.setMoth(month);
				daySignEntity.setDay(day);
				daySignEntity.setTotalday(1);
				daySignEntity.setLianDay(1);
				daySignEntity.setSavetime(time);
				daySignEntity.setTotalJifen(Integral);
			}
			
			userEntity.setSignTotal(userEntity.getSignTotal() + 1);
			userEntity.setJfDaysign(userEntity.getJfDaysign() + Integral);
			
			daysignRepository.saveAndFlush(daySignEntity);
			userRepository.saveAndFlush(userEntity);
			
			// 积分
			excIntegral(uid, new BigDecimal(String.valueOf(Integral)), time);
			// 添加签到详细
			addDayDetail(uid, Integral, "连续签到" + String.valueOf(daySignEntity.getLianDay()) + "天", time);
			
			// 返回数据
			HashMap<String,Object> resultData = new HashMap<String,Object>();
			resultData.put("lianDay", daySignEntity.getLianDay());
			resultData.put("totalDay", daySignEntity.getTotalday());
			resultData.put("Integral", Integral);
			resultData.put("signTotal", userEntity.getSignTotal());
			resultData.put("JfDaysign",userEntity.getJfDaysign());
			
			HashMap<String,Object> dayDetail = new HashMap<String,Object>();
			dayDetail.put("addtime", dayDetailFormat.format(Long.valueOf(time + "000")));
			dayDetail.put("Integral",Integral);
			resultData.put("dayDetail", dayDetail);
			
			resultData.put("thisIntegral", Integral);
			result.setCode(0);
			result.setData(resultData);
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			result.setCode(1);
			result.setMessage("签到处理失败，请重试");
            return result;
		}
		
	}
	
	// 积分 
 	private void excIntegral(int uid, BigDecimal integral, String time) {
 		
 		YskUserWealthEntity userWealthEntity = userWealthRepository.findById(uid).get();
 		userWealthEntity.setIntegral(userWealthEntity.getIntegral().add(integral));
 		userWealthEntity.setTotalIntegral(userWealthEntity.getTotalIntegral().add(integral));
 		userWealthEntity.setUptimeing(new Timestamp(System.currentTimeMillis()));
 		userWealthRepository.saveAndFlush(userWealthEntity);
 		// huabao_detail添加一条数据
 		
 		YskIntegralDetailEntity integralDetailEntity = new YskIntegralDetailEntity();
 		integralDetailEntity.setContent("签到奖励" + integral.toString());
 		integralDetailEntity.setFromType((byte)1);
 		integralDetailEntity.setType("usersign");
 		integralDetailEntity.setTypeName("签到奖励");
 		integralDetailEntity.setUid(uid);
 		integralDetailEntity.setCreateTime(new Integer(time));
 		integralDetailEntity.setStatus((byte)1);
 		integralDetailEntity.setMoney(integral);
 		integralDetailEntity.setMoneyRecord(userWealthEntity.getIntegral());
 		integralDetailRepository.saveAndFlush(integralDetailEntity);
 	}
 	
 	private void addDayDetail(Integer uid, int integral, String content, String time) {
 		YskDaydetailEntity dayDetailEntity = new YskDaydetailEntity();
 		dayDetailEntity.setDUid(uid);
 		dayDetailEntity.setDAddtime(time);
 		dayDetailEntity.setDContent(content);
 		dayDetailEntity.setDMoney(integral);
 		daydetailRepository.saveAndFlush(dayDetailEntity);
 	}
}
