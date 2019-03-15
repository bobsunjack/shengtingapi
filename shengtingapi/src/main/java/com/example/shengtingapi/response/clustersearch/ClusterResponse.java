package com.example.shengtingapi.response.clustersearch;

import com.example.shengtingapi.response.base.ObjectId;
import com.example.shengtingapi.response.base.PanoramicImage;
import com.example.shengtingapi.response.base.PortraitImage;

public class ClusterResponse {
    private ObjectId object_id;
    private PortraitImage portrait_image;
    private PanoramicImage panoramic_image;
    private String score;


    public ObjectId getObject_id() {
        return object_id;
    }

    public void setObject_id(ObjectId object_id) {
        this.object_id = object_id;
    }

    public PortraitImage getPortrait_image() {
        return portrait_image;
    }

    public void setPortrait_image(PortraitImage portrait_image) {
        this.portrait_image = portrait_image;
    }

    public PanoramicImage getPanoramic_image() {
        return panoramic_image;
    }

    public void setPanoramic_image(PanoramicImage panoramic_image) {
        this.panoramic_image = panoramic_image;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
