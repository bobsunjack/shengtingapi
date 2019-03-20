package com.example.shengtingapi.controller;

import com.alibaba.fastjson.JSON;
import com.example.shengtingapi.db.mongo.dao.ClusterInfoRepository;
import com.example.shengtingapi.db.mongo.entity.CameraInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterStatistics;
import com.example.shengtingapi.db.mongo.service.MongoService;
import com.example.shengtingapi.dto.*;
import com.example.shengtingapi.init.MongoCacheExecute;
import com.example.shengtingapi.json.BaseJson;
import com.example.shengtingapi.response.base.ObjectId;
import com.example.shengtingapi.response.clusterget.ClusterGetResponse;
import com.example.shengtingapi.response.clustersearch.ClusterListResponse;
import com.example.shengtingapi.response.clustersearch.ClusterResponse;
import com.example.shengtingapi.response.clustersearch.ClusterSearchResponse;
import com.example.shengtingapi.response.wrap.ClusterGetItem;
import com.example.shengtingapi.response.wrap.ClusterGetResult;
import com.example.shengtingapi.response.wrap.ClusterSearchResult;
import com.example.shengtingapi.response.wrap.ClusterStatisticsNumResult;
import com.example.shengtingapi.util.DateUtil;
import com.example.shengtingapi.util.HttpClientUtil;
import com.example.shengtingapi.util.MapUrlParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
@RequestMapping(value = "/shengting")
public class ReleaseController extends BaseController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoService mongoService;


    @Autowired
    ClusterInfoRepository clusterInfoRepository;

    @Autowired
    MongoCacheExecute mongoCacheExecute;

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
           logger.debug("",e);
        }
        return new RestResult("异常出错");
    }

    @RequestMapping(value = "/clusterInfo")
    public Object clusterInfo(@RequestBody BaseJson baseJson) {
        try {
            Aggregation agg = null;
            Sort sortOperation;

            /*
            Long beginTime = DateUtil.getTimeByTime(baseJson.getStartTime());
            Long endTime = DateUtil.getTimeByTime(baseJson.getEndTime());

            Criteria matchCondition= Criteria.where("CaptureTime").gte(beginTime).lte(endTime);
            if(baseJson.getClusterTotal()!=null){
                matchCondition.and("ClusterTotal").gte(baseJson.getClusterTotal());
            }*/
            Criteria matchCondition = Criteria.where("ClusterTotal").gte(baseJson.getBeginClusterTotal());
            if (!baseJson.getEndClusterTotal().equals(-1L)) {
                matchCondition.lt(baseJson.getEndClusterTotal());
                if (baseJson.getBeginClusterTotal().equals(1L) && baseJson.getEndClusterTotal().equals(2L)) {
                    matchCondition = Criteria.where("ClusterTotal").is(baseJson.getBeginClusterTotal());
                }
            }


            logger.debug("-----------baseJson.getBeginClusterTotal()"+baseJson.getBeginClusterTotal()+","+baseJson.getEndClusterTotal());

            String orderField = baseJson.getOrderField() != null ? baseJson.getOrderField() : "CaptureTime";
            if (orderField.equals("qtime")) {
                orderField = "CaptureTime";
            }
            logger.debug("-----------base" + orderField);
            if (baseJson.getOrderType().equals(-1)) {
               // sortOperation = sort(Sort.Direction.DESC, orderField);
                sortOperation   =  new Sort(new Sort.Order(Sort.Direction.DESC, orderField));
            } else {
                //sortOperation = sort(Sort.Direction.ASC, orderField);
                sortOperation   =  new Sort(new Sort.Order(Sort.Direction.ASC, orderField));
            }
            long _index = (baseJson.getPageNum() - 1) * baseJson.getPageSize();

            Query query = new Query(matchCondition);
            query.limit(baseJson.getPageSize().intValue());
            query.skip((int) _index);
            query.with(sortOperation);
            Long begin = System.currentTimeMillis();
            logger.debug("begin"+baseJson.getPageSize().intValue()+"--skip:"+(int) _index+";;;"+orderField);
            List<ClusterInfo> result=mongoTemplate.find(query,ClusterInfo.class);
            Long end = System.currentTimeMillis();
            logger.debug("end"+result.size()+"---"+(end-begin));
            return JSON.toJSONString(new RestResult<>(result));
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

           /* Criteria matchCondition = Criteria.where("ClusterTotal").gte(baseJson.getBeginClusterTotal());
            if (baseJson.getEndClusterTotal() != null) {
                matchCondition.lt(baseJson.getEndClusterTotal());
            }

            Query query = new Query(matchCondition);
            long count=mongoTemplate.count(query,ClusterInfo.class);*/
            long count = 0;
            if (baseJson.getBeginClusterTotal().equals(1L)&&baseJson.getEndClusterTotal().equals(-1L)) {
                count= MongoCacheExecute.getClusterInfoPageTotal();
            }else {
                 count= MongoCacheExecute.getClusterInfoPage(baseJson.getBeginClusterTotal(), baseJson.getEndClusterTotal());
            }
            return new RestResult<>(count);
        } catch (Exception e) {
            logger.error("",e);
        }
        return new RestResult("异常出错");
    }

    @RequestMapping(value = "/imageSearch")
    public Object imageSearch(@RequestBody BaseJson baseJson) {
        try {

            String param = extractParam(baseJson.getImageBlob());
            String url = realUrl(BatchDetectAndExtract);
            String content = HttpClientUtil.postByStringJson(param, url, null);
            logger.debug("extract:" + content);

            int begin = content.indexOf("blob") + 7;
            int end = content.indexOf("\"",begin) ;
            String imageStr = content.substring(begin, end);
            param = clusterSearchParam(baseJson.getTop(),imageStr,baseJson.getScore());
            url = realUrlClusterGet(ClusterSearch,"");
            String result = HttpClientUtil.postByStringJson(param, url, null);
            logger.debug("search:"+result);
            ClusterSearchResponse obj = JSON.parseObject(result, ClusterSearchResponse.class);
            List<ClusterSearchResult> convertItems=convertSearch(obj);
            return new RestResult<>(convertItems);
        } catch (Exception e) {
            logger.debug("",e);
        }
        return new RestResult("异常出错");
    }

    private List<ClusterSearchResult> convertSearch(ClusterSearchResponse result) {
        List<ClusterSearchResult> calcResult = new ArrayList();
        List<ClusterListResponse> clusters = result.getClusters();

        for (ClusterListResponse cluster:clusters){
            ClusterResponse clusterResponse = cluster.getResults().get(0);
            ClusterSearchResult item = new ClusterSearchResult();
            item.setScore(cluster.getScore());
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
            clusterSearchResult.setCameraId(item.getCameraId());
            clusterSearchResult.setCaptureTime(item.getCaptureTime());
            clusterSearchResult.setImgUrl(item.getImgUrl());
            clusterSearchResult.setImgBigUrl(item.getImgBigUrl());
            clusterSearchResult.setCameraName(item.getCameraName());
            clusterSearchResult.setRegionName(item.getRegionName());
        } catch (IOException e) {
            logger.debug("", e);
        }

    }
    private String clusterSearchParam(Integer top,String imageStr,Float score) throws IOException {
        Feature feature = new Feature(imageStr);
        Config config = new Config(top,score);
        ClusterSearch clusterSearch = new ClusterSearch(feature, config);
        String jsonParam = JSON.toJSONString(clusterSearch);
        logger.debug("param:" + jsonParam);
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
        logger.debug("param:" + jsonParam);
        return jsonParam;
    }

    @RequestMapping(value = "/onePersonList")
    public Object onePersonList(@RequestBody BaseJson baseJson) {
        try {
            logger.debug("one personList clusterId list:"+baseJson.getClusterId());
            String[] clusters=baseJson.getClusterId().split(",");
            List<ClusterGetItem> calclist = new ArrayList();
            for(String clusterId:clusters) {
                logger.debug("one personList clusterId:"+clusterId);
                if(clusterId==null||clusterId.trim().equals(""))
                {
                    continue;
                }
                baseJson.setClusterId(clusterId);
                logger.debug("basejson clusterId:"+baseJson.getClusterId());
                personAll(baseJson, calclist);
            }
            Long begin = System.currentTimeMillis();
            if (calclist.size() > 0) {
                Collections.sort(calclist);
            }
            ///logger.debug("sort----"+(System.currentTimeMillis()-begin)+calclist.size()+calclist.get(0).getCaptureTime()+"next="+calclist.get(1).getCaptureTime());
            ClusterGetResult clusterGetResult= new ClusterGetResult(calclist, new Long(calclist.size()));
            String content = JSON.toJSONString(new RestResult<>(clusterGetResult));
            logger.debug("sort1----" + content);
            return content;
        } catch (Exception e) {
            logger.error("",e);
        }
        return new RestResult("异常出错");
    }

    public  void personAll(BaseJson baseJson,List<ClusterGetItem> calclist){
        try {
            Long pageNum=1L;
            Long pageSize = 500L;
            String param = searchGetParamAll(pageNum,pageSize,baseJson.getClusterId(),baseJson.getStartTime(),baseJson.getEndTime());
            String url = realUrlClusterGet(ClusterGet,baseJson.getClusterId()) + "?" + param;
            String content = HttpClientUtil.getByUrl(url, null);
            logger.debug("personall:" + url);
            logger.debug("personall content:"+content);
            ClusterGetResponse obj = JSON.parseObject(content, ClusterGetResponse.class);
            convertClusterGetAll (obj,calclist);
            Long totalSize = obj.getPage().getTotal();
            Long totalPageNum = pageNum;
            if(totalSize.compareTo(pageSize)>0){
                totalPageNum = new Double(Math.ceil(totalSize*1.0 / pageSize)).longValue();
            }
            logger.debug("totalSize:"+totalSize+",totalPageNum"+totalPageNum);
            for (pageNum = 2L; totalPageNum > pageNum;pageNum++) {
                logger.debug("totalPage:"+totalPageNum+",pageNum"+pageNum);
                param = searchGetParamAll(pageNum,pageSize,baseJson.getClusterId(),baseJson.getStartTime(),baseJson.getEndTime());
                url = realUrlClusterGet(ClusterGet,baseJson.getClusterId()) + "?" + param;
                content = HttpClientUtil.getByUrl(url, null);
                obj = JSON.parseObject(content, ClusterGetResponse.class);
                convertClusterGetAll (obj,calclist);
            }
        } catch (IOException e) {
          logger.debug("personAll" ,e);
        }
    }

