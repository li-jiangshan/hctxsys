package com.hctxsys.controller.adminmall;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.service.SelfCommentServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;

@Controller
@RequestMapping("Adminmall")
public class SelfCommentController {
@Autowired
SelfCommentServiceImpl selfCommentServiceImpl;
	@GetMapping("Comment/index")
	public ModelAndView getSellerComment(TableResult result) {
		TableResult tableResult = selfCommentServiceImpl.indexTable(result);
		ModelAndView view = new ModelAndView("/adminmall/comment/index");
		view.addObject("tableResult", tableResult);
		return view;

	}
	
//	/**
//	 * 新增、更新
//	 * 
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(path = "/update", method = RequestMethod.POST)
//	@ResponseBody
//	public Result updateNew(YskGoodCommentEntity gc, HttpSession httpSession) {
//		Integer attribute = (Integer) httpSession.getAttribute("sellerId");
//		int sellerId = 0;
//		if (attribute != null) {
//			sellerId = Integer.valueOf(attribute);
//		}
//		if (gc.getGoodName() == null || gc.getGoodName() == "") {
//			return new Result(0, "标题名称不能为空", "/Admin/new/index", "");
//		}
//		// New.setStatus((byte) 1);
//		// New.setSellerId(sellerId);
//		YskGoodCommentEntity entity = selfCommentServiceImpl.updateNew(gc);
//		if (entity != null) {
//			return new Result(1, "保存成功", "/Adminmall/comment/index", "");
//		}
//		return new Result(0, "保存失败", "/Adminmall/comment/index", "");
//	}

	/**
	 * 删除分类
	 * 
	 * @param id
	 */
	@RequestMapping(path = "comment/deleteSelfComment", method = RequestMethod.GET)
	@ResponseBody
	public Result deleteBrand(@RequestParam String id) {
		int i = selfCommentServiceImpl.deleteSelfComment(Integer.valueOf(id));
		if (i == 1) {
			return new Result(1, "删除成功，不可恢复", "/Adminmall/Comment/index", "");
		}
		return new Result(0, "删除失败", "/Adminmall/Comment/index", "");
	}
}
