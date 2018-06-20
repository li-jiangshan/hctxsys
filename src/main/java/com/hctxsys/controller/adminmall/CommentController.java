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
import com.hctxsys.entity.YskNewsTitleEntity;
import com.hctxsys.service.CommentServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;

@Controller
@RequestMapping("Adminmall")
public class CommentController {
	@Autowired
	CommentServiceImpl commentServiceImpl;

	@GetMapping("Comment/sellercomment")
	public ModelAndView getSellerComment(TableResult result) {
		TableResult tableResult = commentServiceImpl.indexTable(result);
		ModelAndView view = new ModelAndView("/adminmall/comment/sellercomment");
		view.addObject("tableResult", tableResult);
		return view;

	}



	/**
	 * 删除分类
	 * 
	 * @param id
	 */
	@RequestMapping(path = "Comment/deletebrand", method = RequestMethod.GET)
	@ResponseBody
	public Result deleteBrand(@RequestParam String id) {
		int i = commentServiceImpl.deleteBrand(Integer.valueOf(id));
		if (i == 1) {
			return new Result(1, "删除成功，不可恢复", "/Adminmall/Comment/sellercomment", "");
		}
		return new Result(0, "删除失败", "/Adminmall/Comment/sellercomment", "");
	}
}
