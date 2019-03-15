package com.example.shengtingapi.dto;

import com.example.shengtingapi.controller.BaseController;

public class ClusterSearch {
    private String load_mode = "CLUSTER_LOAD_PREVIEW";
    private Feature feature;
    private Config config;
    private Integer max_preview_load_count = 4;


    public ClusterSearch(Feature feature, Config config) {
        this.feature = feature;
        this.config = config;
    }

    public String getLoad_mode() {
        return load_mode;
    }

    public void setLoad_mode(String load_mode) {
        this.load_mode = load_mode;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

}
