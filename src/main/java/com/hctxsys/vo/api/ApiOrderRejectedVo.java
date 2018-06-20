package com.hctxsys.vo.api;

public class ApiOrderRejectedVo  {

	//退款订单id
    private Integer rejectedId;
    //用户id
    private Integer userId;
    //商品详细id
    private Integer orderDetailId;
    //退货原因
    private String rejectedReason;
    //退货凭证图片
    private String rejectedImg;
	//物流单号
    private String logisticsNo;
	//联系电话
    private String logisticsMobile;
	//物流图片
    private String logisticsImg;
    
	public Integer getRejectedId() {
		return rejectedId;
	}

	public void setRejectedId(Integer rejectedId) {
		this.rejectedId = rejectedId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public String getRejectedImg() {
		return rejectedImg;
	}

	public void setRejectedImg(String rejectedImg) {
		this.rejectedImg = rejectedImg;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getLogisticsMobile() {
		return logisticsMobile;
	}

	public void setLogisticsMobile(String logisticsMobile) {
		this.logisticsMobile = logisticsMobile;
	}

	public String getLogisticsImg() {
		return logisticsImg;
	}

	public void setLogisticsImg(String logisticsImg) {
		this.logisticsImg = logisticsImg;
	}
}
