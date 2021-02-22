package com.eastrobot.robotdev.com.eastrobot.robotdev.bean;

import lombok.Data;

/**
 * @dec :
 * @Date: 2020/3/5
 * @Auther: pengbo.zhao
 * @version: 1.0
 * @demand:
 */
@Data
public class User {

    //流水号
    private String serialNo;

    //申请人姓名
    private String clientName;

    //申请人姓名
    private String clientSex;

    //生日
    private String clientBirthday;

    //证件号：身份证号
    private String certificateNo;

    //产品类型: （说明：申请的卡片名称类型，4位码）
    private String productType;

    //主卡客户签字申请日期（yyyy-MM-dd）
    private String applicationDate;

    //手机号
    private String mobile;

    //单位名称
    private String unitName;

    //单位地址
    private String unitAddress;

    //单位电话
    private String unitTelephone;

    //住宅地址
    private String homeAddress;

    public String getSerialNo() {
        return serialNo;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientSex() {
        return clientSex;
    }

    public String getClientBirthday() {
        return clientBirthday;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public String getProductType() {
        return productType;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public String getUnitTelephone() {
        return unitTelephone;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientSex(String clientSex) {
        this.clientSex = clientSex;
    }

    public void setClientBirthday(String clientBirthday) {
        this.clientBirthday = clientBirthday;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public void setUnitTelephone(String unitTelephone) {
        this.unitTelephone = unitTelephone;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "serialNo='" + serialNo + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientSex='" + clientSex + '\'' +
                ", clientBirthday='" + clientBirthday + '\'' +
                ", certificateNo='" + certificateNo + '\'' +
                ", productType='" + productType + '\'' +
                ", applicationDate='" + applicationDate + '\'' +
                ", mobile='" + mobile + '\'' +
                ", unitName='" + unitName + '\'' +
                ", unitAddress='" + unitAddress + '\'' +
                ", unitTelephone='" + unitTelephone + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                '}';
    }
}