/*    @RequestMapping(value = "/onePersonList")
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
    }*/
    private ClusterGetResult convertClusterGet(ClusterGetResponse result) {
        List<ClusterGetItem> calclist = new ArrayList();
        List<ClusterResponse> clusters = result.getCluster().getResults();
        for (ClusterResponse clusterResponse:clusters){
            ClusterGetItem item = new ClusterGetItem();
            item.setCaptureTime(clusterResponse.getObject_id().getCaptured_time());
            item.setCameraId(clusterResponse.getObject_id().getCamera_id().getCamera_idx());
            item.setRegionId(clusterResponse.getObject_id().getCamera_id().getRegion_id());
            item.setImgUrl(clusterResponse.getPortrait_image().getUrl());
            item.setImgBigUrl(clusterResponse.getPanoramic_image().getUrl());
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
        logger.debug("param:" + jsonParam);
        return jsonParam;
    }


    @RequestMapping(value = "/onePersonListAll")
    public Object onePersonListAll(@RequestBody BaseJson baseJson) {
        try {
            Long pageNum=1L;
            Long pageSize = 500L;
            String param = searchGetParamAll(pageNum,pageSize,baseJson.getClusterId(),baseJson.getStartTime(),baseJson.getEndTime());
            String url = realUrlClusterGet(ClusterGet,baseJson.getClusterId()) + "?" + param;
            String content = HttpClientUtil.getByUrl(url, null);
            ClusterGetResponse obj = JSON.parseObject(content, ClusterGetResponse.class);
            List<ClusterGetItem> calclist = new ArrayList();
            convertClusterGetAll (obj,calclist);
            Long totalSize = obj.getPage().getTotal();
            Long totalPageNum = pageNum;
            if(totalSize.compareTo(pageSize)>0){
               totalPageNum = new Double(Math.ceil(totalSize*1.0 / pageSize)).longValue();
            }
            for (pageNum = 2L; totalPageNum > pageNum;pageNum++) {
                param = searchGetParamAll(pageNum,pageSize,baseJson.getClusterId(),baseJson.getStartTime(),baseJson.getEndTime());
                url = realUrlClusterGet(ClusterGet,baseJson.getClusterId()) + "?" + param;
                content = HttpClientUtil.getByUrl(url, null);
                obj = JSON.parseObject(content, ClusterGetResponse.class);
                convertClusterGetAll (obj,calclist);
            }
            return new RestResult<>(calclist);
        } catch (Exception e) {
            logger.error("",e);
        }
        return new RestResult("异常出错");
    }


    private String searchGetParamAll(Long page,Long size,String clusterId,String beginTime,String endTime) {
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
        logger.debug("param:" + jsonParam);
        return jsonParam;
    }


    private void convertClusterGetAll(ClusterGetResponse result,List<ClusterGetItem> calclist) {

        List<ClusterResponse> clusters = result.getCluster().getResults();
        for (ClusterResponse clusterResponse:clusters){
            ClusterGetItem item = new ClusterGetItem();
            item.setCaptureTime(clusterResponse.getObject_id().getCaptured_time());
            item.setCameraId(clusterResponse.getObject_id().getCamera_id().getCamera_idx());
            item.setRegionId(clusterResponse.getObject_id().getCamera_id().getRegion_id());
            item.setImgUrl(clusterResponse.getPortrait_image().getUrl());
            item.setImgBigUrl(clusterResponse.getPanoramic_image().getUrl());
            CameraInfo cameraInfo=MongoCacheExecute.getItem(item.getCameraId(), item.getRegionId());
            item.setCameraName(cameraInfo.getCameraName());
            item.setRegionName(cameraInfo.getRegionName());
            item.setLat(cameraInfo.getLat());
            item.setLng(cameraInfo.getLng());
            calclist.add(item);
        }
        //return new ClusterGetResult(calclist,result.getPage().getTotal());
    }


    @RequestMapping(value = "/picNumStatistic")
    public Object picNumStatistic(@RequestBody BaseJson baseJson) {
        return MongoCacheExecute.CLUSTERINFOPAGE;
    }

    @RequestMapping(value = "/clusterStatisticByMonth")
    public Object clusterStatisticByMonth(@RequestBody BaseJson baseJson) {
        try {
            //Long qtime = DateUtil.getTimeByDay(baseJson.getQtime());
            //  Criteria criteria=  Criteria.where("StatisticsTime").is(baseJson.getQtime());

            Pattern pattern = Pattern.compile("^"+baseJson.getQtime()+".*$", Pattern.CASE_INSENSITIVE);
            Query query = new Query(
                    Criteria.where("StatisticsTime").regex(pattern));
            List<ClusterStatistics> clusterStatistics= mongoTemplate.find(query, ClusterStatistics.class);
            String preDay=DateUtil.getPreMonthLastDay(baseJson.getQtime());
            ClusterStatistics clusterStatisticsDay= findCalcNumByDay(preDay);
            if (clusterStatisticsDay != null) {
                clusterStatistics.add(clusterStatisticsDay);
            }
            List<String> queryDays = DateUtil.getMonthDays(baseJson.getQtime());
            List<ClusterStatisticsNumResult> results=  wrapStatisticNumByMonth(clusterStatistics, queryDays);
            return new RestResult<>(results);
        } catch (Exception e) {
            logger.error("",e);
        }
        return new RestResult("异常出错");
    }
    private List<ClusterStatisticsNumResult> wrapStatisticNumByMonth(List<ClusterStatistics> clusterStatistics,List<String> queryDays) {
        int index = 0;
        ClusterStatistics lastItem = null;
        List<ClusterStatisticsNumResult> results = new ArrayList<>();
        for (String day : queryDays) {
            ClusterStatisticsNumResult clusterStatisticsNumResult = null;
            for (ClusterStatistics item : clusterStatistics) {

                if (day.equals(item.getStatisticsTime())) {
                    if(index==1) {
                        lastItem = item;
                        break;
                    }

                    clusterStatisticsNumResult = new ClusterStatisticsNumResult();
                    clusterStatisticsNumResult.setCaptureTotal(item.getCaptureTotal());
                    clusterStatisticsNumResult.setClusterTotal(item.getClusterTotal());
                    clusterStatisticsNumResult.setNum(index);
                    clusterStatisticsNumResult.setStatisticsTime(item.getStatisticsTime());
                    if(lastItem!=null){
                        clusterStatisticsNumResult.setAddCaptureNum(item.getCaptureTotal()-lastItem.getCaptureTotal());
                        clusterStatisticsNumResult.setAddClusterNum(item.getClusterTotal()-lastItem.getClusterTotal());
                    }
                    lastItem = item;
                    break;
                }

            }
            if(index!=0) {
                if (clusterStatisticsNumResult == null) {
                    clusterStatisticsNumResult = new ClusterStatisticsNumResult();
                    clusterStatisticsNumResult.setNum(index);
                    lastItem = null;
                }
                results.add(clusterStatisticsNumResult);
            }
            index++;
        }
        return results;
    }

    @RequestMapping(value = "/clusterStatisticNum")
    public Object clusterStatisticNum(@RequestBody BaseJson baseJson) {
        try {
            //Long qtime = DateUtil.getTimeByDay(baseJson.getQtime());
            //  Criteria criteria=  Criteria.where("StatisticsTime").is(baseJson.getQtime());
            HashSet queryDay = new LinkedHashSet();
            if ("month".equals(baseJson.getType())) {
                queryDay= DateUtil.getListTime("month", 12);
                queryDay.add(DateUtil.getQtimeStrByDiffDay(-1));
                if(queryDay.size()>12){
                    queryDay.remove(queryDay.toArray()[0]);
                }
            }else if ("week".equals(baseJson.getType())) {
                queryDay= DateUtil.getListTime("week", 12);
            }else if ("day".equals(baseJson.getType())) {
                queryDay= DateUtil.getListTime("day", 12);
            }

            List<ClusterStatistics> clusterStatistics= calcNumByList(queryDay);
            //Collections.sort(clusterStatistics);
            List<ClusterStatisticsNumResult> results=wrapStatisticNum(clusterStatistics,queryDay);
            return new RestResult<>(results);
        } catch (Exception e) {
            logger.error("",e);
        }
        return new RestResult("异常出错");
    }

    private List<ClusterStatisticsNumResult> wrapStatisticNum(List<ClusterStatistics> clusterStatistics,HashSet<String> queryDays) {
        int index = 1;

        List<ClusterStatisticsNumResult> results = new ArrayList<>();
        for (String day : queryDays) {
            ClusterStatisticsNumResult clusterStatisticsNumResult = null;
            for (ClusterStatistics item : clusterStatistics) {
                if (day.equals(item.getStatisticsTime())) {
                    clusterStatisticsNumResult = new ClusterStatisticsNumResult();
                    clusterStatisticsNumResult.setCaptureTotal(item.getCaptureTotal());
                    clusterStatisticsNumResult.setClusterTotal(item.getClusterTotal());
                    clusterStatisticsNumResult.setNum(index);
                    clusterStatisticsNumResult.setStatisticsTime(item.getStatisticsTime());
                    break;
                }

            }
            if (clusterStatisticsNumResult == null) {
                clusterStatisticsNumResult=new ClusterStatisticsNumResult();
                clusterStatisticsNumResult.setNum(index);
                clusterStatisticsNumResult.setStatisticsTime(day);
            }

            results.add(clusterStatisticsNumResult);
            index++;
        }
        return results;
    }


    private List<ClusterStatistics> calcNumByList(HashSet<String> days) {
        Criteria matchCondition= Criteria.where("StatisticsTime").in(days);
        Query query = new Query(matchCondition);
        List<ClusterStatistics> clusterStatistics= mongoTemplate.find(query, ClusterStatistics.class);
        //Collections.sort(clusterStatistics);
        return clusterStatistics;
    }

    private ClusterStatistics findCalcNumByDay(String day) {
        Criteria matchCondition= Criteria.where("StatisticsTime").is(day);
        Query query = new Query(matchCondition);
        ClusterStatistics clusterStatistics= mongoTemplate.findOne(query, ClusterStatistics.class);
        //Collections.sort(clusterStatistics);
        return clusterStatistics;
    }

    private Long calcNum(String beginTime, String endTime) {
        GroupOperation groupOperation;
        ProjectionOperation projectionOperation;
        Criteria matchCondition= Criteria.where("StatisticsTime").gte(beginTime).lte(endTime);
        Query query = new Query(matchCondition);
        List<ClusterStatistics> clusterStatistics= mongoTemplate.find(query, ClusterStatistics.class);
        return 1L;

    }


    @RequestMapping(value = "/clusterNotify")
    public void clusterStatisticNum() {
        mongoCacheExecute.taskPageCount();
    }

    @RequestMapping(value = "/captureNum")
    public Object captureNum() {
        try {
            String param = systemInfoParam();
            String url = realUrlClusterGet(SYSTEMINFO,"") + "?" + param;
            logger.debug("captureNum:"+url);
            String content = HttpClientUtil.getByUrl(url, null);
            logger.debug("captureNum:" + content);
            int begin = content.indexOf("features") +11;
            int end = content.indexOf("\"",begin) ;
            String count = content.substring(begin, end);
            logger.debug("captureNum count:" + count);
            return new RestResult<>(new Long(count));
        } catch (Exception e) {
            logger.error("",e);
        }
        return new RestResult<>("异常出错");
    }

    private String systemInfoParam() {
        Map map = new HashMap();
        map.put("object_type", BaseController.OBJECT_TYPE);
        map.put("feature_version", BaseController.FEATURE_VERSION);
        String jsonParam = MapUrlParamsUtils.getUrlParamsByMap(map);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }


}
