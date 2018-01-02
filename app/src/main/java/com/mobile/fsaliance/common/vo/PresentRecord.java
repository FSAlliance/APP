package com.mobile.fsaliance.common.vo;

import java.io.Serializable;

/**
 *  @author tanyadong
  * @Description: 提现记录
  * @date 2018/1/2 0002 19:31
  * ${tags}
 */

public class PresentRecord implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String presentTime; //提现时间
	private String presentMoneny; //提现金额
	private int state; //提现状态  0 提现中，1 已提现

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPresentTime() {
		return presentTime;
	}

	public void setPresentTime(String presentTime) {
		this.presentTime = presentTime;
	}

	public String getPresentMoneny() {
		return presentMoneny;
	}

	public void setPresentMoneny(String presentMoneny) {
		this.presentMoneny = presentMoneny;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}






	





	


	
	
	
