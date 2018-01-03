package com.mobile.fsaliance.common.vo;

import java.io.Serializable;

/**
 *  @author tanyadong
  * @Description: 提现记录
  * @date 2018/1/2 0002 19:31
  * ${tags}
 */

public class IncomeRecord implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String incomeTime; //提现时间
	private String incomeMoneny; //提现金额
	private int type; //收入类型   0 主动收入，1 被动收入
	public IncomeRecord() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIncomeTime() {
		return incomeTime;
	}

	public void setIncomeTime(String incomeTime) {
		this.incomeTime = incomeTime;
	}

	public String getIncomeMoneny() {
		return incomeMoneny;
	}

	public void setIncomeMoneny(String incomeMoneny) {
		this.incomeMoneny = incomeMoneny;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}






	





	


	
	
	
