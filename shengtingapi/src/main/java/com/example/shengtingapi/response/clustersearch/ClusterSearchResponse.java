package com.example.shengtingapi.response.clustersearch;

import java.util.List;

public class ClusterSearchResponse {
    private List<ClusterListResponse> clusters;

    public List<ClusterListResponse> getClusters() {
        return clusters;
    }

    public void setClusters(List<ClusterListResponse> clusters) {
        this.clusters = clusters;
    }
}
