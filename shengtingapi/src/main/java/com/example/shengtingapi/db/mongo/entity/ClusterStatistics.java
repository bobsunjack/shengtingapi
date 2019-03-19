package com.example.shengtingapi.db.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ClusterStatistics")
public class ClusterStatistics implements Comparable<ClusterStatistics>{
    @Id
    private String id;
    private Long CaptureTotal;
    private String StatisticsTime;
    private Long ClusterTotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public int compareTo(ClusterStatistics o) {
        return o.getStatisticsTime().compareTo(this.getStatisticsTime());
    }
}
