package com.hctxsys.repository;

import com.hctxsys.entity.YskOrderEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<YskOrderEntity,Integer>,JpaSpecificationExecutor<YskOrderEntity> {
    Page<YskOrderEntity> findAllByOrderStatusAndUserIdOrderByOrderIdDesc(Byte id,Integer userID, Pageable pageable);
    Page<YskOrderEntity> findAllByUserIdOrderByOrderIdDesc(Integer userID,Pageable pageable);
    YskOrderEntity findByOrderId(int orderId);
    List<YskOrderEntity> findAllByOrderNo(String orderNo);
//    @Query(value = "SELECT order_id FROM ysk_order WHERE order_id in ?1",nativeQuery=true)
    List<YskOrderEntity> findByOrderNoIn(List<String> orderNo);
    
    List<YskOrderEntity> findByUserIdAndOrderStatusAndOrderNoIn(Integer userId, byte orderStatus,List<String> orderNos);
    
    List<YskOrderEntity> findByUserIdAndOrderStatus(Integer userId, byte orderStatus);
    
	public YskOrderEntity findByOrderNo(String orderNo);
	
	@Query(value = "SELECT * FROM ysk_order o WHERE o.order_status = ?1 AND o.money_to_seller = ?2 AND o.order_ship_time < ?3",nativeQuery=true)
	public List<YskOrderEntity> findByOrderStatusAndMoneyToSellerAndOrderShipTimeLessThan(byte orderStatus, byte moneyToSeller, Integer searchDate);
}
