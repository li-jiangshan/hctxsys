package com.hctxsys.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ysk_order", schema = "hctx_db", catalog = "")
public class YskOrderEntity {
	private int orderId;
	private String orderNo;
	private byte orderStatus;
	private int userId;
	private String userName;
	private String userCountry;
	private String userProvince;
	private String userCity;
	private String userDistrict;
	private String userAddress;
	private String userMobile;
	private String orderTransportCode;
	private String orderTransportName;
	private String orderPayCode;
	private String orderPayName;
	private BigDecimal orderShipPrice;
	private BigDecimal orderUseMoney;
	private BigDecimal orderUseCoupon;
	private int orderUseIntegral;
	private BigDecimal orderTotalPrice;
	private int orderCreateTime;
	private Integer orderShipTime;
	private Integer orderConfirmTime;
	private int orderPayTime;
	private BigDecimal orderDiscount;
	private String orderUserNote;
	private String orderAdminNote;
	private Byte orderIsDistribut;
	private byte orderIsDelete;
	private int sellerId;
	private byte moneyToSeller;
	private List<YskOrderDetailEntity> detailEntity;
	private YskUserEntity userEntity;
	private int addressID;
    @Transient
	public int getAddressID() {
		return addressID;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
	public List<YskOrderDetailEntity> getDetailEntity() {
		return detailEntity;
	}

	public void setDetailEntity(List<YskOrderDetailEntity> detailEntity) {
		this.detailEntity = detailEntity;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userid",insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public YskUserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(YskUserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Basic
	@Column(name = "order_no")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Basic
	@Column(name = "order_status")
	public byte getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(byte orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Basic
	@Column(name = "user_id")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Basic
	@Column(name = "user_country")
	public String getUserCountry() {
		if (userCountry == null)
			return "0";
		return userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	@Basic
	@Column(name = "user_province")
	public String getUserProvince() {
		return userProvince;
	}

	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}

	@Basic
	@Column(name = "user_city")
	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	@Basic
	@Column(name = "user_district")
	public String getUserDistrict() {
		return userDistrict;
	}

	public void setUserDistrict(String userDistrict) {
		this.userDistrict = userDistrict;
	}

	@Basic
	@Column(name = "user_address")
	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	@Basic
	@Column(name = "user_mobile")
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	@Basic
	@Column(name = "order_transport_code")
	public String getOrderTransportCode() {
		if (orderTransportCode == null)
			return "";
		return orderTransportCode;
	}

	public void setOrderTransportCode(String orderTransportCode) {
		this.orderTransportCode = orderTransportCode;
	}

	@Basic
	@Column(name = "order_transport_name")
	public String getOrderTransportName() {
		if (orderTransportName == null)
			return "";
		return orderTransportName;
	}

	public void setOrderTransportName(String orderTransportName) {
		this.orderTransportName = orderTransportName;
	}

	@Basic
	@Column(name = "order_pay_code")
	public String getOrderPayCode() {
		if (orderPayCode == null)
			return "";
		return orderPayCode;
	}

	public void setOrderPayCode(String orderPayCode) {
		this.orderPayCode = orderPayCode;
	}

	@Basic
	@Column(name = "order_pay_name")
	public String getOrderPayName() {
		if (orderPayName == null)
			return "";
		return orderPayName;
	}

	public void setOrderPayName(String orderPayName) {
		this.orderPayName = orderPayName;
	}

	@Basic
	@Column(name = "order_ship_price")
	public BigDecimal getOrderShipPrice() {
		if (orderShipPrice == null)
			return BigDecimal.valueOf(0);
		return orderShipPrice;
	}

	public void setOrderShipPrice(BigDecimal orderShipPrice) {
		this.orderShipPrice = orderShipPrice;
	}

	@Basic
	@Column(name = "order_use_money")
	public BigDecimal getOrderUseMoney() {
		if (orderUseMoney == null)
			return BigDecimal.valueOf(0);
		return orderUseMoney;
	}

	public void setOrderUseMoney(BigDecimal orderUseMoney) {
		this.orderUseMoney = orderUseMoney;
	}

	@Basic
	@Column(name = "order_use_coupon")
	public BigDecimal getOrderUseCoupon() {
		if (orderUseCoupon == null)
			return BigDecimal.valueOf(0);
		return orderUseCoupon;
	}

	public void setOrderUseCoupon(BigDecimal orderUseCoupon) {
		this.orderUseCoupon = orderUseCoupon;
	}

	@Basic
	@Column(name = "order_use_integral")
	public int getOrderUseIntegral() {
		return orderUseIntegral;
	}

	public void setOrderUseIntegral(int orderUseIntegral) {
		this.orderUseIntegral = orderUseIntegral;
	}

	@Basic
	@Column(name = "order_total_price")
	public BigDecimal getOrderTotalPrice() {
		if(orderTotalPrice==null) return BigDecimal.valueOf(0);
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	@Basic
	@Column(name = "order_create_time")
	public int getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(int orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	@Basic
	@Column(name = "order_ship_time")
	public Integer getOrderShipTime() {
		if (orderShipTime == null)
			return 0;
		return orderShipTime;
	}

	public void setOrderShipTime(Integer orderShipTime) {
		this.orderShipTime = orderShipTime;
	}

	@Basic
	@Column(name = "order_confirm_time")
	public Integer getOrderConfirmTime() {
		if (orderConfirmTime == null)
			return 0;
		return orderConfirmTime;
	}

	public void setOrderConfirmTime(Integer orderConfirmTime) {
		this.orderConfirmTime = orderConfirmTime;
	}

	@Basic
	@Column(name = "order_pay_time")
	public int getOrderPayTime() {
		return orderPayTime;
	}

	public void setOrderPayTime(int orderPayTime) {
		this.orderPayTime = orderPayTime;
	}

	@Basic
	@Column(name = "order_discount")
	public BigDecimal getOrderDiscount() {
		if (orderDiscount == null)
			return BigDecimal.valueOf(0);
		return orderDiscount;
	}

	public void setOrderDiscount(BigDecimal orderDiscount) {
		this.orderDiscount = orderDiscount;
	}

	@Basic
	@Column(name = "order_user_note")
	public String getOrderUserNote() {
		if (orderUserNote == null)
			return "";
		return orderUserNote;
	}

	public void setOrderUserNote(String orderUserNote) {
		this.orderUserNote = orderUserNote;
	}

	@Basic
	@Column(name = "order_admin_note")
	public String getOrderAdminNote() {
		if (orderAdminNote == null)
			return "";
		return orderAdminNote;
	}

	public void setOrderAdminNote(String orderAdminNote) {
		this.orderAdminNote = orderAdminNote;
	}

	@Basic
	@Column(name = "order_is_distribut")
	public Byte getOrderIsDistribut() {
		if (orderIsDistribut == null)
			return 0;
		return orderIsDistribut;
	}

	public void setOrderIsDistribut(Byte orderIsDistribut) {
		this.orderIsDistribut = orderIsDistribut;
	}

	@Basic
	@Column(name = "order_is_delete")
	public byte getOrderIsDelete() {
		return orderIsDelete;
	}

	public void setOrderIsDelete(byte orderIsDelete) {
		this.orderIsDelete = orderIsDelete;
	}

	@Basic
	@Column(name = "seller_id")
	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	@Basic
	@Column(name = "money_to_seller")
	public byte getMoneyToSeller() {
		return moneyToSeller;
	}

	public void setMoneyToSeller(byte moneyToSeller) {
		this.moneyToSeller = moneyToSeller;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		YskOrderEntity that = (YskOrderEntity) o;
		return orderId == that.orderId && orderStatus == that.orderStatus && userId == that.userId
				&& orderUseIntegral == that.orderUseIntegral && orderCreateTime == that.orderCreateTime
				&& orderPayTime == that.orderPayTime && orderIsDelete == that.orderIsDelete && sellerId == that.sellerId
				&& moneyToSeller == that.moneyToSeller && Objects.equals(orderNo, that.orderNo)
				&& Objects.equals(userName, that.userName) && Objects.equals(userCountry, that.userCountry)
				&& Objects.equals(userProvince, that.userProvince) && Objects.equals(userCity, that.userCity)
				&& Objects.equals(userDistrict, that.userDistrict) && Objects.equals(userAddress, that.userAddress)
				&& Objects.equals(userMobile, that.userMobile)
				&& Objects.equals(orderTransportCode, that.orderTransportCode)
				&& Objects.equals(orderTransportName, that.orderTransportName)
				&& Objects.equals(orderPayCode, that.orderPayCode) && Objects.equals(orderPayName, that.orderPayName)
				&& Objects.equals(orderShipPrice, that.orderShipPrice)
				&& Objects.equals(orderUseMoney, that.orderUseMoney)
				&& Objects.equals(orderUseCoupon, that.orderUseCoupon)
				&& Objects.equals(orderTotalPrice, that.orderTotalPrice)
				&& Objects.equals(orderShipTime, that.orderShipTime)
				&& Objects.equals(orderConfirmTime, that.orderConfirmTime)
				&& Objects.equals(orderDiscount, that.orderDiscount)
				&& Objects.equals(orderUserNote, that.orderUserNote)
				&& Objects.equals(orderAdminNote, that.orderAdminNote)
				&& Objects.equals(orderIsDistribut, that.orderIsDistribut);
	}

	@Override
	public int hashCode() {

		return Objects.hash(orderId, orderNo, orderStatus, userId, userName, userCountry, userProvince, userCity,
				userDistrict, userAddress, userMobile, orderTransportCode, orderTransportName, orderPayCode,
				orderPayName, orderShipPrice, orderUseMoney, orderUseCoupon, orderUseIntegral, orderTotalPrice,
				orderCreateTime, orderShipTime, orderConfirmTime, orderPayTime, orderDiscount, orderUserNote,
				orderAdminNote, orderIsDistribut, orderIsDelete, sellerId, moneyToSeller);
	}
}
