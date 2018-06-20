package com.hctxsys.service;

import com.hctxsys.entity.YskUserLevelEntity;
import com.hctxsys.repository.UserLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userLevelService")
public class UserLevelServiceImpl {
    @Autowired
    UserLevelRepository levelRepository;

    /**
     * 查询用户等级表
     * @return
     */
    public List<YskUserLevelEntity> getUserLevel() {
        return levelRepository.findAll();
    }

    /**
     * 查询等级标识大于等于level的用户等级
     * @param level 等级标识 4以上代表代理商
     * @return 返回集合
     */
    public List<YskUserLevelEntity> getArea(Byte level){
        return levelRepository.findByLevelGreaterThanEqual(level);
    }
}
