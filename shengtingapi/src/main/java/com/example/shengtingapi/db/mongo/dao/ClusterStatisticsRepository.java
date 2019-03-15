package com.example.shengtingapi.db.mongo.dao;


import com.example.shengtingapi.db.mongo.entity.ClusterStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClusterStatisticsRepository extends MongoRepository<ClusterStatistics, String> {
    @Query(value="{'id':?0}")
    public ClusterStatistics getById(String pid);
}
