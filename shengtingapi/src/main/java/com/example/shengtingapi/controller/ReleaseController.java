package com.example.shengtingapi.controller;

import com.alibaba.fastjson.JSON;
import com.example.shengtingapi.db.mongo.entity.CameraInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterStatistics;
import com.example.shengtingapi.db.mongo.service.MongoService;
import com.example.shengtingapi.dto.*;
import com.example.shengtingapi.init.MongoCacheExecute;
import com.example.shengtingapi.json.BaseJson;
import com.example.shengtingapi.response.clusterget.ClusterGetResponse;
import com.example.shengtingapi.response.clustersearch.ClusterListResponse;
import com.example.shengtingapi.response.clustersearch.ClusterResponse;
import com.example.shengtingapi.response.clustersearch.ClusterSearchResponse;
import com.example.shengtingapi.response.wrap.ClusterGetItem;
import com.example.shengtingapi.response.wrap.ClusterGetResult;
import com.example.shengtingapi.response.wrap.ClusterSearchResult;
import com.example.shengtingapi.util.DateUtil;
import com.example.shengtingapi.util.HttpClientUtil;
import com.example.shengtingapi.util.MapUrlParamsUtils;
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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
@RequestMapping(value = "/shengting-r")
public class ReleaseController extends BaseController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoService mongoService;

    @RequestMapping(value = "/clusterStatistic")
    public Object clusterStatistic(@RequestBody BaseJson baseJson) {
        try {
            //Long qtime = DateUtil.getTimeByDay(baseJson.getQtime());
            //  Criteria criteria=  Criteria.where("StatisticsTime").is(baseJson.getQtime());
            Query query = new Query(
                    Criteria.where("StatisticsTime").is(baseJson.getQtime()));
            ClusterStatistics clusterStatistics= mongoTemplate.findOne(query, ClusterStatistics.class);
            return new RestResult<>(clusterStatistics);
        } catch (Exception e) {
           logger.error("",e);
        }
        return new RestResult("异常出错");
    }

    @RequestMapping(value = "/clusterInfo")
    public Object clusterInfo(@RequestBody BaseJson baseJson) {
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
            if (!baseJson.getEndClusterTotal().equals(-1)) {
                matchCondition.lt(baseJson.getBeginClusterTotal());
            }


            String orderField = baseJson.getOrderField() != null ? baseJson.getOrderField() : "CaptureTime";
            if (baseJson.getOrderType().equals("-1")) {
                sortOperation = sort(Sort.Direction.DESC, orderField);
            } else {
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
            logger.error("",e);
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
    public Object imageSearch(@RequestBody BaseJson baseJson) {
        try {

            String param = extractParam(baseJson.getImageBlob());
            String url = realUrl(BatchDetectAndExtract);
            String content = HttpClientUtil.postByStringJson(param, url, null);
            logger.error("extract:" + content);

            int begin = content.indexOf("blob") + 7;
            int end = content.indexOf("\"",begin) ;
            String imageStr = content.substring(begin, end);
            param = clusterSearchParam(baseJson.getTop(),imageStr,baseJson.getScore());
            url = realUrlClusterGet(ClusterSearch,"");
            String result = HttpClientUtil.postByStringJson(param, url, null);
            logger.error("search:"+result);
            ClusterSearchResponse obj = JSON.parseObject(content, ClusterSearchResponse.class);
            List<ClusterSearchResult> convertItems=convertSearch(obj);
            return new RestResult<>(convertItems);
        } catch (Exception e) {
            logger.error("",e);
        }
        return new RestResult("异常出错");
    }

    private List<ClusterSearchResult> convertSearch(ClusterSearchResponse result) {
        List<ClusterSearchResult> calcResult = new ArrayList();
        List<ClusterListResponse> clusters = result.getClusters();

        for (ClusterListResponse cluster:clusters){
            ClusterResponse clusterResponse = cluster.getResults().get(0);
            ClusterSearchResult item = new ClusterSearchResult();
            item.setScore(clusterResponse.getScore());
            item.setClusterId(clusterResponse.getCluster_id());

            /*item.setRegionId(clusterResponse.getObject_id().getCamera_id().getRegion_id());
            item.setImgUrl(clusterResponse.getPortrait_image().getUrl());
            item.setImgBigUrl(clusterResponse.getPortrait_image().getUrl());
            CameraInfo cameraInfo=MongoCacheExecute.getItem(item.getCameraId(), item.getRegionId());
            item.setCameraName(cameraInfo.getCameraName());
            item.setRegionName(cameraInfo.getRegionName());*/

            searchPersonByHttp(item);
            calcResult.add(item);
        }
        return calcResult;
    }

    private void searchPersonByHttp(ClusterSearchResult clusterSearchResult){
        try {
            String clusterId = clusterSearchResult.getClusterId();
            String url = realUrlClusterGet(ClusterGet,clusterId) + "?cluster_id=" +clusterId ;
            String content = HttpClientUtil.getByUrl(url, null);
            ClusterGetResponse obj = JSON.parseObject(content, ClusterGetResponse.class);
            ClusterGetResult clusterGetResult= convertClusterGet (obj);
            ClusterGetItem item= clusterGetResult.getItems().get(0);
            clusterSearchResult.setRegionId(item.getRegionId());
            clusterSearchResult.setImgUrl(item.getImgUrl());
            clusterSearchResult.setImgBigUrl(item.getImgBigUrl());
            clusterSearchResult.setCameraName(item.getCameraName());
            clusterSearchResult.setRegionName(item.getRegionName());
        } catch (IOException e) {
            logger.error("", e);
        }

    }
    private String clusterSearchParam(Integer top,String imageStr,Float score) throws IOException {
        Feature feature = new Feature(imageStr);
        Config config = new Config(top,score);
        ClusterSearch clusterSearch = new ClusterSearch(feature, config);
        String jsonParam = JSON.toJSONString(clusterSearch);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }

    private String extractParam(String imageStr) {
        Map param = new HashMap();
        List data = new ArrayList<>();
        Image image = new Image();
        image.setData(imageStr);
        image.setFormat(null);
        image.setUrl(null);
        ImageExtract imageExtract = new ImageExtract("LargestFace", image);
        data.add(imageExtract);
        param.put("requests", data);
        param.put("detect_mode", "Default");
        String jsonParam = JSON.toJSONString(param);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }

    @RequestMapping(value = "/onePersonList")
    public Object onePersonList(@RequestBody BaseJson baseJson) {
        try {
            String param = searchGetParam(baseJson.getPageNum(),baseJson.getPageSize(),baseJson.getClusterId(),baseJson.getStartTime(),baseJson.getEndTime());
            String url = realUrlClusterGet(ClusterGet,baseJson.getClusterId()) + "?" + param;
            String content = HttpClientUtil.getByUrl(url, null);
            ClusterGetResponse obj = JSON.parseObject(content, ClusterGetResponse.class);
            ClusterGetResult clusterGetResult= convertClusterGet (obj);
            return new RestResult<>(clusterGetResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RestResult("异常出错");
    }
    private ClusterGetResult convertClusterGet(ClusterGetResponse result) {
        List<ClusterGetItem> calclist = new ArrayList();
        List<ClusterResponse> clusters = result.getCluster().getResults();
        for (ClusterResponse clusterResponse:clusters){
            ClusterGetItem item = new ClusterGetItem();
            item.setCaptureTime(clusterResponse.getObject_id().getCaptured_time());
            item.setCameraId(clusterResponse.getObject_id().getCamera_id().getCamera_idx());
            item.setRegionId(clusterResponse.getObject_id().getCamera_id().getRegion_id());
            item.setImgUrl(clusterResponse.getPortrait_image().getUrl());
            item.setImgBigUrl(clusterResponse.getPortrait_image().getUrl());
            CameraInfo cameraInfo=MongoCacheExecute.getItem(item.getCameraId(), item.getRegionId());
            item.setCameraName(cameraInfo.getCameraName());
            item.setRegionName(cameraInfo.getRegionName());
            item.setLat(cameraInfo.getLat());
            item.setLng(cameraInfo.getLng());
            calclist.add(item);
        }
        return new ClusterGetResult(calclist,result.getPage().getTotal());
    }
    private String searchGetParam(Long page,Long size,String clusterId,String beginTime,String endTime) {
        Long offset = (page-1)*size;
        Map map = new HashMap();
        map.put("cluster_id", clusterId);
        map.put("period.start", DateUtil.convertToTZTime(beginTime));   //"2017-01-01T10:00:20.021Z"
        map.put("period.end", DateUtil.convertToTZTime(endTime));   //"2019-10-01T10:00:20.021Z"
        map.put("page.offset", offset);
        map.put("page.limit", size);
        map.put("ignore_centroid", false);
        map.put("object_type", BaseController.OBJECT_TYPE);
        map.put("feature_version", BaseController.FEATURE_VERSION);
        String jsonParam = MapUrlParamsUtils.getUrlParamsByMap(map);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }


}
