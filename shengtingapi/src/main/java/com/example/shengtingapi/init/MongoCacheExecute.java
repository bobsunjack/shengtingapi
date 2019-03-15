package com.example.shengtingapi.init;

import com.example.shengtingapi.db.mongo.dao.CameraInfoRepository;
import com.example.shengtingapi.db.mongo.entity.CameraInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MongoCacheExecute implements CommandLineRunner {
    @Autowired
    CameraInfoRepository cameraInfoRepository;

    public static List<CameraInfo> cameraInfoList = null;
    public static Map<String, CameraInfo> CAMERAINFOMAP = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {

        List<CameraInfo> cameraInfoList1 = cameraInfoRepository.findAll();
        for (CameraInfo cameraInfo : cameraInfoList1) {
            CAMERAINFOMAP.put(cameraInfo.getCameraId() + cameraInfo.getRegionId(), cameraInfo);
        }

    }

    public static CameraInfo getItem(String cameraId, String regionId) {
        CameraInfo cameraInfo = CAMERAINFOMAP.get(cameraId + regionId);
        if (cameraInfo != null) {
            return cameraInfo;
        }

        return new CameraInfo();
    }

/*    public static CameraInfo getItem(String cameraId, String regionId) {
        for (CameraInfo cameraInfo : cameraInfoList) {
            if (cameraInfo.getCameraId().equals(cameraId) && cameraInfo.getRegionId().equals(regionId)) {
                return cameraInfo;
            }
        }
        return new CameraInfo();
    }*/
}
