package com.example.shengtingapi.response.wrap;

import com.example.shengtingapi.util.DateUtil;

import java.util.Date;

public class ClusterGetItem implements Comparable<ClusterGetItem>{
    private String cameraId;
    private String regionId;
    private String captureTime;
    private String imgUrl;
    private String imgBigUrl;
    private String cameraName;
    private String regionName;
    private String lat;
    private String lng;


    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

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


    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }



    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCaptureTime() {
        if (captureTime != null) {
            return DateUtil.shangTangTimeToStr(captureTime);
            //return captureTime.replace("T", " ").replace("Z", "");
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

    @Override
    public int compareTo(ClusterGetItem o) {
        return o.captureTime.compareTo(this.captureTime);
    }
}
