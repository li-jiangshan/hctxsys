package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskMenuEntity;

public interface MenuRepository extends JpaRepository<YskMenuEntity, Integer>, JpaSpecificationExecutor<YskMenuEntity> {

	YskMenuEntity findById(int id);
	
	public List<YskMenuEntity> findByIdAndPid(int id, int pid);
	
	public List<YskMenuEntity> findByStatusAndLevel(byte status, int level);
	
	public List<YskMenuEntity> findByStatusAndLevelAndPid(byte status, int level,int pid,Sort sort);
	
	public List<YskMenuEntity> findByStatusAndLevelAndGid(byte status, int level,int gid,Sort sort);
	
	public List<YskMenuEntity> findByLevelAndGid(int level,int gid);
	
	YskMenuEntity findByIdAndStatusAndLevel(int id,byte status, int level,Sort sort);
	
	YskMenuEntity findByIdAndStatusAndLevelAndPid(int id,byte status, int level,int pid,Sort sort);
	
	YskMenuEntity findByIdAndStatusAndLevelAndGid(int id,byte status, int level,int gid,Sort sort);
	
	public List<YskMenuEntity> findByIdAndLevelAndGid(int id,int level,int gid);
	
	public List<YskMenuEntity> findByStatusAndLevel(byte status, int level,Sort sort);

}
