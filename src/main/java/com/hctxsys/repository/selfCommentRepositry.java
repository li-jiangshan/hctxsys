package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskGoodCommentEntity;

public interface selfCommentRepositry extends JpaRepository<YskGoodCommentEntity, Integer>,JpaSpecificationExecutor<YskGoodCommentEntity>{

}
