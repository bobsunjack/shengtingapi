package com.example.shengtingapi.db.mongo.dao;


import com.example.shengtingapi.db.mongo.entity.CameraInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraInfoRepository extends MongoRepository<CameraInfo, String> {
   /* @Query(value="{'StatisticsTime':?0}")
    public CameraInfo getByStatisticsTime(String statisticsTime);*/
}
