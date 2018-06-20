package com.hctxsys.controller.adminmall;

import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.service.SellerServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("Adminmall/Seller")
public class SellerController {
    @Autowired
    private SellerServiceImpl sellerService;

    /**
     *店铺列表
     * @param result 动态查询及分页
     * @return
     */
    @GetMapping("index")
    public ModelAndView index(TableResult result) {
        TableResult tableResult = sellerService.indexTable(result);
        ModelAndView modelAndView = new ModelAndView("/adminmall/seller/index");
        modelAndView.addObject("tableResult",tableResult);
        return modelAndView;
    }

    /**
     *店铺详情页
     * @param uid 店铺ID
     * @return
     */
    @GetMapping("detail/id/{uid}")
    public ModelAndView getDeatil(@PathVariable Integer uid) {
        YskShopInfoEntity deatil = sellerService.getDeatil(uid);
        ModelAndView modelAndView = new ModelAndView("adminmall/seller/detail");
        modelAndView.addObject("shop",deatil);
        return modelAndView;
    }

    /**
     * 商家列表
     * @return result 动态查询及分页
     */
    @GetMapping("sellerlist")
    public ModelAndView sellerList(TableResult result) {
        TableResult tableResult = sellerService.sellerList(result);
        ModelAndView modelAndView = new ModelAndView("adminmall/seller/sellerlist");
        modelAndView.addObject(tableResult);
        return modelAndView;
    }
    
    @PostMapping("setsupport/{uid}")
    @ResponseBody
    public Result setSupport(@PathVariable Integer uid) {
    	Result result = sellerService.setsupport(uid);
    	return result;
    }
}
