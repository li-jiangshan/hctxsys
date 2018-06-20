package com.hctxsys.repository;

import com.hctxsys.entity.YskCarTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarTypeRepository extends JpaRepository<YskCarTypeEntity,Integer> {
}
