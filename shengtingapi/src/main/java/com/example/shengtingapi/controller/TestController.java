package com.example.shengtingapi.controller;

import com.alibaba.fastjson.JSON;
import com.example.shengtingapi.db.mongo.dao.CameraInfoRepository;
import com.example.shengtingapi.db.mongo.entity.CameraInfo;
import com.example.shengtingapi.dto.*;
import com.example.shengtingapi.test.TestFile2;
import com.example.shengtingapi.util.HttpClientUtil;
import com.example.shengtingapi.util.MapUrlParamsUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/shangtang1")
public class TestController extends BaseController {
    @Autowired
    CameraInfoRepository cameraInfoRepository;


    @RequestMapping(value = "/mongoinit")
    public Object mongoinit() {
        List<String> items= TestFile2.toArrayByFileReader1("E:\\TEST\\runoob.txt");
        for (String item : items) {
            String unit[] = item.split("\t");
            CameraInfo info = new CameraInfo();
            info.setCameraName(unit[0]);
            info.setCameraId(unit[1]);
            info.setRegionId(unit[2]);
            info.setRegionName(unit[3]);
            info.setArea(unit[4]);
            cameraInfoRepository.save(info);
        }
        return "success" + items.size();
    }


    @RequestMapping(value = "/batchExtract")
    public Object batchExtract() {
        try {
            String imageStr = toImgeString();

            String param = extractParam(imageStr);
            String url = realUrl(BatchDetectAndExtract);
            String result = HttpClientUtil.postByStringJson(param, url, null);
            logger.error("extract:" + result);
            return result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }

    private String toImgeString() throws IOException {
        InputStream inputStream = new FileInputStream(BaseController.TEST_IMG);// 照片所在路径
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        //base64编码
        Base64 base64 = new Base64();
        return base64.encodeToString(bytes);
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


    @RequestMapping(value = "/clusterGet")
    public Object clusterGet(Integer page) {
        try {
            String param = searchGetParam(page);
            String url = realUrlClusterGet(ClusterGet,CLUSTER_ID);
            String result = HttpClientUtil.postByStringJson(param, url, null);
            logger.error("extract:" + result);
            return result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }
    @RequestMapping(value = "/clusterGet2")
    public Object clusterGet2(Integer page,String clusterId) {
        try {
            BaseController.CLUSTER_ID = clusterId;
            String param = searchGetParam2(page);
            String url = realUrlClusterGet(ClusterGet,clusterId) + "?" + param;
            logger.error("get:"+url);
            String result = HttpClientUtil.getByUrl(url, null);
            logger.error("extract:" + result);
            return result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }
    private String searchGetParam2(Integer page) {
        int offset = page*30;
        Map map = new HashMap();
        map.put("cluster_id", BaseController.CLUSTER_ID);
        map.put("period.start", "2017-01-01T10:00:20.021Z");
        map.put("period.end", "2019-10-01T10:00:20.021Z");
        map.put("page.offset", offset);
        map.put("page.limit", BaseController.PAGE_LIMIT);
        map.put("ignore_centroid", false);
        map.put("object_type", BaseController.OBJECT_TYPE);
        map.put("feature_version", BaseController.FEATURE_VERSION);
        String jsonParam = MapUrlParamsUtils.getUrlParamsByMap(map);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }

    private String searchGetParam(Integer page) {
        int offset = page;
        Period period = new Period("2017-01-01T10:00:20.021Z", "2019-10-01T10:00:20.021Z");
        Page page1 = new Page(offset, 30);
        ClusterGet clusterGet = new ClusterGet(page1, period);
        String jsonParam = JSON.toJSONString(clusterGet);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }


    @RequestMapping(value = "/clusterSearch")
    public Object clusterSearch(Integer top) {
        try {
            String imageStr = toImgeString();
            String param = clusterSearchParam(top,imageStr);
            String url = realUrlClusterGet(ClusterSearch,"");
            String result = HttpClientUtil.postByStringJson(param, url, null);
            logger.error("extract:" + result);
            return result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }

    private String clusterSearchParam(Integer top,String imageStr) throws IOException {
        Feature feature = new Feature(imageStr);
        Config config = new Config(top);
        ClusterSearch clusterSearch = new ClusterSearch(feature, config);
        String jsonParam = JSON.toJSONString(clusterSearch);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }


    @RequestMapping(value = "/systeminfo")
    public Object systeminfo() {
        try {
            String param = systemInfoParam();
            String url = realUrlClusterGet(SYSTEMINFO,"") + "?" + param;
            logger.error("get:"+url);
            String result = HttpClientUtil.getByUrl(url, null);
            logger.error("extract:" + result);
            return result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }

    private String systemInfoParam() {
        Map map = new HashMap();
        map.put("object_type", BaseController.OBJECT_TYPE);
        map.put("feature_version", BaseController.FEATURE_VERSION);
        String jsonParam = MapUrlParamsUtils.getUrlParamsByMap(map);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }


    @RequestMapping(value = "/search")
    public Object search(Integer top) {
        try {
            String imageStr = toImgeString();

            String param = extractParam(imageStr);
            String url = realUrl(BatchDetectAndExtract);
            String content = HttpClientUtil.postByStringJson(param, url, null);
            logger.error("extract:" + content);

            int begin = content.indexOf("blob") + 7;
            int end = content.indexOf("\"",begin) ;
            imageStr = content.substring(begin, end);
            System.out.println();
            param = clusterSearchParam(top,imageStr);
            url = realUrlClusterGet(ClusterSearch,"");
            String result = HttpClientUtil.postByStringJson(param, url, null);
            logger.error("extract:" + result);
            return result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }

    @RequestMapping(value = "/clusterGet3")
    public Object clusterGet3(Integer begin,Integer size) {
        try {
            Integer page=0;
            String clusterId;
            String result="";
            long beginTime = System.currentTimeMillis();
            Integer total=size+begin;
            for(int i=begin;i<total;i++) {
                BaseController.CLUSTER_ID = i+"";
                String param = searchGetParam2(page);
                String url = realUrlClusterGet(ClusterGet,CLUSTER_ID) + "?" + param;
                logger.error("get:" + url);
                result = HttpClientUtil.getByUrl2(url, null);
                if(result==null){
                    logger.error("-----------------null:"+i);
                    total++;
                    continue;
                }
                logger.error("extract:" + result);
            }
            return (System.currentTimeMillis()-beginTime)/1000+"秒:"+result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }
}
