package com.hctxsys.controller.api;

import com.hctxsys.entity.YskUserAdviceEntity;
import com.hctxsys.service.api.ApiFeedbackServiceImpl;
import com.hctxsys.vo.api.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/feedback")
public class ApiFeedbackController {
    @Autowired
    private ApiFeedbackServiceImpl feedbackService;

    /**
     * 用户反馈提交
     * @param advice
     * @return
     */
    @PostMapping("submit")
    public JsonResult feedback(@RequestBody YskUserAdviceEntity advice) {
        if(advice.getTitle().equals("")) {
            return new JsonResult(0,"标题不能为空");
        }if(advice.getContent().equals("")) {
            return new JsonResult(0,"内容不能为空");
        }
        return feedbackService.feedback(advice);

    }
}
