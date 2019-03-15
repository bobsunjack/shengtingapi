package com.example.shengtingapi.response.clustersearch;

import com.example.shengtingapi.response.base.ObjectId;

public class ClusterResponse {
    private ObjectId object_id;
    private String portrait_image;
    private String panoramic_image;
    private String score;

    public ObjectId getObject_id() {
        return object_id;
    }

    public void setObject_id(ObjectId object_id) {
        this.object_id = object_id;
    }

    public String getPortrait_image() {
        return portrait_image;
    }

    public void setPortrait_image(String portrait_image) {
        this.portrait_image = portrait_image;
    }

    public String getPanoramic_image() {
        return panoramic_image;
    }

    public void setPanoramic_image(String panoramic_image) {
        this.panoramic_image = panoramic_image;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
