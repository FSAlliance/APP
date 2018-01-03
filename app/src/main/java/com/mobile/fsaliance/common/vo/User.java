package com.mobile.fsaliance.common.vo;

import java.io.Serializable;

/**
 * Created by 78326 on 2017.9.9.
 */
public class User implements Serializable{
    private Long id;//序号
    private String userName;//用户名。昵称
    private String password;//密码
    private String userHead; //头像
    private String aliPayAccount; //支付宝账号
    private String  shareCode; //推荐码

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getAliPayAccount() {
        return aliPayAccount;
    }

    public void setAliPayAccount(String aliPayAccount) {
        this.aliPayAccount = aliPayAccount;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}
