package com.example.shengtingapi.response.wrap;

public class ClusterGetResult {
    private String camerId;
    private String regionId;
    private String captureTime;
    private String imgUrl;
    private String imgBigUrl;
    private String camerName;
    private String regionName;
    private String lat;
    private String lng;


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCamerName() {
        return camerName;
    }

    public void setCamerName(String camerName) {
        this.camerName = camerName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCamerId() {
        return camerId;
    }

    public void setCamerId(String camerId) {
        this.camerId = camerId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCaptureTime() {
        if (captureTime != null) {
            return captureTime.replace("T", " ").replace("Z", "");
        } else {
            return captureTime;
        }
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgBigUrl() {
        return imgBigUrl;
    }

    public void setImgBigUrl(String imgBigUrl) {
        this.imgBigUrl = imgBigUrl;
    }
}
