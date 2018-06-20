package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskNewsTitleEntity;

public interface NewTitleRepositry extends JpaRepository<YskNewsTitleEntity, Integer> ,JpaSpecificationExecutor<YskNewsTitleEntity>{
	public YskNewsTitleEntity findById(int id);
}
