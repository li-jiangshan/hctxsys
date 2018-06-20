package com.hctxsys.vo.api;

import java.math.BigDecimal;
import java.util.List;

public class ApiOrderVo  {
	
//    private List<YskOrderEntity> orderList;
    
//    private YskOrderEntity order;

    //用户id
    private Integer userId;
    //商家名称
    private String shopName;
    //订单号
    private String orderNo;
    //订单状态
    private Integer orderStatus;
    //订单总价
    private BigDecimal orderTotalPrice;
    //地址id
    private Integer addressID;
    //商品详细信息
    private List<ApiOrderDetailVo> orderDetailList;

//    public YskOrderEntity getOrder() {
//        return order;
//    }
//
//    public void setOrder(YskOrderEntity order) {
//        this.order = order;
//    }
//
//    public List<YskOrderEntity> getOrderList() {
//        return orderList;
//    }
//
//    public void setOrderList(List<YskOrderEntity> orderList) {
//        this.orderList = orderList;
//    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	
	public Integer getAddressID() {
		return addressID;
	}

	public void setAddressID(Integer addressID) {
		this.addressID = addressID;
	}

	public List<ApiOrderDetailVo> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<ApiOrderDetailVo> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

}
