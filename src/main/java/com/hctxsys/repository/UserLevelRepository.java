package com.hctxsys.repository;

import com.hctxsys.entity.YskUserLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLevelRepository extends JpaRepository<YskUserLevelEntity,Integer> {
	
    public List<YskUserLevelEntity> findByLevelGreaterThanEqual(Byte level);
    
	public List<YskUserLevelEntity> findByLevelIn(List<Byte> levels);  //通过等级查询
}
