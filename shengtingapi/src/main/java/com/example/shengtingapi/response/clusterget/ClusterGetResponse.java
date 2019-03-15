package com.example.shengtingapi.response.clusterget;

import com.example.shengtingapi.response.clustersearch.ClusterListResponse;

public class ClusterGetResponse {
    private ClusterListResponse cluster;

    public ClusterListResponse getCluster() {
        return cluster;
    }

    public void setCluster(ClusterListResponse cluster) {
        this.cluster = cluster;
    }
}
