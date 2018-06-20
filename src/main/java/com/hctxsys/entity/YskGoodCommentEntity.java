package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_good_comment", schema = "hctx_db", catalog = "")
public class YskGoodCommentEntity {
	private int id;
	private int uid;
	private String username;
	private String mobile;
	private int goodId;
	private String goodName;
	private String goodItem;
	private String goodCoverImg;
	private int starAbility;
	private int starAttitude;
	private int starPrice;
	private String content;
	private int createTime;
	private int orderId;
	private int level;
	private int sellerId;

	private YskShopInfoEntity shopInfoEntity;
	private YskUserEntity userEntity;

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "seller_Id", referencedColumnName = "userid", updatable = false, insertable = false)
	public YskUserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(YskUserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "seller_Id", referencedColumnName = "uid", updatable = false, insertable = false)

	public YskShopInfoEntity getShopInfoEntity() {
		return shopInfoEntity;
	}

	public void setShopInfoEntity(YskShopInfoEntity shopInfoEntity) {
		this.shopInfoEntity = shopInfoEntity;
	}

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "uid")
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@Basic
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Basic
	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Basic
	@Column(name = "good_id")
	public int getGoodId() {
		return goodId;
	}

	public void setGoodId(int goodId) {
		this.goodId = goodId;
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
	@Column(name = "good_item")
	public String getGoodItem() {
		return goodItem;
	}

	public void setGoodItem(String goodItem) {
		this.goodItem = goodItem;
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
	@Column(name = "star_ability")
	public int getStarAbility() {
		return starAbility;
	}

	public void setStarAbility(int starAbility) {
		this.starAbility = starAbility;
	}

	@Basic
	@Column(name = "star_attitude")
	public int getStarAttitude() {
		return starAttitude;
	}

	public void setStarAttitude(int starAttitude) {
		this.starAttitude = starAttitude;
	}

	@Basic
	@Column(name = "star_price")
	public int getStarPrice() {
		return starPrice;
	}

	public void setStarPrice(int starPrice) {
		this.starPrice = starPrice;
	}

	@Basic
	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Basic
	@Column(name = "create_time")
	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	@Basic
	@Column(name = "order_id")
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Basic
	@Column(name = "level")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Basic
	@Column(name = "seller_id")
	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		YskGoodCommentEntity that = (YskGoodCommentEntity) o;
		return id == that.id && uid == that.uid && goodId == that.goodId && starAbility == that.starAbility
				&& starAttitude == that.starAttitude && starPrice == that.starPrice && createTime == that.createTime
				&& orderId == that.orderId && level == that.level && sellerId == that.sellerId
				&& Objects.equals(username, that.username) && Objects.equals(mobile, that.mobile)
				&& Objects.equals(goodName, that.goodName) && Objects.equals(goodItem, that.goodItem)
				&& Objects.equals(content, that.content);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, uid, username, mobile, goodId, goodName, goodItem, starAbility, starAttitude, starPrice,
				content, createTime, orderId, level, sellerId);
	}

}
