package com.mobile.fsaliance.common.vo;

/**
 * @author yuanxueyuan
 * @Description: 商品信息
 * @date 2017/12/25  21:52
 * ${tags}
 */
public class Good {


    private String goodsId;//ID
    private String goodsDescribe;//商品简介
    private double goodsPriceDiscount;//商品优惠券价格
    private double goodsPrice;//商品价格
    private int goodsSaleNum;//商品销量
    private String goodsImg;//商品图
    private String goodsCode;//商品的优惠卷的码
    private String shopTitle;// 店铺名称
    private String goodsTitle;// 商品标题
    private String goodsFinalPrice;//折扣价
    private String nick;//卖家名称
    private int volume; //30天销量
    private String goodsPicturl;//商品主图
    private String itemUrl; //商品详情页链接地址
    private int  couponTotalCount;//优惠券总量
    private String commissionRate;//佣金比率(%)
    private String couponInfo;//优惠券面额
    private int couponRemainCount;//优惠券剩余量
    private String couponClickUrl;//商品优惠券推广链接
    private String itemDescription;// 宝贝描述（推荐理由)

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsDescribe() {
        return goodsDescribe;
    }

    public void setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
    }

    public double getGoodsPriceDiscount() {
        return goodsPriceDiscount;
    }

    public void setGoodsPriceDiscount(double goodsPriceDiscount) {
        this.goodsPriceDiscount = goodsPriceDiscount;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsSaleNum() {
        return goodsSaleNum;
    }

    public void setGoodsSaleNum(int goodsSaleNum) {
        this.goodsSaleNum = goodsSaleNum;
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

    public String getGoodsPicturl() {
        return goodsPicturl;
    }

    public void setGoodsPicturl(String goodsPicturl) {
        this.goodsPicturl = goodsPicturl;
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
                ", goodsDescribe='" + goodsDescribe + '\'' +
                ", goodsPriceDiscount=" + goodsPriceDiscount +
                ", goodsPrice=" + goodsPrice +
                ", goodsSaleNum=" + goodsSaleNum +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", shopTitle='" + shopTitle + '\'' +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsFinalPrice='" + goodsFinalPrice + '\'' +
                ", nick='" + nick + '\'' +
                ", volume='" + volume + '\'' +
                ", goodsPicturl='" + goodsPicturl + '\'' +
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
