package com.mobile.fsaliance.common.vo;

import java.io.Serializable;


/**
 * 订单表
 */

public class Order implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String orderNumber;    //订单编号
	private int type; //订单状态   0成功 1 已结算 2已付款 3 失效订单
	private Double money;//订单总金额
	private String orderTime;//下单时间
	private String earningTime;//结算时间
	private String orderShopTitle; //店铺名称
	private String orderSellerNick; //卖家昵称
	private String orderItemTitle;// 商品标题
	public Order() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getEarningTime() {
		return earningTime;
	}

	public void setEarningTime(String earningTime) {
		this.earningTime = earningTime;
	}

	public String getOrderShopTitle() {
		return orderShopTitle;
	}

	public void setOrderShopTitle(String orderShopTitle) {
		this.orderShopTitle = orderShopTitle;
	}

	public String getOrderSellerNick() {
		return orderSellerNick;
	}

	public void setOrderSellerNick(String orderSellerNick) {
		this.orderSellerNick = orderSellerNick;
	}

	public String getOrderItemTitle() {
		return orderItemTitle;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id='" + id + '\'' +
				", orderNumber='" + orderNumber + '\'' +
				", type=" + type +
				", money=" + money +
				", orderTime='" + orderTime + '\'' +
				", earningTime='" + earningTime + '\'' +
				", orderShopTitle='" + orderShopTitle + '\'' +
				", orderSellerNick='" + orderSellerNick + '\'' +
				", orderItemTitle='" + orderItemTitle + '\'' +
				'}';
	}

	public void setOrderItemTitle(String orderItemTitle) {
		this.orderItemTitle = orderItemTitle;
	}
}






	





	


	
	
	
