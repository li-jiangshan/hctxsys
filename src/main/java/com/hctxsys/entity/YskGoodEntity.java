package com.hctxsys.entity;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.*;

import com.hctxsys.entity.YskGoodBrandEntity;
import com.hctxsys.entity.YskGoodCategoryEntity;

@Entity
@Table(name = "ysk_good", schema = "hctx_db", catalog = "")
public class YskGoodEntity {
	
    //商品id
    private int goodId;
    //分类id
    private int categoryId;
    //商品编号 
    private String goodNo;
    //商品名称
    private String goodName;
    //浏览次数
    private int goodRead;
    //品牌id
    private int brandId;
    //品牌名称
    private String brandName;
    //库存数量
    private int goodStore;
    //商品评论数
    private int goodComment;
    //商品重量克为单位
    private int goodWeight;
    //市场价
    private BigDecimal marketPrice;
    //成本价
    private BigDecimal costPrice;
    //商品价
    private BigDecimal goodPrice;
    //商品关键词
    private String keywords;
    //商品详细描述
    private String goodContent;
    //是否为实物
    private int isReal;
	//是否上架 1-上架 0-下架
    private int status;
	//是否包邮0否1是
    private int shipFee;
	//商品排序
    private int goodSort;
	//是否推荐
    private int isRecommend;
	//是否新品
    private int isNew;
	//是否热卖
    private int isHot;
	//最后更新时间
    private int lastUpdate;
    //购买商品赠送积分
    private int goodIntegral;
    //供货商
    private String goodSupplier;
    //商品销量
    private int goodSellNum;
    //佣金用于分销分成 百分比
    private String goodCommission;
    //封面图片
    private String goodCoverImg;
    //添加时间
    private int createTime;
	//模型ID
    private int modelId;
	//商家ID
    private int sellerId;
	//0-平台自己 1-商家产品
    private int goodType;


    private YskGoodCategoryEntity yskGoodCategoryEntity;
    
    
    private YskGoodImgEntity yskGoodImgEntity;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goodId",referencedColumnName = "good_id",updatable = false,insertable = false)
    public YskGoodImgEntity getYskGoodImgEntity() {
		return yskGoodImgEntity;
	}

	public void setYskGoodImgEntity(YskGoodImgEntity yskGoodImgEntity) {
		this.yskGoodImgEntity = yskGoodImgEntity;
	}

	@Transient
    public YskGoodCategoryEntity getYskGoodCategoryEntity() {
		return yskGoodCategoryEntity;
	}

	public void setYskGoodCategoryEntity(YskGoodCategoryEntity yskGoodCategoryEntity) {
		this.yskGoodCategoryEntity = yskGoodCategoryEntity;
	}
	
	private YskGoodBrandEntity yskGoodBrandEntity;

	@Transient
	public YskGoodBrandEntity getYskGoodBrandEntity() {
		return yskGoodBrandEntity;
	}

