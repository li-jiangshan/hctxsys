package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskSellerApplyEntity;
import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.SellerApplyRepository;
import com.hctxsys.repository.ShopInfoRepository;
import com.hctxsys.repository.YsKUserRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.util.Result;

@Service("sellerUser")
public class SellerUserServiceImpl {
	@Autowired
	private SellerApplyRepository sellerUserRepository;
	@Autowired
	private ShopInfoRepository shopInfoRepository;
	@Autowired
	private YsKUserRepository ysKUserRepository;
	
	/**搜索栏功能
	 * @param status 状态
	 * @param page 页码
	 * @param size 每页显示几条数据
	 * @param date_start 开始日期
	 * @param date_end 结束日期
	 * @param querytype 搜索类型
	 * @param keyword 搜索关键字
	 * @return
	 */
	public Page<YskSellerApplyEntity> findByStatusEquals(byte status,int page,int size,String date_start,String date_end,String querytype,String keyword){
		Sort sort = new Sort(Direction.DESC, "id");
		PageRequest pageable=PageRequest.of(page, size, sort);
		Page<YskSellerApplyEntity> list = sellerUserRepository.findAll(new Specification<YskSellerApplyEntity>() {
			
			@Override
			public Predicate toPredicate(Root<YskSellerApplyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates=new ArrayList<>();//条件集合
				predicates.add(cb.equal(root.get("status"), status));
				if(null!=date_start) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(date_start))));
				}
				if(null!=date_end) {
					predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(date_end))+60 * 60 * 24));
				}
				if(null!=querytype&&null!=keyword) {
					//predicates.add(cb.equal(root.get(querytype), keyword));
					predicates.add(cb.like(root.get(querytype), "%" + keyword + "%"));
				}
				Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pageable);
		return list;
	}
	
	/**获取用户信息
	 * @param id 用户id
	 * @return
	 */
	public YskSellerApplyEntity findById(Integer id) {
		Optional<YskSellerApplyEntity> byId = sellerUserRepository.findById(id);
		YskSellerApplyEntity sellerApply = byId.get();
		return sellerApply;
	}
	
	/**审核
	 * @param id 用户id
	 * @param content 回复类容
	 * @param agree 状态
	 * @return
	 */
	@Transactional
	public Result checkSellerApply(Integer id,String content,Byte agree,String shopJ,String shopW) {
		YskShopInfoEntity res=null;
		//没有选择状态
		if(agree==null||agree==0) {
			return new Result(0, "请选择状态", "", "");
		}
		//没有写拒绝理由
		if(content.length()==0&&agree==2) {
			return new Result(0, "请填写拒绝理由", "", "");
		}
		YskSellerApplyEntity sellerApply = sellerUserRepository.findByIdAndStatus(id, (byte)0);
		Optional<YskShopInfoEntity> findById = shopInfoRepository.findById(sellerApply.getUid());
		//已经注册为商家
		if(findById.isPresent()&&agree==1) {
			return new Result(0, "该用户已是商家", "", "");
		}
		sellerApply.setStatus(agree);
		YskSellerApplyEntity apply = sellerUserRepository.saveAndFlush(sellerApply);
		//添加店铺信息
		if(null!=apply&&agree==1) {
			Optional<YskUserEntity> optional = ysKUserRepository.findById(sellerApply.getUid());
			YskUserEntity yskUser = optional.get();
			yskUser.setLevel((byte)2);
			yskUser.setSeller((byte)1);
			ysKUserRepository.saveAndFlush(yskUser);
			YskShopInfoEntity info = new YskShopInfoEntity();
			info.setUid(sellerApply.getUid());
			info.setShopName(sellerApply.getShopName());
			info.setResponName(sellerApply.getResponName());
			info.setResponMobile(sellerApply.getResponMobile());
			info.setResponEmail(sellerApply.getResponEmail());
			info.setProvince(sellerApply.getProvince());
			info.setCity(sellerApply.getCity());
			info.setDistrict(sellerApply.getDistrict());
			info.setAddresssDetail(sellerApply.getAddresssDetail());
			info.setCreateTime(sellerApply.getCreateTime());
			info.setIndustryId(String.valueOf(sellerApply.getIndustryId()));
			info.setFee(sellerApply.getFee());
			info.setIndustryName(sellerApply.getIndustryName());
			info.setShopJ(shopJ);
			info.setShopW(shopW);
			res = shopInfoRepository.saveAndFlush(info);
		}
	
		if(null!=res||null!=apply) {
			return new Result(1, "操作成功", "/Admin/Sellerapply/apply", "");
		}else {
			return new Result(0, "操作失败", "", "");
		}
	}
	
	public String setHref(String root,byte status,Integer page,String date_start,String date_end,String querytype,String keyword) {
		StringBuffer sb = new StringBuffer(root);
		if(status==0) {
			sb.append("apply?");
		}
		if(status!=0) {
			sb.append("apply/type/"+status+"?");
		}
		if(null!=date_start) {
			sb.append("date_start="+date_start+"&");
		}
		if(null!=date_end) {
			sb.append("date_end="+date_end+"&");
		}
		if(null!=querytype) {
			sb.append("querytype="+querytype+"&");
		}
		if(null!=keyword) {
			sb.append("keyword="+keyword+"&");
		}
		if(null!=page) {
			sb.append("status="+status+"&page="+page);
		}
		return sb.toString();
	}
}
