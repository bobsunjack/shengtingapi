package com.example.shengtingapi.response.base;

import com.example.shengtingapi.controller.BaseController;

public class PortraitImage {
    private String url;

    public String getUrl() {
        if (url != null) {
            return BaseController.PORTRAIT_IMAGE_PREX+url;
        }else{
            return null;
        }

    }

    public void setUrl(String url) {
        this.url = url;
    }
}
