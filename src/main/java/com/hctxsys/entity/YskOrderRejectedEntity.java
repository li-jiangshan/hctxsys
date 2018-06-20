package com.hctxsys.entity;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name = "ysk_order_rejected", schema = "hctx_db", catalog = "")
public class YskOrderRejectedEntity {
	
	//退款订单id
    private Integer rejectedId;
	//用户id
    private Integer userId;
	//退款编号
    private String rejectedNo;
    //支付订单编号
    private String payOrderNo;
    //支付方式
    private String payOrderCode;
    //支付订单金额
    private BigDecimal payOrderPrice;
	//退款详细id
    private Integer orderDetailId;
	//退款金额
    private BigDecimal rejectedPrice;
	//退货数量
    private String rejectedNum;
	//退货原因
    private String rejectedReason;
	//凭证
    private String rejectedImg;
	//凭证
    private String rejectedImg2;
	//凭证
    private String rejectedImg3;
	//收件地址
    private String receivingAddress;
	//收件人
    private String rejectedName;
	//收件人电话
    private String rejectedPhone;
	//物流单号
    private String logisticsNo;
	//联系电话
    private String logisticsMobile;
	//物流图片
    private String logisticsImg;
	//拒绝理由
    private String orderStatusReason;
	//退款时间
    private Integer rejectedTime;
	//创建时间
    private Integer createTime;
	//状态 0审批中 1通过 2拒绝 3已退货 4已退款 5已取消
    private Integer orderStatus;
    
    private YskOrderDetailEntity yskOrderDetailEntity;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_detail_id",referencedColumnName = "id",updatable = false,insertable = false)
    public YskOrderDetailEntity getYskOrderDetailEntity() {
		return yskOrderDetailEntity;
	}

	public void setYskOrderDetailEntity(YskOrderDetailEntity yskOrderDetailEntity) {
		this.yskOrderDetailEntity = yskOrderDetailEntity;
	}
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rejected_id")
    public Integer getRejectedId() {
		return rejectedId;
	}

	public void setRejectedId(Integer rejectedId) {
		this.rejectedId = rejectedId;
	}

    @Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    @Column(name = "rejected_no")
	public String getRejectedNo() {
		return rejectedNo;
	}

	public void setRejectedNo(String rejectedNo) {
		this.rejectedNo = rejectedNo;
	}

    @Column(name = "order_detail_id")
	public Integer getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

    @Column(name = "rejected_reason")
	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

    @Column(name = "rejected_img")
	public String getRejectedImg() {
		return rejectedImg;
	}

	public void setRejectedImg(String rejectedImg) {
		this.rejectedImg = rejectedImg;
	}

    @Column(name = "rejected_img2")
	public String getRejectedImg2() {
		return rejectedImg2;
	}

	public void setRejectedImg2(String rejectedImg2) {
		this.rejectedImg2 = rejectedImg2;
	}

    @Column(name = "rejected_img3")
    public String getRejectedImg3() {
		return rejectedImg3;
	}

	public void setRejectedImg3(String rejectedImg3) {
		this.rejectedImg3 = rejectedImg3;
	}

	@Column(name = "receiving_address")
	public String getReceivingAddress() {
		return receivingAddress;
	}

	public void setReceivingAddress(String receivingAddress) {
		this.receivingAddress = receivingAddress;
	}

    @Column(name = "rejected_name")
	public String getRejectedName() {
		return rejectedName;
	}

	public void setRejectedName(String rejectedName) {
		this.rejectedName = rejectedName;
	}

    @Column(name = "rejected_phone")
	public String getRejectedPhone() {
		return rejectedPhone;
	}

	public void setRejectedPhone(String rejectedPhone) {
		this.rejectedPhone = rejectedPhone;
	}

    @Column(name = "logistics_no")
	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

    @Column(name = "logistics_mobile")
	public String getLogisticsMobile() {
		return logisticsMobile;
	}

	public void setLogisticsMobile(String logisticsMobile) {
		this.logisticsMobile = logisticsMobile;
	}

    @Column(name = "logistics_img")
	public String getLogisticsImg() {
		return logisticsImg;
	}

	public void setLogisticsImg(String logisticsImg) {
		this.logisticsImg = logisticsImg;
	}

    @Column(name = "order_status_reason")
	public String getOrderStatusReason() {
		return orderStatusReason;
	}

	public void setOrderStatusReason(String orderStatusReason) {
		this.orderStatusReason = orderStatusReason;
	}
	
	@Column(name = "rejected_time")
	public Integer getRejectedTime() {
		return rejectedTime;
	}

	public void setRejectedTime(Integer rejectedTime) {
		this.rejectedTime = rejectedTime;
	}
	
    @Column(name = "create_time")
	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

    @Column(name = "order_status")
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

    @Column(name = "pay_order_no")
	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	
    @Column(name = "pay_order_code")
    public String getPayOrderCode() {
		return payOrderCode;
	}

	public void setPayOrderCode(String payOrderCode) {
		this.payOrderCode = payOrderCode;
	}
	
    @Column(name = "pay_order_price")
	public BigDecimal getPayOrderPrice() {
		return payOrderPrice;
	}

	public void setPayOrderPrice(BigDecimal payOrderPrice) {
		this.payOrderPrice = payOrderPrice;
	}

	@Column(name = "rejected_price")
	public BigDecimal getRejectedPrice() {
		return rejectedPrice;
	}

	public void setRejectedPrice(BigDecimal rejectedPrice) {
		this.rejectedPrice = rejectedPrice;
	}

	@Column(name = "rejected_num")
	public String getRejectedNum() {
		return rejectedNum;
	}

	public void setRejectedNum(String rejectedNum) {
		this.rejectedNum = rejectedNum;
	}
	
	
}
