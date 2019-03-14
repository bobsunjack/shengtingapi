package com.example.shengtingapi.dto;

import com.example.shengtingapi.controller.BaseController;

public class ClusterGet {
    private String cluster_id= BaseController.CLUSTER_ID;
    private boolean ignore_centroid;
    private String object_type=BaseController.OBJECT_TYPE;
    private String feature_version=BaseController.FEATURE_VERSION;
    private Page page;
    private Period period;

    public ClusterGet(Page page, Period period) {
        this.page = page;
        this.period = period;
    }

    public String getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(String cluster_id) {
        this.cluster_id = cluster_id;
    }

    public boolean isIgnore_centroid() {
        return ignore_centroid;
    }

    public void setIgnore_centroid(boolean ignore_centroid) {
        this.ignore_centroid = ignore_centroid;
    }

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public String getFeature_version() {
        return feature_version;
    }

    public void setFeature_version(String feature_version) {
        this.feature_version = feature_version;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
