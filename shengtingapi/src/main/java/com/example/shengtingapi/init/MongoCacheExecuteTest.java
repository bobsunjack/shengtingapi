package com.example.shengtingapi.init;

import com.example.shengtingapi.db.mongo.dao.CameraInfoRepository;
import com.example.shengtingapi.db.mongo.dao.ClusterInfoRepository;
import com.example.shengtingapi.db.mongo.dao.ClusterStatisticsRepository;
import com.example.shengtingapi.db.mongo.entity.CameraInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterStatistics;
import com.example.shengtingapi.test.TestFile2;
import com.example.shengtingapi.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
public class MongoCacheExecuteTest implements CommandLineRunner {
    @Autowired
    CameraInfoRepository cameraInfoRepository;

    @Autowired
    ClusterStatisticsRepository clusterStatisticsRepository;

    @Autowired
    ClusterInfoRepository clusterInfoRepository;

    public static List<CameraInfo> cameraInfoList = null;
    public static Map<String, CameraInfo> CAMERAINFOMAP = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {

        /*ClusterStatistics clusterStatistics = new ClusterStatistics();
        clusterStatistics.setId(UuidUtil.get32UUID());
        clusterStatistics.setCaptureTotal(3l);
        clusterStatistics.setClusterTotal(4l);
        clusterStatistics.setStatisticsTime("2018-01-02");
        clusterStatisticsRepository.save(clusterStatistics);

        ClusterInfo clusterInfo = new ClusterInfo();
        clusterInfo.setId(UuidUtil.get32UUID());
        clusterInfo.setCameraId("2");
        clusterInfo.setCameraName("test");
        clusterInfo.setCaptureTime(1552623132L);
        clusterInfo.setClusterTotal(4L);
        clusterInfo.setImgBigUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552628281360&di=a068768494637a372f0f46d44891222c&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F16%2F52%2F75558PICxda_1024.jpg");
        clusterInfo.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552628281360&di=a068768494637a372f0f46d44891222c&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F16%2F52%2F75558PICxda_1024.jpg");
        clusterInfo.setLastTotal(44l);
        clusterInfo.setRegionId("23323");
        clusterInfo.setRegionName("西湖");
        clusterInfo.setLat(39.916527);
        clusterInfo.setLng(116.397128);
        clusterInfo.setArea("杭州");
        clusterInfoRepository.save(clusterInfo);*/

        List<String> items= TestFile2.toArrayByFileReader1("d:\\qxb\\runoob.txt");
        for (String item : items) {
            String unit[] = item.split("\t");
            CameraInfo info = new CameraInfo();
            info.setCameraName(unit[0]);
            info.setCameraId(unit[1]);
            info.setRegionId(unit[2]);
            info.setRegionName(unit[3]);
            info.setArea(unit[4]);
            info.setLat(unit[5]);
            info.setLng(unit[6]);
            cameraInfoRepository.save(info);
        }
    }
}
