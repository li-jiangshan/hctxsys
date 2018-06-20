package com.hctxsys.service.api;

import com.hctxsys.entity.YskUserAdviceEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.repository.UseradviceRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.vo.api.JsonResult;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("apiFeedbackService")
public class ApiFeedbackServiceImpl {
    @Autowired
    private UseradviceRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public JsonResult feedback(YskUserAdviceEntity advice) {
        try {
            int userid = advice.getUserid();
            YskUserEntity user = userRepository.findById(userid).get();
            advice.setAccount(user.getAccount());
            advice.setMobile(user.getMobile());
            if(!StringUtils.isEmpty(user.getUsername())) {
            	advice.setUsername(user.getUsername());
            }
            advice.setCreateTime(DateUtils.getTime());
            repository.saveAndFlush(advice);
            return new JsonResult(1,"提交成功");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"提交失败");
        }
    }
}
