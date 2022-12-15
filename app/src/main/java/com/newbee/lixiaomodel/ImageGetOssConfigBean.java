package com.newbee.lixiaomodel;

import java.io.Serializable;

public class ImageGetOssConfigBean implements Serializable {
    private String expiration;
    private String accessKeyId;
    private String accessKeySecret;
    private String securityToken;
    private String region;
    private String bucket;

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Override
    public String toString() {
        return "ImageGetOssConfigBean{" +
                "expiration='" + expiration + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", securityToken='" + securityToken + '\'' +
                ", region='" + region + '\'' +
                ", bucket='" + bucket + '\'' +
                '}';
    }
}
