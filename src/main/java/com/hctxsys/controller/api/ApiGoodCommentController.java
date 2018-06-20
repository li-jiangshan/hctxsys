package com.hctxsys.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.service.api.ApiGoodCommentServiceImpl;
import com.hctxsys.vo.api.ApiCommentVo;
import com.hctxsys.vo.api.ApiGetCommentVo;
import com.hctxsys.vo.api.ApiGoodCommentVo;
import com.hctxsys.vo.api.ApiMyCommentVo;
import com.hctxsys.vo.api.ApiOrderCommentListVo;
import com.hctxsys.vo.api.ApiOrderCommentVo;
import com.hctxsys.vo.api.JsonResult;

@Controller
@RequestMapping("/api/comment")
public class ApiGoodCommentController {
	@Autowired
	private ApiGoodCommentServiceImpl goodCommentService;
	
	/**根据商品id查找评价
	 * @param goodid 商品id
	 * @param page 页码
	 * @param pageSize 每页显示几个数据
	 * @return 分完页的数据
	 */
	@RequestMapping(value="/getComment",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult getGoodComment(@RequestBody ApiGetCommentVo vo) {
		
		JsonResult returnVo = new JsonResult();
		
		ApiGoodCommentVo goodCommentVo = new ApiGoodCommentVo();
		if(vo.getGoodId()<1) {
			returnVo.setCode(0);
			returnVo.setMessage("缺少参数商品id");
			return returnVo;
		}
		if(vo.getPage()<0) {
			returnVo.setCode(0);
			returnVo.setMessage("参数page不能小于0");
			return returnVo;
		}
		if(vo.getPageSize()==0) {
			returnVo.setCode(0);
			returnVo.setMessage("缺少参数pageSize");
			return returnVo;
		}
		Page<YskGoodCommentEntity> pageList = goodCommentService.findByGoodId(vo.getGoodId(), vo.getPage(), vo.getPageSize());
		if(pageList.getContent().size()!=0) {
			returnVo.setCode(1);
			returnVo.setMessage("查找出商品id为"+vo.getGoodId()+"的评价");
			goodCommentVo.setPageList(pageList.getContent());
			goodCommentVo.setPageSize(pageList.getSize());
			goodCommentVo.setPage(vo.getPage());
			goodCommentVo.setTotalPages(pageList.getTotalPages());
			returnVo.setData(goodCommentVo);
			return returnVo;
		}
		returnVo.setCode(1);
		returnVo.setMessage("该商品没有评论");
		return returnVo;
	}
	
	/**保存商品的评价
	 * @param goodComment 商品实体
	 * @return 结果json数据
	 */
	@RequestMapping(value="/saveComment",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult saveGoodComment(@RequestBody ApiCommentVo vo) {
		JsonResult result = goodCommentService.saveGoodComment(vo);
		return result;
	}
	
	/**根据用户id查找评论
	 * @param uid 用户id
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/myComment",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult getGoodCommentByUid(@RequestBody ApiMyCommentVo vo) {
		ApiOrderCommentListVo commentListVo = new ApiOrderCommentListVo();
		JsonResult returnVo = new JsonResult();
		if(vo.getUid()<1) {
			returnVo.setCode(0);
			returnVo.setMessage("缺少参数用户id");
			return returnVo;
		}
		List<ApiOrderCommentVo> list = goodCommentService.getGoodCommentByUid(vo.getUid(),vo.getIsComment());
		if(list.size()!=0) {
			commentListVo.setOrderCommentList(list);
			returnVo.setCode(1);
			returnVo.setMessage("查找出用户id为"+vo.getUid()+"的评价");
			returnVo.setData(commentListVo);
			return returnVo;
		}
		returnVo.setCode(1);
		returnVo.setMessage("该用户没有任何评论");
		ApiOrderCommentListVo orderCommentListVo = new ApiOrderCommentListVo();
		orderCommentListVo.setOrderCommentList(new ArrayList<ApiOrderCommentVo>());
		returnVo.setData(orderCommentListVo);
		return returnVo;
	}
}
