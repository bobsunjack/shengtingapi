package com.example.shengtingapi;

import com.example.shengtingapi.db.mongo.dao.CameraInfoRepository;
import com.example.shengtingapi.db.mongo.dao.ClusterInfoRepository;
import com.example.shengtingapi.db.mongo.dao.ClusterStatisticsRepository;
import com.example.shengtingapi.db.mongo.entity.CameraInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterStatistics;
import com.example.shengtingapi.test.TestFile2;
import com.example.shengtingapi.util.UuidUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShengtingapiApplicationTests {

    @Autowired
    ClusterStatisticsRepository clusterStatisticsRepository;

    @Autowired
    ClusterInfoRepository clusterInfoRepository;

    @Autowired
    CameraInfoRepository cameraInfoRepository;

    @Test
    public void contextLoads() {
        ClusterStatistics clusterStatistics = new ClusterStatistics();
        clusterStatistics.setId(UuidUtil.get32UUID());
        clusterStatistics.setCaptureTotal(3l);
        clusterStatistics.setClusterTotal(4l);
        clusterStatistics.setStatisticsTime("2019-01-02");
        clusterStatisticsRepository.save(clusterStatistics);

        clusterStatistics = new ClusterStatistics();
        clusterStatistics.setId(UuidUtil.get32UUID());
        clusterStatistics.setCaptureTotal(13l);
        clusterStatistics.setClusterTotal(14l);
        clusterStatistics.setStatisticsTime("2019-03-18");
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
        clusterInfoRepository.save(clusterInfo);
    }


    //@Test
    public void contextLoads2() {
        List<String> items= TestFile2.toArrayByFileReader1("d:\\qxb\\runoob.txt");
        for (String item : items) {
            String[] unit = item.split("\t");
            CameraInfo info = new CameraInfo();
            info.setCameraName(unit[0]);
            info.setCameraId(unit[1]);
            info.setRegionId(unit[2]);
            info.setRegionName(unit[3]);
            info.setArea(unit[4]);
            cameraInfoRepository.save(info);
        }

    }
   // @Test
    public void contextLoads3() {
      List<CameraInfo> cameraInfoList= cameraInfoRepository.findAll();
        System.out.println(cameraInfoList.size());
    }

}
