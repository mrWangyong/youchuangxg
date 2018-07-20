package com.ycxg.server.model;

import java.util.Date;

public class License {
    private Integer licenseId;

    private String licenseAccreditCode;

    private String licenseUniqueMark;

    private String licensePackageName;

    private Integer licenseDeviceCount;

    private Date licenseUseTimeLimit;

    private String licenseServiceCategory;

    private String licenseAppName;

    private String licenseApplyCategory;

    private String licenseLicPath;

    private String licensePublicStorePath;

    private String licenseClientType;

    private String licenseSecretkey;

    private String request;

    private Integer licenseUserId;

    private Date licenseCreateTime;

    private Date licenseUpdateTime;

    private Integer licenseState;

    public Date getLicenseCreateTime() {
        return licenseCreateTime;
    }

    public void setLicenseCreateTime(Date licenseCreateTime) {
        this.licenseCreateTime = licenseCreateTime;
    }

    public Date getLicenseUpdateTime() {
        return licenseUpdateTime;
    }

    public void setLicenseUpdateTime(Date licenseUpdateTime) {
        this.licenseUpdateTime = licenseUpdateTime;
    }

    public Integer getLicenseState() {
        return licenseState;
    }

    public void setLicenseState(Integer licenseState) {
        this.licenseState = licenseState;
    }

    public Integer getLicenseUserId() {
        return licenseUserId;
    }

    public void setLicenseUserId(Integer licenseUserId) {
        this.licenseUserId = licenseUserId;
    }

    public Integer getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenseAccreditCode() {
        return licenseAccreditCode;
    }

    public void setLicenseAccreditCode(String licenseAccreditCode) {
        this.licenseAccreditCode = licenseAccreditCode;
    }

    public String getLicenseUniqueMark() {
        return licenseUniqueMark;
    }

    public void setLicenseUniqueMark(String licenseUniqueMark) {
        this.licenseUniqueMark = licenseUniqueMark;
    }

    public String getLicensePackageName() {
        return licensePackageName;
    }

    public void setLicensePackageName(String licensePackageName) {
        this.licensePackageName = licensePackageName;
    }

    public Integer getLicenseDeviceCount() {
        return licenseDeviceCount;
    }

    public void setLicenseDeviceCount(Integer licenseDeviceCount) {
        this.licenseDeviceCount = licenseDeviceCount;
    }

    public Date getLicenseUseTimeLimit() {
        return licenseUseTimeLimit;
    }

    public void setLicenseUseTimeLimit(Date licenseUseTimeLimit) {
        this.licenseUseTimeLimit = licenseUseTimeLimit;
    }

    public String getLicenseServiceCategory() {
        return licenseServiceCategory;
    }

    public void setLicenseServiceCategory(String licenseServiceCategory) {
        this.licenseServiceCategory = licenseServiceCategory;
    }

    public String getLicenseAppName() {
        return licenseAppName;
    }

    public void setLicenseAppName(String licenseAppName) {
        this.licenseAppName = licenseAppName;
    }

    public String getLicenseApplyCategory() {
        return licenseApplyCategory;
    }

    public void setLicenseApplyCategory(String licenseApplyCategory) {
        this.licenseApplyCategory = licenseApplyCategory;
    }

    public String getLicenseLicPath() {
        return licenseLicPath;
    }

    public void setLicenseLicPath(String licenseLicPath) {
        this.licenseLicPath = licenseLicPath;
    }

    public String getLicensePublicStorePath() {
        return licensePublicStorePath;
    }

    public void setLicensePublicStorePath(String licensePublicStorePath) {
        this.licensePublicStorePath = licensePublicStorePath;
    }

    public String getLicenseClientType() {
        return licenseClientType;
    }

    public void setLicenseClientType(String licenseClientType) {
        this.licenseClientType = licenseClientType;
    }

    public String getLicenseSecretkey() {
        return licenseSecretkey;
    }

    public void setLicenseSecretkey(String licenseSecretkey) {
        this.licenseSecretkey = licenseSecretkey;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}