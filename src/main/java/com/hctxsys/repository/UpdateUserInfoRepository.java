package com.hctxsys.repository;

import com.hctxsys.entity.YskUpdateUserinfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UpdateUserInfoRepository extends JpaRepository<YskUpdateUserinfoEntity,Integer>,JpaSpecificationExecutor<YskUpdateUserinfoEntity> {
    @Query("select ui,u.userType,u.username,u.mobile from  YskUserEntity u left join YskUpdateUserinfoEntity ui on u.userid=ui.uid where ui.id=?1")
    Object[] selectDetail(Integer id);
    @Query("select i from YskUpdateUserinfoEntity i where i.uid=?1 and i.status=?2 order by i.id desc")
    List<YskUpdateUserinfoEntity> selectAllByStatus(Integer uid,Integer status);
}
