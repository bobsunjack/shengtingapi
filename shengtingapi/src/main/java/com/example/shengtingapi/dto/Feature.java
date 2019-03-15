package com.example.shengtingapi.dto;

import com.example.shengtingapi.controller.BaseController;

public class Feature {
    public String type= BaseController.OBJECT_TYPE;
    public Integer version=BaseController.FEATURE_VERSION;
    public String blob;
    public Feature(String blob) {
        this.blob = blob;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getBlob() {
        return blob;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }
}
