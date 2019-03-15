package com.example.shengtingapi.db.mongo.service;

import com.example.shengtingapi.db.mongo.entity.ClusterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MongoService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ClusterInfo> findClusterInfo(Aggregation agg) {
        AggregationResults<ClusterInfo> results = mongoTemplate.aggregate(agg, ClusterInfo.COLLETION_NAME, ClusterInfo.class);
        return results.getMappedResults();
    }
}