	public void setYskGoodBrandEntity(YskGoodBrandEntity yskGoodBrandEntity) {
		this.yskGoodBrandEntity = yskGoodBrandEntity;
	}
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "good_id")
	public int getGoodId() {
		return goodId;
	}
	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}
	@Basic
	@Column(name = "category_id")
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
	@Column(name = "good_name")
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	@Basic
	@Column(name = "good_read")
	public int getGoodRead() {
		return goodRead;
	}
	public void setGoodRead(int goodRead) {
		this.goodRead = goodRead;
	}
	@Basic
	@Column(name = "brand_id")
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	@Basic
	@Column(name = "brand_name")
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	@Basic
	@Column(name = "good_store")
	public int getGoodStore() {
		return goodStore;
	}
	public void setGoodStore(int goodStore) {
		this.goodStore = goodStore;
	}
	@Basic
	@Column(name = "good_comment")
	public int getGoodComment() {
		return goodComment;
	}
	public void setGoodComment(int goodComment) {
		this.goodComment = goodComment;
	}
	@Basic
	@Column(name = "good_weight")
	public int getGoodWeight() {
		return goodWeight;
	}
	public void setGoodWeight(int goodWeight) {
		this.goodWeight = goodWeight;
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
	@Column(name = "cost_price")
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
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
	@Column(name = "keywords")
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	@Basic
	@Column(name = "good_content")
	public String getGoodContent() {
		return goodContent;
	}
	public void setGoodContent(String goodContent) {
		this.goodContent = goodContent;
	}
	@Basic
	@Column(name = "is_real")
	public int getIsReal() {
		return isReal;
	}
	public void setIsReal(int isReal) {
		this.isReal = isReal;
	}
	@Basic
	@Column(name = "status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Basic
	@Column(name = "ship_fee")
	public int getShipFee() {
		return shipFee;
	}
	public void setShipFee(int shipFee) {
		this.shipFee = shipFee;
	}
	@Basic
	@Column(name = "good_sort")
	public int getGoodSort() {
		return goodSort;
	}
	public void setGoodSort(int goodSort) {
		this.goodSort = goodSort;
	}
	@Basic
	@Column(name = "is_recommend")
	public int getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(int isRecommend) {
		this.isRecommend = isRecommend;
	}
	@Basic
	@Column(name = "is_new")
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	@Basic
	@Column(name = "is_hot")
	public int getIsHot() {
		return isHot;
	}
	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}
	@Basic
	@Column(name = "last_update")
	public int getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(int lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	@Basic
	@Column(name = "good_integral")
	public int getGoodIntegral() {
		return goodIntegral;
	}
	public void setGoodIntegral(int goodIntegral) {
		this.goodIntegral = goodIntegral;
	}
	@Basic
	@Column(name = "good_supplier")
	public String getGoodSupplier() {
		return goodSupplier;
	}
	public void setGoodSupplier(String goodSupplier) {
		this.goodSupplier = goodSupplier;
	}
	@Basic
	@Column(name = "good_sell_num")
	public int getGoodSellNum() {
		return goodSellNum;
	}
	public void setGoodSellNum(int goodSellNum) {
		this.goodSellNum = goodSellNum;
	}
	@Basic
	@Column(name = "good_commission")
	public String getGoodCommission() {
		return goodCommission;
	}
	public void setGoodCommission(String goodCommission) {
		this.goodCommission = goodCommission;
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
	@Column(name = "create_time")
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	@Basic
	@Column(name = "model_id")
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
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
	@Column(name = "good_type")
	public int getGoodType() {
		return goodType;
	}
	public void setGoodType(int goodType) {
		this.goodType = goodType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		YskGoodEntity that = (YskGoodEntity) o;
		return goodId == that.goodId &&
				categoryId == that.categoryId &&
				goodRead == that.goodRead &&
				brandId == that.brandId &&
				goodStore == that.goodStore &&
				goodComment == that.goodComment &&
				goodWeight == that.goodWeight &&
				isReal == that.isReal &&
				status == that.status &&
				shipFee == that.shipFee &&
				goodSort == that.goodSort &&
				isRecommend == that.isRecommend &&
				isNew == that.isNew &&
				isHot == that.isHot &&
				lastUpdate == that.lastUpdate &&
				goodIntegral == that.goodIntegral &&
				goodSellNum == that.goodSellNum &&
				createTime == that.createTime &&
				modelId == that.modelId &&
				sellerId == that.sellerId &&
				goodType == that.goodType &&
				Objects.equals(goodNo, that.goodNo) &&
				Objects.equals(goodName, that.goodName) &&
				Objects.equals(brandName, that.brandName) &&
				Objects.equals(marketPrice, that.marketPrice) &&
				Objects.equals(costPrice, that.costPrice) &&
				Objects.equals(goodPrice, that.goodPrice) &&
				Objects.equals(keywords, that.keywords) &&
				Objects.equals(goodContent, that.goodContent) &&
				Objects.equals(goodSupplier, that.goodSupplier) &&
				Objects.equals(goodCommission, that.goodCommission) &&
				Objects.equals(goodCoverImg, that.goodCoverImg);
	}

	@Override
	public int hashCode() {

		return Objects.hash(goodId, categoryId, goodNo, goodName, goodRead, brandId, brandName, goodStore, goodComment, goodWeight, marketPrice, costPrice, goodPrice, keywords, goodContent, isReal, status, shipFee, goodSort, isRecommend, isNew, isHot, lastUpdate, goodIntegral, goodSupplier, goodSellNum, goodCommission, goodCoverImg, createTime, modelId, sellerId, goodType);
	}
}
