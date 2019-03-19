package com.example.shengtingapi.json;

public class BaseJson {
    private String imageBlob;  ///base64 encode图片
    private Integer top;   ///前多少条
    private String startTime;  ///开始时间
    private String endTime;   //结束时间
    private String qtime;  ///开始时间
    private Long pageNum;  ///第0开始
    private Long pageSize;  //每页多少

    private Long clusterTotal;  //归档总数
    private Integer orderType=-1;
    private String orderField;
    private Float score;  //匹配度
    private String clusterId;

    private Long beginClusterTotal;  //归档总数
    private Long endClusterTotal;  //归档总数

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getBeginClusterTotal() {
        return beginClusterTotal;
    }

    public void setBeginClusterTotal(Long beginClusterTotal) {
        this.beginClusterTotal = beginClusterTotal;
    }

    public Long getEndClusterTotal() {
        return endClusterTotal;
    }

    public void setEndClusterTotal(Long endClusterTotal) {
        this.endClusterTotal = endClusterTotal;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public Long getClusterTotal() {
        return clusterTotal;
    }

    public void setClusterTotal(Long clusterTotal) {
        this.clusterTotal = clusterTotal;
    }

    public String getQtime() {
        return qtime;
    }

    public void setQtime(String qtime) {
        this.qtime = qtime;
    }

    public String getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(String imageBlob) {
        this.imageBlob = imageBlob;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}
