package com.mobile.fsaliance.common.vo;

import java.io.Serializable;

/**
 * @author yuanxueyuan
 * @Description: 商品信息
 * @date 2017/12/25  21:52
 * ${tags}
 */
public class Good implements Serializable{


    private String goodsId;//ID
    private String goodsImg;//商品图
    private String goodsCode;//商品的优惠卷的码
    private String sellerId;// 卖家id
    private String shopTitle;// 店铺名称
    private String goodsTitle;// 商品标题
    private String goodsFinalPrice;//折扣价
    private String goodsFinalPriceWap;//无线折扣价
    private String nick;//卖家名称
    private int volume; //30天销量
    private String itemUrl; //商品详情页链接地址
    private int  couponTotalCount;//优惠券总量
    private String commissionRate;//佣金比率(%)
    private String couponInfo;//优惠券面额
    private int couponRemainCount;//优惠券剩余量
    private String couponClickUrl;//商品优惠券推广链接
    private String itemDescription;// 宝贝描述（推荐理由)
    private String goodProvcity;//宝贝所在地
    private String reservePrice;//商品一口价格
    private String goodStatus;//宝贝状态，0失效，1有效；
    private int goodType;//宝贝类型：1 普通商品； 2 鹊桥高佣金商品；3 定向招商商品；4 营销计划商品;
    private int userType;//卖家类型

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }



    public String getGoodsFinalPriceWap() {
        return goodsFinalPriceWap;
    }

    public void setGoodsFinalPriceWap(String goodsFinalPriceWap) {
        this.goodsFinalPriceWap = goodsFinalPriceWap;
    }

    public String getGoodStatus() {
        return goodStatus;

    }

    public int getGoodType() {
        return goodType;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setGoodType(int goodType) {
        this.goodType = goodType;
    }

    public void setGoodStatus(String goodStatus) {
        this.goodStatus = goodStatus;
    }



    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(String reservePrice) {
        this.reservePrice = reservePrice;
    }


    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsFinalPrice() {
        return goodsFinalPrice;
    }

    public void setGoodsFinalPrice(String goodsFinalPrice) {
        this.goodsFinalPrice = goodsFinalPrice;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public int getCouponTotalCount() {
        return couponTotalCount;
    }

    public void setCouponTotalCount(int couponTotalCount) {
        this.couponTotalCount = couponTotalCount;
    }

    public String getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(String couponInfo) {
        this.couponInfo = couponInfo;
    }

    public int getCouponRemainCount() {
        return couponRemainCount;
    }

    public void setCouponRemainCount(int couponRemainCount) {
        this.couponRemainCount = couponRemainCount;
    }

    public String getGoodProvcity() {
        return goodProvcity;
    }

    public void setGoodProvcity(String goodProvcity) {
        this.goodProvcity = goodProvcity;
    }

    public String getCouponClickUrl() {
        return couponClickUrl;
    }

    public void setCouponClickUrl(String couponClickUrl) {
        this.couponClickUrl = couponClickUrl;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    @Override
    public String toString() {
        return "Good{" +
                "goodsId='" + goodsId + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", shopTitle='" + shopTitle + '\'' +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsFinalPrice='" + goodsFinalPrice + '\'' +
                ", nick='" + nick + '\'' +
                ", volume='" + volume + '\'' +
                ", itemUrl='" + itemUrl + '\'' +
                ", couponTotalCount=" + couponTotalCount +
                ", commissionRate='" + commissionRate + '\'' +
                ", couponInfo='" + couponInfo + '\'' +
                ", couponRemainCount='" + couponRemainCount + '\'' +
                ", couponClickUrl='" + couponClickUrl + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                '}';
    }
}
