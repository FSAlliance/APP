package com.mobile.fsaliance.common.vo;

/**
 * @author yuanxueyuan
 * @Description: 商品信息
 * @date 2017/12/25  21:52
 * ${tags}
 */
public class Goods {

    private String goodsId;//ID
    private String goodsDescribe;//商品简介
    private double goodsPriceDiscount;//商品优惠券价格
    private double goodsPrice;//商品价格
    private int goodsSaleNum;//商品销量
    private String goodsImg;//商品图
    private String goodsCode;//商品的优惠卷的码


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

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId='" + goodsId + '\'' +
                ", goodsDescribe='" + goodsDescribe + '\'' +
                ", goodsPriceDiscount=" + goodsPriceDiscount +
                ", goodsPrice=" + goodsPrice +
                ", goodsSaleNum=" + goodsSaleNum +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                '}';
    }
}
