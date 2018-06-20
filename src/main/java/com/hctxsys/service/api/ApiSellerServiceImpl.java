package com.hctxsys.service.api;

import com.hctxsys.entity.YskIndustryEntity;
import com.hctxsys.entity.YskSellerApplyEntity;
import com.hctxsys.entity.YskUpdateOrderEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.IndustryRepository;
import com.hctxsys.repository.SellerApplyRepository;
import com.hctxsys.repository.UpdateOrderRepository;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.vo.api.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("sellerService")
public class ApiSellerServiceImpl {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerApplyRepository sellerApplyRepository;
    @Autowired
    private IndustryRepository industryRepository;
	@Autowired
	private UpdateOrderRepository updateOrderRepository;
    
    //查询商家分类信息
    public JsonResult getIndustryInfo() {
    	JsonResult returnVo = new JsonResult();
    	List<YskIndustryEntity> industryList = industryRepository.findAll();
    	returnVo.setData(industryList);
    	return returnVo;
    }
    
    //查询是否申请过联盟商家
    public JsonResult getApplyInfo(YskSellerApplyEntity sellerApply) {
    	JsonResult returnVo = new JsonResult();
    	//查询申请信息
    	List<YskSellerApplyEntity> sellerApplyEntityList = sellerApplyRepository.findByUidOrderByIdDesc(sellerApply.getUid());
    	
    	if (sellerApplyEntityList.size() == 0) {
    		YskSellerApplyEntity newSellerApply = new YskSellerApplyEntity();
    		newSellerApply.setStatus((byte) -1);
    		returnVo.setData(newSellerApply);
        	return returnVo;
    	}
    	
    	returnVo.setData(sellerApplyEntityList.get(0));
    	
    	return returnVo;
    }
    
    //校验是否可以申请联盟商家
    public JsonResult checkApplySeller(YskSellerApplyEntity sellerApply) {
    	JsonResult returnVo = new JsonResult();
    	//查询登录用户信息
    	Optional<YskUserEntity> opUserEntity = userRepository.findById(sellerApply.getUid());
    	
    	if (!opUserEntity.isPresent()) {
    			returnVo.setCode(0);
    			returnVo.setMessage("用户不存在");
    	        return returnVo;
    	}
    	
    	YskUserEntity userEntity = opUserEntity.get();
    	
    	returnVo = this.checkUserInfo(userEntity);
    	
    	return returnVo;
    }
    
    //联盟商家申请
    public JsonResult applySeller(YskSellerApplyEntity sellerApply) {
    	
    	JsonResult returnVo = new JsonResult();
    	
    	//查询申请信息
    	List<YskSellerApplyEntity> sellerApplyEntityList = sellerApplyRepository.findByUidOrderByIdDesc(sellerApply.getUid());
    	
    	if (sellerApplyEntityList.size() > 0 && sellerApplyEntityList.get(0).getStatus() != 2) {
			returnVo.setCode(0);
			returnVo.setMessage("你已经提交过申请了，请耐心等待平台审核");
	        return returnVo;
    	}
    	
    	//查询登录用户信息
    	Optional<YskUserEntity> opUserEntity = userRepository.findById(sellerApply.getUid());
    	
    	if (!opUserEntity.isPresent()) {
    			returnVo.setCode(0);
    			returnVo.setMessage("用户不存在");
    	        return returnVo;
    	}

    	YskUserEntity userEntity = opUserEntity.get();
    	
    	returnVo = this.checkUserInfo(userEntity);

        //如果校验失败返回错误信息
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        
//        List<YskUpdateOrderEntity> updateOrderList = updateOrderRepository.findByUidAndUserLevel(userEntity.getUserid(),2);
//        
//		if (updateOrderList.size() == 0) {
//  			returnVo.setCode(0);
//  			returnVo.setMessage("请先购买牌匾");
//  	        return returnVo;
//		} 
//		
//		YskUpdateOrderEntity updateOrder = updateOrderList.get(0);
//		
//		if (updateOrder.getStatus() == 0) {
//  			returnVo.setCode(0);
//  			returnVo.setMessage("请先购买牌匾");
//  	        return returnVo;
//		}

    	YskIndustryEntity industry = industryRepository.findById(sellerApply.getIndustryId());
    	
    	sellerApply.setUsername(userEntity.getUsername());
    	sellerApply.setAccount(userEntity.getAccount());
    	sellerApply.setMobile(userEntity.getMobile());
    	sellerApply.setIndustryName(industry.getName());
    	sellerApply.setStatus((byte) 0);
    	sellerApply.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //申请时间
    	sellerApplyRepository.save(sellerApply);
    	
    	return returnVo;
    }
    
    private JsonResult checkUserInfo(YskUserEntity userEntity) {

    	JsonResult returnVo = new JsonResult();
    	if (userEntity.getUserType() == 0) {
			returnVo.setCode(0);
			returnVo.setMessage("个人用户不能申请商家");
	        return returnVo;
    	}
    	
    	if (userEntity.getLevel() == 0) {
			returnVo.setCode(0);
			returnVo.setMessage("先升级为VIP用户才能申请商家");
	        return returnVo;
    	}
    	
        if(userEntity.getCheckinfoEntity().getIsCheckCompany() != 2){
			returnVo.setCode(0);
			returnVo.setMessage("请先去认证身份");
	        return returnVo;
        }
        
    	if (userEntity.getSeller() == 1) {
			returnVo.setCode(0);
			returnVo.setMessage("您已是商家，请不要重复申请");
	        return returnVo;
    	}
    	return returnVo;
    }
    

    
    
}
