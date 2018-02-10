package com.mobile.fsaliance.common.vo;

import java.io.Serializable;

/**
 * Created by 78326 on 2017.9.9.
 */
public class User implements Serializable{
    private String id;//序号
    private String phoneNum;//用户名
    private String nickName; //昵称
    private String password;//密码
    private String userHead; //头像
    private String aliPayAccount; //支付宝账号
    private String  shareCode; //推荐码
    private String  scoreNum; //积分
    private Double  cashing; //提现中
    private Double  cashed; //已提现
    private Double balanceNum;//余额
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(String scoreNum) {
        this.scoreNum = scoreNum;
    }

    public Double getCashing() {
        return cashing;
    }

    public void setCashing(Double cashing) {
        this.cashing = cashing;
    }

    public Double getCashed() {
        return cashed;
    }

    public void setCashed(Double cashed) {
        this.cashed = cashed;
    }

    public Double getBalanceNum() {
        return balanceNum;
    }

    public void setBalanceNum(Double balanceNum) {
        this.balanceNum = balanceNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phoneNum='" + phoneNum + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", userHead='" + userHead + '\'' +
                ", aliPayAccount='" + aliPayAccount + '\'' +
                ", shareCode='" + shareCode + '\'' +
                ", scoreNum='" + scoreNum + '\'' +
                ", cashing='" + cashing + '\'' +
                ", cashed='" + cashed + '\'' +
                '}';
    }
}
