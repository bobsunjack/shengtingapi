package com.example.shengtingapi.response.wrap;

public class ClusterStatisticsNumResult {
    private Integer num;
    private Long CaptureTotal;
    private String StatisticsTime;
    private Long ClusterTotal;
    private Long addClusterNum;
    private Long addCaptureNum;

    public Long getAddClusterNum() {
        return addClusterNum;
    }

    public void setAddClusterNum(Long addClusterNum) {
        this.addClusterNum = addClusterNum;
    }

    public Long getAddCaptureNum() {
        return addCaptureNum;
    }

    public void setAddCaptureNum(Long addCaptureNum) {
        this.addCaptureNum = addCaptureNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getCaptureTotal() {
        return CaptureTotal;
    }

    public void setCaptureTotal(Long captureTotal) {
        CaptureTotal = captureTotal;
    }

    public String getStatisticsTime() {
        return StatisticsTime;
    }

    public void setStatisticsTime(String statisticsTime) {
        StatisticsTime = statisticsTime;
    }

    public Long getClusterTotal() {
        return ClusterTotal;
    }

    public void setClusterTotal(Long clusterTotal) {
        ClusterTotal = clusterTotal;
    }
}
