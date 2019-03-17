package com.example.shengtingapi.response.clustersearch;

import com.example.shengtingapi.response.base.ObjectId;

import java.util.List;

public class ClusterListResponse {
    private String cluster_id;
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(String cluster_id) {
        this.cluster_id = cluster_id;
    }

    private List<ClusterResponse> results;

    public List<ClusterResponse> getResults() {
        return results;
    }

    public void setResults(List<ClusterResponse> results) {
        this.results = results;
    }
}
