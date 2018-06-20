package com.hctxsys.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskGoodCategoryEntity;
import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskGoodImgEntity;
import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.repository.GoodCategoryRepository;
import com.hctxsys.service.api.ApiGoodDetailServiceImpl;

/**
 * @ClassName:ApiGoodDetailController
 * @Author:li
 * @CreateDate:2018年4月24日
 */
@Controller
@RequestMapping(value = "/mall/good")
public class ApiGoodDetailController {

    @Autowired
    private ApiGoodDetailServiceImpl apiGoodDetailService;
    
    @Autowired
    private GoodCategoryRepository goodCategoryRepository;

    /**
     * 商品详情
     *
     * @param good_id
     * @param model
     * @return detail.html
     */
    @RequestMapping(value = "/details/good_id/{good_id}", method = RequestMethod.GET)
    public String goodImgList(@PathVariable Integer good_id, Model model) {
        // 获取商品详细轮播图
        List<YskGoodImgEntity> goodImgList = apiGoodDetailService.getGoodImgList(good_id);
        model.addAttribute("list_img", goodImgList);

        // 获取商品详细价格，名称等
        YskGoodEntity goodInfo = apiGoodDetailService.getGoodInfo(good_id);
        model.addAttribute("info", goodInfo);

		//TODO: 暂时写死
		YskGoodCategoryEntity categoryList = goodCategoryRepository.findById((short) goodInfo.getCategoryId()).get();

        // 获取店铺信息
		String shopMobile;
		if (goodInfo.getSellerId() > 0) {
			YskShopInfoEntity shopInfo = apiGoodDetailService.getShopInfo(goodInfo.getSellerId());
			model.addAttribute("shop_info", shopInfo);
			shopMobile = shopInfo.getServerTel();
			model.addAttribute("shop_mobile", shopMobile);
		} else {
			Optional<YskConfigEntity> config = apiGoodDetailService.getConfig();
			shopMobile = config.get().getValue();
			model.addAttribute("shop_mobile", shopMobile);
		}
		if (categoryList.getPidPath().indexOf("865") != -1) {
			// 旅游联系电话
			shopMobile = "0411-39987715";
			model.addAttribute("shop_mobile", shopMobile);
		}
        model.addAttribute("sellerId", goodInfo.getSellerId());
        // 产品评论
        List<YskGoodCommentEntity> goodCommentList = apiGoodDetailService.getGoodCommentList(good_id);
        returnUserName(goodCommentList);
        model.addAttribute("commentList", goodCommentList);
        model.addAttribute("comment_count", goodCommentList.size());

        // 猜你喜欢
        if (goodInfo.getCategoryId() != 0) {
            List<YskGoodEntity> relateList = apiGoodDetailService.getRelateList(goodInfo.getCategoryId());
            model.addAttribute("relateList", relateList);
        }

        return "/mall/good/details";
    }

    /**
     * 全部评论
     *
     * @param good_id
     * @param model
     * @return goodcomment.html
     */
    @RequestMapping(value = "/goodcomment/good_id/{good_id}", method = RequestMethod.GET)
    public String goodCommentList(@PathVariable Integer good_id, Model model) {
        // 查询全部评论
        List<YskGoodCommentEntity> goodCommentListAll = apiGoodDetailService.getGoodCommentListAll(good_id);
        returnUserName(goodCommentListAll);
        List<YskGoodCommentEntity> goodCommentListLow = apiGoodDetailService.getGoodCommentListLow(good_id);
        returnUserName(goodCommentListLow);
        List<YskGoodCommentEntity> goodCommentListMiddle = apiGoodDetailService.getGoodCommentListMiddle(good_id);
        returnUserName(goodCommentListMiddle);
        List<YskGoodCommentEntity> goodCommentListHigh = apiGoodDetailService.getGoodCommentListHigh(good_id);
        returnUserName(goodCommentListHigh);
        HashMap<String, Object> goodCommentDetailList = new HashMap<>();
        goodCommentDetailList.put("all", goodCommentListAll);
        goodCommentDetailList.put("low", goodCommentListLow);
        goodCommentDetailList.put("middle", goodCommentListMiddle);
        goodCommentDetailList.put("high", goodCommentListHigh);
        model.addAttribute("list", goodCommentDetailList);

        return "/mall/good/goodcomment";
    }

    /**
     * 用户名处理
     *
     * @param list
     * @return 处理后用户名
     */
    public List<YskGoodCommentEntity> returnUserName(List<YskGoodCommentEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            String username = list.get(i).getUsername();
            if (username != null && username.length() != 0) {
                username = username.substring(0, 1) + "***";
            } else {
                username = "匿名";
            }
            list.get(i).setUsername(username);
        }
        return list;
    }
}
