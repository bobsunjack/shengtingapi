package com.example.shengtingapi.init;

import com.example.shengtingapi.db.mongo.dao.CameraInfoRepository;
import com.example.shengtingapi.db.mongo.entity.CameraInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoCacheExecute implements CommandLineRunner {
    @Autowired
    CameraInfoRepository cameraInfoRepository;

    public static List<CameraInfo> cameraInfoList = null;
    @Override
    public void run(String... args) throws Exception {

        cameraInfoList= cameraInfoRepository.findAll();

    }

    public static CameraInfo getItem(String cameraId, String regionId) {
        for (CameraInfo cameraInfo : cameraInfoList) {
            if (cameraInfo.getCameraId().equals(cameraId) && cameraInfo.getRegionId().equals(regionId)) {
                return cameraInfo;
            }
        }
        return new CameraInfo();
    }
}
