package com.example.shengtingapi.response.base;

public class ObjectId {
    private CameraId camera_id;
    private String captured_time;

    public CameraId getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(CameraId camera_id) {
        this.camera_id = camera_id;
    }

    public String getCaptured_time() {
        return captured_time;
    }
    public String getCaptured_time_normal() {
        if (captured_time != null) {
          return   captured_time.replace("T", " ").replace("Z", "");
        }
        return captured_time;
    }

    public void setCaptured_time(String captured_time) {
        this.captured_time = captured_time;
    }
}
