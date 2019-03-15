package com.example.shengtingapi.response.wrap;

public class ClusterSearchResult {
    private String camerId;
    private String regionId;
    private String ClusterId;
    private String captureTime;
    private String imgUrl;
    private String imgBigUrl;
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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

    public String getClusterId() {
        return ClusterId;
    }

    public void setClusterId(String clusterId) {
        ClusterId = clusterId;
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
