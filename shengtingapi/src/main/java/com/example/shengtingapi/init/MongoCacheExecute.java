package com.example.shengtingapi.init;

import com.example.shengtingapi.db.mongo.dao.CameraInfoRepository;
import com.example.shengtingapi.db.mongo.entity.CameraInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class MongoCacheExecute implements CommandLineRunner {
    public static Logger logger = LoggerFactory.getLogger(MongoCacheExecute.class);
    @Autowired
    CameraInfoRepository cameraInfoRepository;

    public static List<CameraInfo> cameraInfoList = null;
    public static Map<String, CameraInfo> CAMERAINFOMAP = new HashMap<>();

    public static Map<String, Long> CLUSTERINFOPAGE = new HashMap<>();
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {

        List<CameraInfo> cameraInfoList1 = cameraInfoRepository.findAll();
        for (CameraInfo cameraInfo : cameraInfoList1) {
            logger.debug("region list----------"+cameraInfo.getCameraId() + cameraInfo.getRegionId());
            CAMERAINFOMAP.put(cameraInfo.getCameraId() + cameraInfo.getRegionId(), cameraInfo);
        }

        initPageCount();

    }

    private void initPageCount() {
        cachePageNum(1L, 2L);
        cachePageNum(2L, 51L);
        cachePageNum(51L, 101L);
        cachePageNum(101L, -1L);
    }

    @Scheduled(cron = "${task_pagecount_start_time} * * ?")//定时执行 ，不再加时间判断
    public void taskPageCount() {
        initPageCount();
        logger.debug("-----taskPageCount");
    }


    public static CameraInfo getItem(String cameraId, String regionId) {
        logger.error("region----------"+cameraId+"_"+regionId);
        CameraInfo cameraInfo = CAMERAINFOMAP.get(cameraId + regionId);
        if (cameraInfo != null) {
            return cameraInfo;
        }

        return new CameraInfo();
    }

    public  void cachePageNum(Long beginClusterTotal,Long endClusterTotal){
        Criteria matchCondition = Criteria.where("ClusterTotal").gte(beginClusterTotal);
        if (endClusterTotal!= null&&endClusterTotal!=-1L) {
            matchCondition.lt(endClusterTotal);
        }
        Query query = new Query(matchCondition);
        Long count=mongoTemplate.count(query, ClusterInfo.class);
        logger.error(beginClusterTotal + "_" + endClusterTotal+count);
        CLUSTERINFOPAGE.put(beginClusterTotal + "_" + endClusterTotal,count);
        logger.error(CLUSTERINFOPAGE.get(beginClusterTotal + "_" + endClusterTotal)+"--count");

    }

    public static Long getClusterInfoPage(Long beginClusterTotal,Long endClusterTotal) {
        logger.error("region----------"+beginClusterTotal+"_"+endClusterTotal);
        Long count = CLUSTERINFOPAGE.get(beginClusterTotal + "_" + endClusterTotal);
        if (count == null) {
            return null;
        }
        return count;
    }

    public static Long getClusterInfoPageTotal() {
        Long count = 0L;
        for (Long itemCount : CLUSTERINFOPAGE.values()) {
            count += itemCount;
        }
        return count;
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
