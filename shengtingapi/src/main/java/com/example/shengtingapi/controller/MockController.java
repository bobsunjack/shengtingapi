package com.example.shengtingapi.controller;

import com.alibaba.fastjson.JSON;
import com.example.shengtingapi.db.mongo.entity.ClusterInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterStatistics;
import com.example.shengtingapi.db.mongo.service.MongoService;
import com.example.shengtingapi.dto.*;
import com.example.shengtingapi.json.BaseJson;
import com.example.shengtingapi.response.wrap.ClusterGetItem;
import com.example.shengtingapi.response.wrap.ClusterGetResult;
import com.example.shengtingapi.response.wrap.ClusterSearchResult;
import com.example.shengtingapi.util.DateUtil;
import com.example.shengtingapi.util.HttpClientUtil;
import com.example.shengtingapi.util.MapUrlParamsUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
@RequestMapping(value = "/shengting")
public class MockController extends BaseController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoService mongoService;

    @RequestMapping(value = "/clusterStatistic")
   public Object clusterStatistic(@RequestBody BaseJson baseJson){
        try {
            //Long qtime = DateUtil.getTimeByDay(baseJson.getQtime());
          //  Criteria criteria=  Criteria.where("StatisticsTime").is(baseJson.getQtime());
            Query query = new Query(
                    Criteria.where("StatisticsTime").is(baseJson.getQtime()));
            ClusterStatistics clusterStatistics= mongoTemplate.findOne(query, ClusterStatistics.class);
            return new RestResult<>(clusterStatistics);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestResult("异常出错");
    }

    @RequestMapping(value = "/clusterInfo")
    public Object clusterInfo(@RequestBody BaseJson baseJson){
        try {
            Aggregation agg = null;
            SortOperation sortOperation;
            SkipOperation skipOperation;
            LimitOperation limitOperation;

            /*
            Long beginTime = DateUtil.getTimeByTime(baseJson.getStartTime());
            Long endTime = DateUtil.getTimeByTime(baseJson.getEndTime());

            Criteria matchCondition= Criteria.where("CaptureTime").gte(beginTime).lte(endTime);
            if(baseJson.getClusterTotal()!=null){
                matchCondition.and("ClusterTotal").gte(baseJson.getClusterTotal());
            }*/

            Criteria matchCondition = Criteria.where("ClusterTotal").gte(baseJson.getBeginClusterTotal());
            if (baseJson.getEndClusterTotal() != null) {
                matchCondition.lt(baseJson.getBeginClusterTotal());
            }

            String orderField = baseJson.getOrderField()!=null ? baseJson.getOrderField() : "CaptureTime";
            if(baseJson.getOrderType().equals("-1")){
                sortOperation = sort(Sort.Direction.DESC, orderField);
            }else {
                sortOperation = sort(Sort.Direction.ASC, orderField);
            }
            long _index = (baseJson.getPageNum() - 1) * baseJson.getPageSize();
            skipOperation = skip(_index);
            limitOperation = limit(baseJson.getPageSize());

            agg = newAggregation(
                    match(matchCondition),
                    sortOperation,
                    skipOperation,
                    limitOperation
            );
            agg.withOptions(newAggregationOptions().allowDiskUse(true).build());
            List<ClusterInfo> result = mongoService.findClusterInfo(agg);
            return new RestResult<>(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestResult("异常出错");
    }


    @RequestMapping(value = "/clusterInfoCount")
    public Object clusterInfoCount(@RequestBody BaseJson baseJson){
        try {
            Aggregation agg = null;

            /*
            Long beginTime = DateUtil.getTimeByTime(baseJson.getStartTime());
            Long endTime = DateUtil.getTimeByTime(baseJson.getEndTime());

            Criteria matchCondition= Criteria.where("CaptureTime").gte(beginTime).lte(endTime);
            if(baseJson.getClusterTotal()!=null){
                matchCondition.and("ClusterTotal").gte(baseJson.getClusterTotal());
            }*/

            Criteria matchCondition = Criteria.where("ClusterTotal").gte(baseJson.getBeginClusterTotal());
            if (baseJson.getEndClusterTotal() != null) {
                matchCondition.lt(baseJson.getBeginClusterTotal());
            }

            Query query = new Query(matchCondition);
            long count=mongoTemplate.count(query,ClusterInfo.class);
            return new RestResult<>(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestResult("异常出错");
    }

    @RequestMapping(value = "/imageSearch")
    public Object imageSearch(@RequestBody BaseJson baseJson){
        try {
            ClusterSearchResult result = new ClusterSearchResult();
            result.setCameraId("22");
            result.setCaptureTime("2019-03-01T00:45:11Z");
            result.setClusterId("33");
            result.setImgBigUrl("http://a.jpg");
            result.setImgUrl("http://b.jpg");
            result.setRegionId("222");
            result.setScore("0.9");
            List list = new ArrayList();
            list.add(result);
            return new RestResult<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestResult("异常出错");
    }

    @RequestMapping(value = "/onePersonList")
    public Object onePersonList(@RequestBody BaseJson baseJson){
        try {
            Long beginTime = DateUtil.getTimeByTime(baseJson.getStartTime());
            Long endTime = DateUtil.getTimeByTime(baseJson.getEndTime());


            ClusterGetItem item = new ClusterGetItem();
            item.setCameraId("22");
            item.setCaptureTime("2019-03-01T00:45:11Z");
            item.setImgBigUrl("http://a.jpg");
            item.setImgUrl("http://b.jpg");
            item.setRegionId("222");
            item.setCameraName("探点1");
            item.setRegionName("测试");
            item.setLat("39.916527");
            item.setLng("116.397128");
            List list = new ArrayList();
            list.add(item);
            ClusterGetResult result = new ClusterGetResult(list,1l);
            return new RestResult<>(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestResult("异常出错");
    }


}
