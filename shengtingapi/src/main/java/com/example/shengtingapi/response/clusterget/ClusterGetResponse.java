package com.example.shengtingapi.response.clusterget;

import com.example.shengtingapi.response.base.PageResponse;
import com.example.shengtingapi.response.clustersearch.ClusterListResponse;

public class ClusterGetResponse {
    private ClusterListResponse cluster;
    private PageResponse page;

    public PageResponse getPage() {
        return page;
    }

    public void setPage(PageResponse page) {
        this.page = page;
    }

    public ClusterListResponse getCluster() {
        return cluster;
    }

    public void setCluster(ClusterListResponse cluster) {
        this.cluster = cluster;
    }
}
