package com.example.shengtingapi.db.mongo.dao;


import com.example.shengtingapi.db.mongo.entity.ClusterInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClusterInfoRepository extends MongoRepository<ClusterInfo, String> {
    @Query(value="{'StatisticsTime':?0}")
    public ClusterInfo getByStatisticsTime(String statisticsTime);
}
