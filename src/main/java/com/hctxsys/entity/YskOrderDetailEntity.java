package com.hctxsys.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ysk_order_detail", schema = "hctx_db")
public class YskOrderDetailEntity {
    private Integer id;
    private Integer orderId;
    private Integer goodId;
    private Integer priceId;
    private String goodName;
    private String goodNo;
    private String goodCoverImg;
    private short goodNum;
    private BigDecimal marketPrice;
    private BigDecimal goodPrice;
    private BigDecimal costPrice;
    private BigDecimal userGoodPrice;
    private Integer giveIntegral;
    private String attrValue;
    private String attrText;
    private Byte isComment;
    private Byte isSend;
    private String shipNo;
    private Integer sellerId;
    private YskShopInfoEntity shopInfoEntity;
    @OneToOne
    @JoinColumn(name = "seller_id",referencedColumnName = "uid",insertable = false,updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public YskShopInfoEntity getShopInfoEntity() {
        return shopInfoEntity;
    }

    public void setShopInfoEntity(YskShopInfoEntity shopInfoEntity) {
        this.shopInfoEntity = shopInfoEntity;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order_id")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "good_id")
    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }
    
    @Basic
    @Column(name = "price_id")
    public Integer getPriceId() {
		return priceId;
	}

	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

	@Basic
    @Column(name = "good_name")
    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    @Basic
    @Column(name = "good_no")
    public String getGoodNo() {
        return goodNo;
    }

    public void setGoodNo(String goodNo) {
        this.goodNo = goodNo;
    }

    @Basic
    @Column(name = "good_cover_img")
    public String getGoodCoverImg() {
        return goodCoverImg;
    }

    public void setGoodCoverImg(String goodCoverImg) {
        this.goodCoverImg = goodCoverImg;
    }

    @Basic
    @Column(name = "good_num")
    public short getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(short goodNum) {
        this.goodNum = goodNum;
    }

    @Basic
    @Column(name = "market_price")
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    @Basic
    @Column(name = "good_price")
    public BigDecimal getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(BigDecimal goodPrice) {
        this.goodPrice = goodPrice;
    }

    @Basic
    @Column(name = "cost_price")
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    @Basic
    @Column(name = "user_good_price")
    public BigDecimal getUserGoodPrice() {
        if(userGoodPrice==null) return BigDecimal.valueOf(0);
        return userGoodPrice;
    }

    public void setUserGoodPrice(BigDecimal userGoodPrice) {
        this.userGoodPrice = userGoodPrice;
    }

    @Basic
    @Column(name = "give_integral")
    public Integer getGiveIntegral() {
        return giveIntegral;
    }

    public void setGiveIntegral(Integer giveIntegral) {
        this.giveIntegral = giveIntegral;
    }

    @Basic
    @Column(name = "attr_value")
    public String getAttrValue() {
        if(attrValue==null) return "";
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    @Basic
    @Column(name = "attr_text")
    public String getAttrText() {
        if(attrText==null) return "";
        return attrText;
    }

    public void setAttrText(String attrText) {
        this.attrText = attrText;
    }

    @Basic
    @Column(name = "is_comment")
    public Byte getIsComment() {
        if(isComment==null) return 0;
        return isComment;
    }

    public void setIsComment(Byte isComment) {
        this.isComment = isComment;
    }

    @Basic
    @Column(name = "is_send")
    public Byte getIsSend() {
        if(isSend==null) return 0;
        return isSend;
    }

    public void setIsSend(Byte isSend) {
        this.isSend = isSend;
    }

    @Basic
    @Column(name = "ship_no")
    public String getShipNo() {
        if(shipNo==null) return "0";
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    @Basic
    @Column(name = "seller_id")
    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskOrderDetailEntity that = (YskOrderDetailEntity) o;
        return id == that.id &&
                orderId == that.orderId &&
                goodId == that.goodId &&
                goodNum == that.goodNum &&
                giveIntegral == that.giveIntegral &&
                sellerId == that.sellerId &&
                Objects.equals(goodName, that.goodName) &&
                Objects.equals(goodNo, that.goodNo) &&
                Objects.equals(goodCoverImg, that.goodCoverImg) &&
                Objects.equals(marketPrice, that.marketPrice) &&
                Objects.equals(goodPrice, that.goodPrice) &&
                Objects.equals(costPrice, that.costPrice) &&
                Objects.equals(userGoodPrice, that.userGoodPrice) &&
                Objects.equals(attrValue, that.attrValue) &&
                Objects.equals(attrText, that.attrText) &&
                Objects.equals(isComment, that.isComment) &&
                Objects.equals(isSend, that.isSend) &&
                Objects.equals(shipNo, that.shipNo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderId, goodId, goodName, goodNo, goodCoverImg, goodNum, marketPrice, goodPrice, costPrice, userGoodPrice, giveIntegral, attrValue, attrText, isComment, isSend, shipNo, sellerId);
    }
}
