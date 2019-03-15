package com.example.shengtingapi;

import com.example.shengtingapi.db.mongo.dao.ClusterInfoRepository;
import com.example.shengtingapi.db.mongo.dao.ClusterStatisticsRepository;
import com.example.shengtingapi.db.mongo.entity.ClusterInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterStatistics;
import com.example.shengtingapi.util.UuidUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShengtingapiApplicationTests {

    @Autowired
    ClusterStatisticsRepository clusterStatisticsRepository;

    @Autowired
    ClusterInfoRepository clusterInfoRepository;

    @Test
    public void contextLoads() {
        ClusterStatistics clusterStatistics = new ClusterStatistics();
        clusterStatistics.setId(UuidUtil.get32UUID());
        clusterStatistics.setCaptureTotal(3l);
        clusterStatistics.setClusterTotal(4l);
        clusterStatistics.setStatisticsTime(1552623132L);
        clusterStatisticsRepository.save(clusterStatistics);

        ClusterInfo clusterInfo = new ClusterInfo();
        clusterInfo.setId(UuidUtil.get32UUID());
        clusterInfo.setCamerId("2");
        clusterInfo.setCamerName("test");
        clusterInfo.setCaptureTime(1552623132L);
        clusterInfo.setClusterId("2");
        clusterInfo.setClusterTotal(4L);
        clusterInfo.setImgBigUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552628281360&di=a068768494637a372f0f46d44891222c&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F16%2F52%2F75558PICxda_1024.jpg");
        clusterInfo.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552628281360&di=a068768494637a372f0f46d44891222c&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F16%2F52%2F75558PICxda_1024.jpg");
        clusterInfo.setLastTotal(44l);
        clusterInfo.setRegionId("23323");
        clusterInfo.setRegionName("西湖");
        clusterInfo.setLat("39.916527");
        clusterInfo.setLng("116.397128");
        clusterInfoRepository.save(clusterInfo);
    }

}
