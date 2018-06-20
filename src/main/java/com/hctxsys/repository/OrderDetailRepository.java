package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hctxsys.entity.YskOrderDetailEntity;

public interface OrderDetailRepository extends JpaRepository<YskOrderDetailEntity,Integer>,JpaSpecificationExecutor<YskOrderDetailEntity> {
	
//    List<YskOrderDetailEntity> findgoodById(Integer id);
	@Query(value = "select o from YskOrderDetailEntity o where o.orderId = :orderId")
	List<YskOrderDetailEntity> findgoodById(@Param("orderId")Integer orderId);
	
	YskOrderDetailEntity findByOrderIdAndIsComment(Integer orderId,Byte isComment);
	List<YskOrderDetailEntity> findAllByOrderId(Integer orderId);
	YskOrderDetailEntity findByOrderIdAndIsCommentAndGoodId(Integer orderId,Byte isComment,Integer goodid);

    List<YskOrderDetailEntity> findByOrderId(int orderId);

    List<YskOrderDetailEntity> findByIsCommentAndIsSendAndOrderIdIn(Byte isComment, Byte isSend, List<Integer> orderIds);
}
