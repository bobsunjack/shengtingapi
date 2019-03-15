package com.example.shengtingapi.db.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ClusterInfo")
public class ClusterInfo {
    public static String COLLETION_NAME = "ClusterInfo";
    @Id
    private String id;
    private Long CaptureTime;
    private String ImgUrl;
    private String ImgBigUrl;
    private Long ClusterTotal;
    private Long LastTotal;
    private String CameraId;
    private String CameraName;
    private String RegionId;
    private String RegionName;
    private Double Lat;
    private Double Lng;

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLng() {
        return Lng;
    }

    public void setLng(Double lng) {
        Lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCaptureTime() {
        return CaptureTime;
    }

    public void setCaptureTime(Long captureTime) {
        CaptureTime = captureTime;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getImgBigUrl() {
        return ImgBigUrl;
    }

    public void setImgBigUrl(String imgBigUrl) {
        ImgBigUrl = imgBigUrl;
    }

    public Long getClusterTotal() {
        return ClusterTotal;
    }

    public void setClusterTotal(Long clusterTotal) {
        ClusterTotal = clusterTotal;
    }

    public Long getLastTotal() {
        return LastTotal;
    }

    public void setLastTotal(Long lastTotal) {
        LastTotal = lastTotal;
    }

    public String getCameraId() {
        return CameraId;
    }

    public void setCameraId(String cameraId) {
        CameraId = cameraId;
    }

    public String getCameraName() {
        return CameraName;
    }

    public void setCameraName(String cameraName) {
        CameraName = cameraName;
    }

    public String getRegionId() {
        return RegionId;
    }

    public void setRegionId(String regionId) {
        RegionId = regionId;
    }

    public String getRegionName() {
        return RegionName;
    }

    public void setRegionName(String regionName) {
        RegionName = regionName;
    }
}
